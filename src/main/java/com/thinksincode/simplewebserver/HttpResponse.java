package com.thinksincode.simplewebserver;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpResponse {
  private HttpProtocol protocol = HttpProtocol.HTTP_1_1;

  private HttpResponseCode responseCode;

  private Map<String, String> headers = new HashMap<>();

  private byte[] body = new byte[0];

  public HttpResponse() {
    setHeader("Date", ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.RFC_1123_DATE_TIME));
  }

  public void setResponseCode(HttpResponseCode responseCode) {
    this.responseCode = responseCode;
  }

  public HttpResponseCode getResponseCode() {
    return responseCode;
  }

  public void setBody(String body) {
    this.body = body.getBytes();
  }

  public byte[] getBody() {
    return body;
  }

  public void setHeader(String header, String value) {
    headers.put(header, value);
  }

  public String getHeader(String header) {
    return headers.get(header);
  }

  public Set<Map.Entry<String, String>> getHeaders() {
    return headers.entrySet();
  }

  public void setProtocol(HttpProtocol protocol) {
    this.protocol = protocol;
  }

  public HttpProtocol getProtocol() {
    return protocol;
  }
}
