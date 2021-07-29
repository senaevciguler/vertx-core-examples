package com.example.vertx.starter.json;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonObjectExample {

  @Test
  void JsonObjectCanBeMapped(){
    final JsonObject myJsonObject = new JsonObject();
    myJsonObject.put("id",1);
    myJsonObject.put("name","KRY");
    myJsonObject.put("loves_vertx", true);

    final String encoded = myJsonObject.encode();
    assertEquals("{\"id\":1,\"name\":\"KRY\",\"loves_vertx\":true}", encoded);

    final JsonObject decoded = new JsonObject(encoded);
    assertEquals(myJsonObject,decoded);
  }
  @Test
  void jsonObjectCanBeCreatedFromMap(){
    final Map<String,Object> myMap = new HashMap<>();
    myMap.put("id",1);
    myMap.put("name", "KRY");
    myMap.put("love_vertx", true);
    final JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap,asJsonObject.getMap());
    assertEquals(1,asJsonObject.getInteger("id"));
    assertEquals("KRY",asJsonObject.getString("name"));
    assertEquals(true,asJsonObject.getBoolean("love_vertx"));

  }
  @Test
  void JsonArrayCanBeMapped(){
    JsonArray jsonArray = new JsonArray();
    jsonArray
      .add(new JsonObject().put("id",1))
      .add(new JsonObject().put("id",2))
      .add(new JsonObject().put("name","KRY"))
      .add("randomValue");
    assertEquals("[{\"id\":1},{\"id\":2},{\"name\":\"KRY\"},\"randomValue\"]",jsonArray.encode());

  }
  @Test
  void canMappedJavaObject(){
    Person person = new Person(1,"KRY",true);
    final JsonObject kry = JsonObject.mapFrom(person);
    assertEquals(person.getId(),kry.getInteger("id"));
    assertEquals(person.getName(), kry.getString("name"));
    assertEquals(person.getLove_vertx(), kry.getBoolean("love_vertx"));

    final Person person2 = kry.mapTo(Person.class);
    assertEquals(person.getId(),person2.getId());
    assertEquals(person.getName(),person2.getName());
    assertEquals(person.getLove_vertx(),person2.getLove_vertx());
  }
}
