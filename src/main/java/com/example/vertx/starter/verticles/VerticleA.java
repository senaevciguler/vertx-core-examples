package com.example.vertx.starter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerticleA extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(VerticleA.class);

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    log.debug("start{}", getClass().getName());
    vertx.deployVerticle(new VertickleAA(), whenDeployed ->{
      log.debug("deployed{}" , VertickleAA.class.getName());
      vertx.undeploy(whenDeployed.result());
    });
    vertx.deployVerticle(new VerticleAB(), whenDeployed ->{
      log.debug("deployed{}" , VerticleAB.class.getName());
      //do not undeployed
    });
    startPromise.complete();
  }
}
