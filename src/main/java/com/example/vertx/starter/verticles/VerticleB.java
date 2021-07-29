package com.example.vertx.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleB extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VerticleB.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("start{}" , getClass().getName());
    startPromise.complete();
  }
}
