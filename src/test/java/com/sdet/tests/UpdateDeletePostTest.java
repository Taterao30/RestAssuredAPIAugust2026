package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.client.PostsClient;
import com.sdet.models.PostRequest;
import org.testng.annotations.Test;

import static com.sdet.specs.ResponseSpecFactory.successResponse;
import static org.hamcrest.Matchers.equalTo;

public class UpdateDeletePostTest extends BaseTest {

    private final PostsClient postsClient = new PostsClient();

    @Test(groups = {"regression"})
    public void shouldUpdatePost() {

        PostRequest payload =
                new PostRequest("Updated title", "Updated body", 1);

        postsClient.updatePost(1, payload)
                .then()
                .spec(successResponse(200))
                .body("id", equalTo(1))
                .body("title", equalTo(payload.getTitle()))
                .body("body", equalTo(payload.getBody()));
    }

    @Test(groups = {"regression"})
    public void shouldDeletePost() {

        postsClient.deletePost(1)
                .then()
                .spec(successResponse(200));
    }
}
