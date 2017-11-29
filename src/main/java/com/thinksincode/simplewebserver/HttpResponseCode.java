package com.thinksincode.simplewebserver;

public enum HttpResponseCode {

  OK(200, "OK"),
  BAD_REQUEST(400, "Bad Request"),
  NOT_IMPLEMENTED(501, "Not Implemented");

  private int code;
  private String reason;

  HttpResponseCode(int code, String reason) {
    this.code = code;
    this.reason = reason;
  }

  public int getCode() {
    return code;
  }

  public String getReason() {
    return reason;
  }
}
