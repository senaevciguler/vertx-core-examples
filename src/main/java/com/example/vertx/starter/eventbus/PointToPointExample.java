package com.example.vertx.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PointToPointExample {

  public static void main(String[] args) {
    var vertx = Vertx.vertx();
    vertx.deployVerticle(new Sender());
    vertx.deployVerticle(new Receiver());
  }
  static class Sender extends AbstractVerticle {
    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      String message = "Sending message..";
      startPromise.complete();
      var eventBus = vertx.eventBus();
      vertx.setPeriodic(1000, id -> {
        eventBus.send(Sender.class.getName(), message);
      });

    }
  }

  static class Receiver extends AbstractVerticle{
    private static final Logger log = LoggerFactory.getLogger(Receiver.class);
    @Override
    public void start(Promise<Void> stopPromise) throws Exception {
      stopPromise.complete();
      vertx.eventBus().<String>consumer(Sender.class.getName(),mes->{
        log.debug("Received{}", mes.body());
      });
    }
  }
}
