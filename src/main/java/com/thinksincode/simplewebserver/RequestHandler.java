package com.thinksincode.simplewebserver;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

public class RequestHandler implements Runnable {
  private Socket socket;

  private BufferedOutputStream output;

  private BufferedReader input;

  private RequestLogger logger;

  public RequestHandler(Socket socket, RequestLogger logger) {
    this.socket = socket;
    this.logger = logger;
  }

  @Override
  public void run() {
    try {
      output = new BufferedOutputStream(socket.getOutputStream());
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

      while (true) {
        if (!processRequest()) {
          break;
        }
      }
    } catch (Exception ioe) {
      ioe.printStackTrace();
      System.err.println("Error processing request: " + ioe.getMessage());
    } finally {
      try {
        output.close();
        input.close();
        socket.close();
      } catch (IOException ioe) {}
    }
  }

  private boolean processRequest() throws IOException {
    String requestData = "";
    while (requestData.isEmpty()) {
      requestData = readRequest(input);
    }

    HttpRequest request;
    HttpResponse response;

    try {
      request = new HttpRequestParser().parse(requestData);
      if (request.getMethod() == HttpMethod.UNKNOWN) {
        response = new HttpResponse();
        response.setResponseCode(HttpResponseCode.NOT_IMPLEMENTED);
      } else {
        response = handleRequest(request);
      }
    } catch (BadRequestException bre) {
      request = new HttpRequest();
      request.setRequestLine(requestData.split("\r\n")[0]);
      response = new HttpResponse();
      response.setResponseCode(HttpResponseCode.BAD_REQUEST);
      response.setBody("");
    }

    logger.logRequest(request, response, socket);

    writeResponse(response);
    return keepConnectionAlive(request);
  }

  private boolean keepConnectionAlive(HttpRequest request) {
    if (request != null) {
      String connectionHeader = request.getHeader("Connection");
      return connectionHeader == null || connectionHeader.equals("keep-alive");
    }

    return false;
  }

  private void writeResponse(HttpResponse response) throws IOException {
    writeResponseLine(response);
    writeHeaders(response);
    writeString("\r\n");
    output.write(response.getBody());
    output.flush();
  }

  private void writeResponseLine(HttpResponse response) throws IOException {
    HttpResponseCode responseCode = response.getResponseCode();
    writeString(response.getProtocol().getName());
    writeString(" ");
    writeString(Integer.toString(responseCode.getCode()));
    writeString(" ");
    writeString(responseCode.getReason());
    writeString("\r\n");
  }

  private void writeHeaders(HttpResponse response) throws IOException {
    for (Map.Entry<String, String> header : response.getHeaders()) {
      writeString(header.getKey());
      writeString(": ");
      writeString(header.getValue());
      writeString("\r\n");
    }
  }

  private void writeString(String string) throws IOException {
    output.write(string.getBytes());
  }

  private String readRequest(BufferedReader reader) throws IOException {
    StringBuilder builder = new StringBuilder();
    String line;
    while ((line = reader.readLine()) != null && !line.isEmpty()) {
      builder.append(line).append("\r\n");
    }

    return builder.toString();
  }

  private HttpResponse handleRequest(HttpRequest request) {
    HttpResponse response = new HttpResponse();

    Path path = Paths.get("www", request.getPath());
    if (!Files.exists(path)) {
      response.setResponseCode(HttpResponseCode.NOT_FOUND);
      try (InputStream input404 = RequestHandler.class.getClassLoader().getResourceAsStream("404.html")) {
        byte[] body = new byte[input404.available()];
        input404.read(body);
        response.setBody(body);
      } catch (IOException ioe) {
        response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
      }
      response.setHeader("Content-Type", "text/html");
      return response;
    }

    try {
      if (request.getMethod() == HttpMethod.GET) {
        response.setBody(Files.readAllBytes(path));
      }
    } catch (IOException ioe) {
      response.setResponseCode(HttpResponseCode.INTERNAL_SERVER_ERROR);
      return response;
    }

    String[] pathParts = request.getPath().split("\\.");
    String extension = pathParts[pathParts.length - 1];

    response.setResponseCode(HttpResponseCode.OK);
    response.setHeader("Content-Type", ContentType.getContentType(extension));

    return response;
  }
}
