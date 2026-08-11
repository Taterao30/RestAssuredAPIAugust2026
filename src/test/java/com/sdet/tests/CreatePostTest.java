package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.client.PostsClient;
import com.sdet.models.PostRequest;
import com.sdet.models.PostResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.sdet.specs.ResponseSpecFactory.successResponse;

public class CreatePostTest extends BaseTest {

    private final PostsClient postsClient = new PostsClient();

    @Test(groups = {"smoke", "regression"})
    public void shouldCreatePost() {

        PostRequest payload =
                new PostRequest("Rest Assured Framework", "Created from API automation", 10);

        PostResponse response = postsClient.createPost(payload)
                .then()
                .spec(successResponse(201))
                .extract()
                .as(PostResponse.class);

        Assert.assertTrue(response.getId() > 0);
        Assert.assertEquals(response.getTitle(), payload.getTitle());
        Assert.assertEquals(response.getBody(), payload.getBody());
        Assert.assertEquals(response.getUserId(), payload.getUserId());
    }
}
