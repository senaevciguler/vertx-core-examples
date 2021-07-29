package com.example.vertx.starter.json;

public class Person {
  private Integer id;
  private String name;
  private Boolean love_vertx;

  public Person(){
    //default constructor for Jackson
  }

  public Person(Integer id, String name, Boolean love_vertx) {
    this.id = id;
    this.name = name;
    this.love_vertx = love_vertx;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getLove_vertx() {
    return love_vertx;
  }

  public void setLove_vertx(Boolean love_vertx) {
    this.love_vertx = love_vertx;
  }
}
