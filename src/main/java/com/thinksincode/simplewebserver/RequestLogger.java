package com.thinksincode.simplewebserver;

import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class RequestLogger {
  private DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("'['dd/MMM/yyyy:hh:mm:ss Z']'");

  public void logRequest(HttpRequest request, HttpResponse response, Socket socket) {
    StringBuilder sb = new StringBuilder();

    sb.append(socket.getInetAddress().getHostAddress());
    sb.append(" - - ");

    sb.append(ZonedDateTime.now().format(timestampFormatter));
    sb.append(" ");

    sb.append("\"" + request.getRequestLine() + "\" ");

    sb.append(response.getResponseCode().getCode());
    sb.append(" ");

    sb.append(response.getHeader("Content-Length"));

    System.out.println(sb.toString());
  }
}
