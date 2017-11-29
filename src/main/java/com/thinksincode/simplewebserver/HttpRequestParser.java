package com.thinksincode.simplewebserver;

public class HttpRequestParser {
  public HttpRequest parse(String requestData) throws BadRequestException {
    HttpRequest request = new HttpRequest();

    String[] lines = requestData.trim().split("\r\n");
    String[] requestLineItems = lines[0].split(" ");

    request.setRequestLine(lines[0]);
    if (requestLineItems.length != 3) {
      throw new BadRequestException();
    }

    try {
      request.setMethod(HttpMethod.valueOf(requestLineItems[0]));
    } catch (IllegalArgumentException iae) {
      request.setMethod(HttpMethod.UNKNOWN);
    }

    request.setPath(requestLineItems[1]);

    if (requestLineItems[2].equals("HTTP/1.0")) {
      request.setProtocol(HttpProtocol.HTTP_1_0);
    } else if (requestLineItems[2].equals("HTTP/1.1")) {
      request.setProtocol(HttpProtocol.HTTP_1_1);
    } else {
      throw new BadRequestException();
    }

    for (int i = 1; i < lines.length; i++) {
      String[] headerItems = lines[i].split(":", 2);
      if (headerItems.length != 2) {
        throw new BadRequestException();
      }
      request.setHeader(headerItems[0], headerItems[1].trim());
    }

    return request;
  }
}
