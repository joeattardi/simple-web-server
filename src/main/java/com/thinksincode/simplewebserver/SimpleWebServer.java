package com.thinksincode.simplewebserver;

import java.io.IOException;

public class SimpleWebServer {
  private static final int DEFAULT_PORT = 8080;
  private static final int DEFAULT_THREAD_COUNT = 4;

  public static void main(String...args) {
    int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

    Server server = new Server(port, DEFAULT_THREAD_COUNT);

    try {
      server.start();
    } catch (IOException ioe) {
      System.err.println("Failed to start server: " + ioe.getMessage());
    }
  }
}
