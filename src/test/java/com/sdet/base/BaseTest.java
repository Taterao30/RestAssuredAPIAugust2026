package com.sdet.base;

import com.sdet.specs.RequestSpecFactory;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setupSuite() {
        RestAssured.requestSpecification = RequestSpecFactory.defaultRequestSpec();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }
}
