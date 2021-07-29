package com.example.vertx.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerVerticle extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(WorkerVerticle.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("deployed as worker verticle");
    startPromise.complete();
    Thread.sleep(5000);
    log.debug("blocking operation done");
  }
}
