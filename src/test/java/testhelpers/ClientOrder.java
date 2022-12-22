package testhelpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static testhelpers.Endpoints.API_ORDERS;

public class ClientOrder {


    public Response getOrderList() {
        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body("")
                        .when()
                        .get(API_ORDERS);
        return response;
    }

    public Response createOrder(NewOrder newOrder) {
        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newOrder)
                        .when()
                        .post(API_ORDERS);
        return response;
//        System.out.println(response.asString());

    }


}
