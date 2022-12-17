package testhelpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClientOrder {

    Endpoints endPoint = new Endpoints();

    public Response getOrderList() {
        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body("")
                        .when()
                        .get(endPoint.API_ORDERS);
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
                        .post(endPoint.API_ORDERS);
        return response;
//        System.out.println(response.asString());

    }


}
