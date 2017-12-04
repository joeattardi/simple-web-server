package com.thinksincode.simplewebserver;

import java.io.IOException;

public class SimpleWebServer {
  private static final int DEFAULT_PORT = 8080;
  private static final int DEFAULT_THREAD_COUNT = 4;

  public static void main(String...args) {
    Server server = new Server();

    try {
      server.start();
    } catch (IOException ioe) {
      System.err.println("Failed to start server: " + ioe.getMessage());
    }
  }
}
