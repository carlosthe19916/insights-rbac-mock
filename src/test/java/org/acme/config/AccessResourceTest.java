package org.acme.config;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
public class AccessResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when()
                .header("x-rh-identity", "myHeader")
                .get("/api/rbac/v1/access?application=migration-analytics")
          .then()
             .statusCode(200)
             .body(is(notNullValue()));
    }

}
