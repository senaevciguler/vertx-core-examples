package com.example.vertx.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class VertickleAA extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VertickleAA.class);

  @Override
  public void start(final Promise<Void> startPromise) throws Exception {
   log.debug("start{}" , getClass().getName());
    startPromise.complete();
  }

  @Override
  public void stop(final Promise<Void> stopPromise) throws Exception {
    log.debug("stop{}" , getClass().getName());
    stopPromise.complete();
  }
}
