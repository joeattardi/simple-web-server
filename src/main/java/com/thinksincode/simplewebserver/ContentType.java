package com.thinksincode.simplewebserver;

import java.util.HashMap;
import java.util.Map;

public class ContentType {
  private static Map<String, String> mappings = new HashMap<>();

  static {
    mappings.put("html", "text/html");
    mappings.put("jpg", "image/jpeg");
    mappings.put("jpeg", "image/jpeg");
    mappings.put("gif", "image/gif");
    mappings.put("json", "application/json");
  }

  public static String getContentType(String extension) {
    String contentType = mappings.get(extension.toLowerCase());
    if (contentType == null) {
      return "text/plain";
    }

    return contentType;
  }
}
