package com.example.vertx.starter;

import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(VertxExtension.class)
public class FuturePromiseExample {
  private final Logger log = LoggerFactory.getLogger(FuturePromiseExample.class);

  @Test
  void promise_success(Vertx vertx, VertxTestContext vertxTestContext){
    final Promise<String> promise = Promise.promise();
    log.debug("start");
    vertx.setTimer(5000, id->{
      promise.complete("success");
      log.debug("success");
      vertxTestContext.completeNow();
    });
    log.debug("end");
  }
  @Test
  void promise_failure(Vertx vertx, VertxTestContext vertxTestContext){
    final Promise<String> promise = Promise.promise();
    log.debug("start");
    vertx.setTimer(5000, id ->{
      promise.fail(new RuntimeException("failed"));
      log.debug("failed");
      vertxTestContext.completeNow();
    });
    log.debug("end");
  }
  @Test
  void future_success(Vertx vertx, VertxTestContext vertxTestContext){
    final Promise<String> promise = Promise.promise();
    log.debug("start");
    vertx.setTimer(5000, id ->{
      promise.complete("success");
      log.debug("timer done");
      vertxTestContext.completeNow();
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(result->{
        log.debug("Result{} ", result);
        vertxTestContext.completeNow();
      })
      .onFailure(vertxTestContext::failNow);
  }
  @Test
  void future_fail(Vertx vertx, VertxTestContext vertxTestContext){
    final Promise<String> promise = Promise.promise();
    log.debug("start");
    vertx.setTimer(5000, id ->{
      promise.fail(new RuntimeException("failed"));
      log.debug("timer done");
      //vertxTestContext.completeNow();
    });
    final Future<String> future = promise.future();
    future
      .onSuccess(vertxTestContext::failNow)
      .onFailure(error->{
        log.debug("Result: ", error);
        vertxTestContext.completeNow();
      });
  }
  @Test
  void future_map(Vertx vertx, VertxTestContext vertxTestContext){
    final Promise<String> promise = Promise.promise();
    log.debug("start");
    vertx.setTimer(5000, id ->{
      promise.complete("success");
      log.debug("timer done");

    });
    final Future<String> future = promise.future();
    future
      .map(asString->{
        log.debug("map String to JsonObject");
        return new JsonObject().put("key",asString);
      })
      .map(jsonObject-> new JsonArray().add(jsonObject))
      .onSuccess(result->{
        log.debug("Result: {} of type {} ", result, result.getClass().getSimpleName());
        vertxTestContext.completeNow();
      })
      .onFailure(vertxTestContext::failNow);
  }
  @Test
  void future_coordination(Vertx vertx, VertxTestContext vertxTestContext){
    vertx.createHttpServer()
      .requestHandler(request-> log.debug("{}",request))
      .listen(10_000)
      .onFailure(vertxTestContext::failNow)
      .onSuccess(server->{
        log.debug("server started on port {}", server.actualPort());
        vertxTestContext.completeNow();
      });
  }
  @Test
  void future_composition(Vertx vertx, VertxTestContext vertxTestContext){
    var one= Promise.<Void>promise();
    var two= Promise.<Void>promise();
    var three= Promise.<Void>promise();

    var futureOne = one.future();
    var futureTwo = two.future();
    var futureThree = three.future();

    CompositeFuture.all(futureOne,futureTwo,futureThree)
      .onFailure(vertxTestContext::failNow)
      .onSuccess(result->{
        log.debug("success");
        vertxTestContext.completeNow();
      });
    //Complete Futures
    vertx.setTimer(5000,id->{
      one.complete();
      two.complete();
      three.complete();
    });
  }
}
