package com.example.vertx.starter.customCodec;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongExample {

  static final Logger log = LoggerFactory.getLogger(PingPongExample.class);

  public static void main(String[] args) {

    var vertx = Vertx.vertx();
    vertx.deployVerticle(new PingVerticle(), logOnHandler());
    vertx.deployVerticle(new PongVerticle(), logOnHandler());
  }

  final static Handler<AsyncResult<String>> logOnHandler() {
    return ar -> {
      if (ar.failed()) {
        log.error("err ", ar.cause());
      }
    };

  }

  static class PingVerticle extends AbstractVerticle {

    static final String ADDRESS = PingVerticle.class.getName();

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      final Ping MESSAGE = new Ping("KRY", true);
      var eventBus = vertx.eventBus();
      log.debug("Sending:{}", MESSAGE);
      //register only once
      eventBus.registerDefaultCodec(Ping.class, new LocalMessageCodec<>(Ping.class));
      eventBus.<Pong>request(ADDRESS, MESSAGE, reply -> {
        if (reply.failed()) {
          log.error("Failed", reply.cause());
          return;
        }
        log.debug("Response:{}", reply.result().body());
      });
      startPromise.complete();
    }
  }

  static class PongVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      //register only once
      vertx.eventBus().registerDefaultCodec(Pong.class, new LocalMessageCodec<>(Pong.class));
      vertx.eventBus().<Ping>consumer(PingVerticle.ADDRESS, message -> {
        log.debug("Received message:{}", message.body());
        message.reply(new Pong(0));
      }).exceptionHandler(error -> {
        log.error("Error: ", error);
      });
      startPromise.complete();
    }
  }
}
