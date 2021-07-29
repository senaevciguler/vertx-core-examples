package com.example.vertx.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseExample {

  static final Logger log = LoggerFactory.getLogger(RequestResponseExample.class);

  public static void main(String[] args) {

    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());

  }

  static class RequestVerticle extends AbstractVerticle{

    static final String ADDRESS = "my.sena.request.address";
    public static final String MESSAGE = " Hello KRY";

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      log.debug("Sending:{}", MESSAGE);
      eventBus.<String>request(ADDRESS, MESSAGE, reply -> {
        log.debug("Response:{}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<String>consumer(RequestVerticle.ADDRESS,message ->{
        log.debug("Received message:{}", message.body());
        message.reply(" Received your message,Thank you!");
      });
    }
  }
}
