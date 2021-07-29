package com.example.vertx.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleN extends AbstractVerticle{

  private static final Logger log = LoggerFactory.getLogger(VerticleN.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("start{} with config{}" , getClass().getName() , config().toString());
    startPromise.complete();
  }
}
