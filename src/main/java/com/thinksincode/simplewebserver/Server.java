package com.thinksincode.simplewebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private int port;

  private int threadCount;

  private RequestLogger logger = new RequestLogger();

  private ConfigManager config;

  public Server() {
    try {
      config = new ConfigManager();
    } catch (IOException ioe) {
      System.err.println("Error reading configuration: " + ioe.getMessage());
      System.exit(1);
    }

    this.port = Integer.parseInt(config.get(ConfigManager.CONFIG_KEY_PORT));
    this.threadCount = Integer.parseInt(config.get(ConfigManager.CONFIG_KEY_THREAD_POOL_SIZE));
  }

  public void start() throws IOException {
    ServerSocket serverSocket = new ServerSocket(this.port);
    System.out.println("SimpleWebServer listening on port " + this.port);

    ExecutorService clientExecutor = Executors.newFixedThreadPool(this.threadCount);

    while (true) {
      clientExecutor.submit(new RequestHandler(serverSocket.accept(), logger, config));
    }
  }
}
