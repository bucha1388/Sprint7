package TestHelpers;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClientCourier {
    Hands hand = new Hands();

    public Response createCourier(NewCourier newCourier) {

        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post(hand.apiCreateCourier);

        return response;

    }


    public Response loginCourier(NewCourier newCourier) {

        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post(hand.apiCourierLogin);

        return response;

    }

    public void deleteCourier(NewCourier newCourier) {
        CourierResponse courierResponse =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post(hand.apiCourierLogin)
                        .body()
                        .as(CourierResponse.class);

//       System.out.println(courierResponse.getId());

                given()
                .auth().oauth2("")
                .delete( hand.apiCreateCourier + "/" + courierResponse.getId());


    }
}
