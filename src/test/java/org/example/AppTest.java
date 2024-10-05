package org.example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testGetEndpoint() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)  // Check if status code is 200
                .body("userId", equalTo(1))  // Validate the body field 'userId'
                .body("title", containsString("sunt aut facere"));  // Validate part of the title
    }

    @Test
    public void testPostEndpoint() {
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        given()
                .contentType(ContentType.JSON)  // Set content type
                .body(requestBody)  // Send request body
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)  // Check if status code is 201 (Created)
                .body("title", equalTo("foo"))  // Validate title field
                .body("body", equalTo("bar"))  // Validate body field
                .body("userId", equalTo(1));  // Validate userId field
    }

    @Test
    public void testWithQueryParams() {
        given()
                .queryParam("userId", 1)  // Set query parameter
                .when()
                .get("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(200)
                .body("size()", greaterThan(0));  // Validate that response contains data
    }

    @Test
    public void testResponseHeaders() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8");  // Validate Content-Type header
    }

    @Test
    public void testJsonStructure() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)
                .body("id", notNullValue())  // Validate if 'id' field is not null
                .body("title", notNullValue())  // Validate if 'title' field is not null
                .body("body", notNullValue());  // Validate if 'body' field is not null
    }
}
