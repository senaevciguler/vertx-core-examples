package com.example.vertx.starter.worker;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerExample extends AbstractVerticle {

  private static final Logger log = LoggerFactory.getLogger(WorkerExample.class);

  public static void main(String[] args) {
    var vertx= Vertx.vertx();
    vertx.deployVerticle(new WorkerExample());
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    vertx.deployVerticle(new WorkerVerticle(),
      new DeploymentOptions()
    .setWorker(true)
    .setWorkerPoolSize(1)
    .setWorkerPoolName("my-worker-verticle"));
    startPromise.complete();
    executed();
  }

  private void executed() {
    vertx.executeBlocking(event -> {
      log.debug("executing blocking code");
      try{
        Thread.sleep(5000);
        event.complete();
      }catch (InterruptedException e){
        log.error("failed:",e);
        event.fail(e);
      }
    }, result ->{
      if(result.succeeded()){
        log.debug("blocking call done");
      }else{
        log.debug("blocking call failed due to:", result.cause());
      }
    });
  }
}
