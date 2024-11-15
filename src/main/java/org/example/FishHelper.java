package org.example;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class FishHelper {
  private final RequestSpecification spec;

  public FishHelper() {
    spec = given()
        .baseUri("https://fish-text.ru/")
        .log().all();
  }

  public RequestSpecification getFish() {
    return given(spec);
  }
}
