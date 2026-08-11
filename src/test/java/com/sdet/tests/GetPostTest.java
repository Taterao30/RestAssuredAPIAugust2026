package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.client.PostsClient;
import org.testng.annotations.Test;

import static com.sdet.specs.ResponseSpecFactory.successResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetPostTest extends BaseTest {

    private final PostsClient postsClient = new PostsClient();

    @Test(groups = {"smoke", "regression"})
    public void shouldGetPostById() {

        postsClient.getPost(1)
                .then()
                .spec(successResponse(200))
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", notNullValue());
    }

    @Test(groups = {"regression"})
    public void shouldFilterPostsUsingQueryParameter() {

        postsClient.getPostsByUser(1)
                .then()
                .spec(successResponse(200))
                .body("[0].userId", equalTo(1));
    }
}
