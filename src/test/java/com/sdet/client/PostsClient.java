package com.sdet.client;

import com.sdet.models.PostRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PostsClient {

    public Response getPost(int id) {
        return given()
                .pathParam("id", id)
                .when()
                .get("/posts/{id}");
    }

    public Response getPostsByUser(int userId) {
        return given()
                .queryParam("userId", userId)
                .when()
                .get("/posts");
    }

    public Response createPost(PostRequest payload) {
        return given()
                .body(payload)
                .when()
                .post("/posts");
    }

    public Response updatePost(int id, PostRequest payload) {
        return given()
                .pathParam("id", id)
                .body(payload)
                .when()
                .put("/posts/{id}");
    }

    public Response deletePost(int id) {
        return given()
                .pathParam("id", id)
                .when()
                .delete("/posts/{id}");
    }
}
