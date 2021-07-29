package com.example.vertx.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class PublishSubscribeExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Publish());
    vertx.deployVerticle(new Subscribe1());
    vertx.deployVerticle(Subscribe2.class.getName(), new DeploymentOptions().setInstances(2));
  }

  public static class Publish extends AbstractVerticle{
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.setPeriodic(Duration.ofSeconds(10).toMillis(), id ->{
      vertx.eventBus().publish(Publish.class.getName()," a message for everyone!");
      });
    }
  }
  public static class Subscribe1 extends AbstractVerticle{
    private final Logger log = LoggerFactory.getLogger(Subscribe1.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(Publish.class.getName(),message->{
        log.debug("Received{}", message.body());
      });
    }
  }
  public static class Subscribe2 extends AbstractVerticle{
    private final Logger log = LoggerFactory.getLogger(Subscribe2.class);
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().consumer(Publish.class.getName(),message->{
        log.debug("Received{}", message);
      });
    }
  }
}
