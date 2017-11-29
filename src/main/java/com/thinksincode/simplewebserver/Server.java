package com.thinksincode.simplewebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private int port;

  private int threadCount;

  public Server(int port, int threadCount) {
    this.port = port;
    this.threadCount = threadCount;
  }

  public void start() throws IOException {
    ServerSocket serverSocket = new ServerSocket(this.port);
    System.out.println("SimpleWebServer listening on port " + this.port);

    ExecutorService clientExecutor = Executors.newFixedThreadPool(this.threadCount);

    while (true) {
      clientExecutor.submit(new RequestHandler(serverSocket.accept()));
    }
  }
}
