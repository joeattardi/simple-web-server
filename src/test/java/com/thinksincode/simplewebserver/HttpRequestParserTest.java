package com.thinksincode.simplewebserver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HttpRequestParserTest {
  private HttpRequestParser parser = new HttpRequestParser();

  @Test
  public void testParseSimpleRequest() throws BadRequestException {
    String requestString = "GET / HTTP/1.1\r\n\r\n";
    HttpRequest request = parser.parse(requestString);

    assertEquals(HttpMethod.GET, request.getMethod());
    assertEquals("/", request.getPath());
    assertEquals(HttpProtocol.HTTP_1_1, request.getProtocol());
  }

  @Test
  public void testParseRequestHeaders() throws BadRequestException {
    String requestString = "GET / HTTP/1.1\r\n" +
            "Host: example.com\r\n" +
            "Connection: keep-alive\r\n\r\n";
    HttpRequest request = parser.parse(requestString);

    assertEquals("example.com", request.getHeader("Host"));
    assertEquals("keep-alive", request.getHeader("Connection"));
  }

  @Test
  public void testCaseInsensitiveRequestHeaders() throws BadRequestException {
    String requestString = "GET / HTTP/1.1\r\n" +
            "host:example.com\r\n" +
            "Connection: keep-alive\r\n\r\n";
    HttpRequest request = parser.parse(requestString);

    assertEquals("example.com", request.getHeader("Host"));
    assertEquals("keep-alive", request.getHeader("connection"));
  }

  @Test
  public void testParseHeaderWithColon() throws BadRequestException {
    String requestString = "GET / HTTP/1.1\r\n" +
            "Host: localhost:8080\r\n\r\n";
    HttpRequest request = parser.parse(requestString);

    assertEquals("localhost:8080", request.getHeader("Host"));
  }
}
