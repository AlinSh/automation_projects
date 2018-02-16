package com.epam.alina_shylo.task6;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;


public class RestAssuredTests {

    @BeforeTest
    public void setUpBeforeTest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com/";
    }

    @Test
    public void basicGetTest() {
        given().when().get("https://jsonplaceholder.typicode.com/").then().statusCode(200);
    }

    @Test
    public void verifyTitleOfFirstPostTest() {
        given().when().get("/posts/1").then().statusCode(200).and().
                body("title" , equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));

                /* .and()
                .assertThat()
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))*/
    }

    @Test
    public void verifyContentOfSecondPostTest() {
        given().when().get("/posts/2").then().
                body(containsString("qui est esse")).
                body(containsString("est rerum tempore"));
    }


    @Test
    public void invalidQueryTest() {
        given().when().get("/posts/150")
                .then().statusCode(404);
    }


    @Test
    public void postNewObjectTest() {
        Post post = new Post();
        post.setUserId("777");
        post.setId("777");
        post.setTitle("my first post");
        post.setBody("my new first object");

        given().body(post)
                .when()
                .contentType(ContentType.JSON)
                .post("/posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void updateObjectUsingPut() {
        Post post = new Post();
        post.setUserId("888");
        post.setId("888");
        post.setTitle("my updated first post");
        post.setBody("my new updated first object");

        given().body(post)
                .when()
                .contentType(ContentType.JSON)
                .put("/posts/888");
    }

    @Test
    public void deleteObjectByIdTest() {
        given().pathParam("id" , 1)
                .when().delete("/posts/{id}")
                .then().statusCode(200);
    }


}
