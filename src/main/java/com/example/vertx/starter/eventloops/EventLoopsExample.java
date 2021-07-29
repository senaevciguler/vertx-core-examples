package com.example.vertx.starter.eventloops;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class EventLoopsExample extends AbstractVerticle {
private static final Logger log = LoggerFactory.getLogger(EventLoopsExample.class);

  public static void main(String[] args) {
    var vertx = Vertx.vertx(
      new VertxOptions()
      .setMaxEventLoopExecuteTime(500)
      .setMaxEventLoopExecuteTimeUnit(TimeUnit.MILLISECONDS)
      .setBlockedThreadCheckInterval(1)
      .setBlockedThreadCheckIntervalUnit(TimeUnit.SECONDS)
      .setEventLoopPoolSize(4)
    );
    vertx.deployVerticle(EventLoopsExample.class.getName(),
      new DeploymentOptions().setInstances(4));
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("start{}",getClass().getName());
    startPromise.complete();
    //don't do this inside a verticle
    Thread.sleep(5000);
  }
}
