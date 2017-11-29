package com.thinksincode.simplewebserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest {
  private Map<String, String> headers = new HashMap<>();

  private HttpMethod method;

  private String requestLine;

  private String path;

  private HttpProtocol protocol;

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getPath() {
    return this.path;
  }

  public void setProtocol(HttpProtocol protocol) {
    this.protocol = protocol;
  }

  public HttpProtocol getProtocol() {
    return protocol;
  }

  public void setHeader(String header, String value) {
    headers.put(header.toLowerCase(), value);
  }

  public String getHeader(String header) {
    return headers.get(header.toLowerCase());
  }

  public Set<Map.Entry<String, String>> getHeaders() {
    return headers.entrySet();
  }

  public void setRequestLine(String requestLine) {
    this.requestLine = requestLine;
  }

  public String getRequestLine() {
    return requestLine;
  }
}
