package com.ecomm.dev.productservice;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.TestcontainersConfiguration;

import io.restassured.RestAssured;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(
classes = com.ecomm.dev.productservice.ProductServiceApplication.class,    
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

    @LocalServerPort
    private Integer port;

    @BeforeEach    
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mongoDBContainer.start();
        System.setProperty("spring.data.mongodb.uri", mongoDBContainer.getReplicaSetUrl());
    }

	@Test
	void shouldCreateProduct() {

        String requestBody = """
        {
            "id": "103",
            "name": "iPhone 150",
            "description": "Latest model of iPhone 150",
            "price": 999.99
        }
        """;
        RestAssured.given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .when()
            .post("product-service/api/product/createproduct")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("id", Matchers.notNullValue())
            .body("name", Matchers.equalTo("iPhone 150"))
            .body("description", Matchers.equalTo("Latest model of iPhone 150"))
            .body("price", Matchers.equalTo(999.99f));
            ;
	}

}
