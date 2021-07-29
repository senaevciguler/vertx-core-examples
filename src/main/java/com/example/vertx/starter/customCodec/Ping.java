package com.example.vertx.starter.customCodec;

public class Ping {
  private String name;
  private boolean enabled;

  public Ping(){

  }
  public Ping(String name, boolean enabled) {
    this.name = name;
    this.enabled = enabled;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  @Override
  public String toString() {
    return "Ping{" +
      "name='" + name + '\'' +
      ", enabled=" + enabled +
      '}';
  }
}
