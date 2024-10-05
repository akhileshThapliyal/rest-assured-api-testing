package org.example;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
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
    public void testPostEndpointWithJsonPath() {
        // Setup RestAssured base URL
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Create request body
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";;

        // Make a POST request and capture the response
        Response response = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // Validate status code
                .body("title", equalTo("foo"))  // Basic body assertions
                .body("body", equalTo("bar"))  // Example key-value assertion
                .body("userId", equalTo(1))  // Example key-value assertion
                .extract()
                .response();

        // Use GSON/JsonPath to parse and validate the JSON response
        String jsonResponse = response.asString();

        // Extract specific values using JsonPath
        String id = response.jsonPath().getString("userId");
        String name = response.jsonPath().getString("title");

        // Print the extracted values (Optional)
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);

        // Additional assertions using extracted data
        assertThat(id, notNullValue());
        assertThat(name, equalTo("foo"));
    }

    @Test
    public void testPostEndpointWithGson() {
        // Initialize Gson instance
        Gson gson = new Gson();

        // Create a Java object for the request body
        RequestBody requestBody = new RequestBody("foo", "bar", 1);

        // Convert Java object to JSON string using Gson
        String jsonRequestBody = gson.toJson(requestBody);

        // Setup RestAssured base URL
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        // Make a POST request and capture the response
        Response response = given()
                .header("Content-Type", "application/json")
                .body(jsonRequestBody)  // Use the Gson-generated JSON string
                .when()
                .post("/posts")
                .then()
                .statusCode(201)  // Validate status code
                .body("title", equalTo("foo"))  // Basic body assertions
                .body("body", equalTo("bar"))  // Example key-value assertion
                .body("userId", equalTo(1))  // Example key-value assertion
                .extract()
                .response();

        // Extract specific values using JsonPath
        String id = response.jsonPath().getString("userId");
        String name = response.jsonPath().getString("title");

        // Additional assertions
        assertThat(id, notNullValue());
        assertThat(name, equalTo("foo"));
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
