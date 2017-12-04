package com.thinksincode.simplewebserver;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigManager {
  public static final String CONFIG_KEY_PORT = "port";
  public static final String CONFIG_KEY_THREAD_POOL_SIZE = "threadPoolSize";
  public static final String CONFIG_KEY_WWW_ROOT = "wwwRoot";

  private Properties configuration = new Properties();

  public ConfigManager() throws IOException {
    try (InputStream stream = ConfigManager.class.getClassLoader().getResourceAsStream("serverDefaults.properties")) {
      configuration.load(stream);
    } catch (IOException ioe) {
      throw ioe;
    }

    Path configPath = Paths.get(".", "server.properties");
    try (InputStream stream = Files.newInputStream(configPath)) {
      configuration.load(stream);
    } catch (IOException ioe) {
      throw ioe;
    }
  }

  public String get(String key) {
    return configuration.getProperty(key);
  }
}
