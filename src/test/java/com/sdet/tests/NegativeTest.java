package com.sdet.tests;

import com.sdet.base.BaseTest;
import com.sdet.client.PostsClient;
import org.testng.annotations.Test;

import static com.sdet.specs.ResponseSpecFactory.successResponse;

public class NegativeTest extends BaseTest {

    private final PostsClient postsClient = new PostsClient();

    @Test(groups = {"regression"})
    public void shouldReturn404ForMissingPost() {

        postsClient.getPost(999999)
                .then()
                .spec(successResponse(404));
    }
}
