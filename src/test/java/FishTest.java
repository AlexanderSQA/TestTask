import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.example.FishHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class FishTest {
  FishHelper fishHelper = new FishHelper();

  @Test
  public void checkMethodWithSentence() {
    int number = 2;
    ValidatableResponse resp = fishHelper
        .getFish()
        .formParam("type", "sentence")
        .formParam("number", number)
        .formParam("format", "html")
        .when()
        .get("/get")
        .then().log().all()
        .assertThat().contentType(ContentType.HTML);
    String response = resp.extract().body().asString();
    String[] sentences = response.replaceAll("<[^>]*>", "").split("\\. | , | \\?");
    Assertions.assertEquals(number, sentences.length);
  }

  @Test
  public void checkMethodWithParagraph() {
    int number = 4;
    ValidatableResponse resp = fishHelper
        .getFish()
        .formParam("type", "paragraph")
        .formParam("number", number)
        .formParam("format", "html")
        .when()
        .get("/get")
        .then().log().all()
        .assertThat().contentType(ContentType.HTML);
    String response = resp.extract().body().asString();
    String[] paragraph = response.split("<[^>]*>");
    Assertions.assertEquals(number, Arrays.stream(paragraph).filter(s -> !s.isBlank()).count());
  }

  @Test
  public void checkMethodWithTitle() {
    int number = 6;
    ValidatableResponse resp = fishHelper
        .getFish()
        .formParam("type", "title")
        .formParam("number", number)
        .formParam("format", "html")
        .when()
        .get("/get")
        .then().log().all()
        .assertThat().contentType(ContentType.HTML);
    String response = resp.extract().body().asString();
    String[] title = response.split("<[^>]*>");
    Assertions.assertEquals(number, Arrays.stream(title).filter(s -> !s.isBlank()).count());
  }

  @Test
  public void checkMethodWithSentenceAndJson() {
    int number = 2;
    ValidatableResponse resp = fishHelper
        .getFish()
        .formParam("type", "sentence")
        .formParam("number", number)
        .formParam("format", "json")
        .when()
        .get("/get")
        .then().log().all()
        .assertThat().contentType(ContentType.JSON);
    String response = resp.extract().body().jsonPath().getString("text");
    String[] sentences = response.replaceAll("<[^>]*>", "").split("\\. | , | \\?");
    Assertions.assertEquals(number, sentences.length);
  }
  @Test
  public void checkMethodWithParagraphAndJson() {
    int number = 3;
    ValidatableResponse resp = fishHelper
        .getFish()
        .formParam("type", "paragraph")
        .formParam("number", number)
        .formParam("format", "json")
        .when()
        .get("/get")
        .then().log().all()
        .assertThat().contentType(ContentType.JSON);
    String response = resp.extract().body().jsonPath().getString("text");

    String[] paragraph = response.split("\\\\n\\\\n");
    Assertions.assertEquals(number, Arrays.stream(paragraph).filter(s -> !s.isBlank()).count());
  }

  @Test
  public void checkMethodWithTitleAndJson() {
    int number = 5;
    ValidatableResponse resp = fishHelper
        .getFish()
        .formParam("type", "title")
        .formParam("number", number)
        .formParam("format", "json")
        .when()
        .get("/get")
        .then().log().all()
        .assertThat().contentType(ContentType.JSON);
    String response = resp.extract().body().jsonPath().getString("text");
    String[] title = response.split("\\\\n\\\\n");
    Assertions.assertEquals(number, Arrays.stream(title).filter(s -> !s.isBlank()).count());
  }
}
