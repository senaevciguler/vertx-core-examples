package com.example.vertx.starter.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseJsonExample {

  static final Logger log = LoggerFactory.getLogger(RequestResponseJsonExample.class);

  public static void main(String[] args) {

    var vertx = Vertx.vertx();
    vertx.deployVerticle(new RequestVerticle());
    vertx.deployVerticle(new ResponseVerticle());

  }

  static class RequestVerticle extends AbstractVerticle{

    static final String ADDRESS = "my.sena.request.address";


    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      var eventBus = vertx.eventBus();
      final var MESSAGE = new JsonObject()
        .put("message","Hello KRY")
        .put("version", 1);
      log.debug("Sending:{}", MESSAGE);
      eventBus.<JsonArray>request(ADDRESS, MESSAGE, reply -> {
        log.debug("Response:{}", reply.result().body());
      });
    }
  }

  static class ResponseVerticle extends AbstractVerticle{

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
      startPromise.complete();
      vertx.eventBus().<JsonObject>consumer(RequestVerticle.ADDRESS, message ->{
        log.debug("Received message:{}", message.body());
        message.reply(new JsonArray().add("one").add("two").add("three"));
      });
    }
  }
}
