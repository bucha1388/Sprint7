package testhelpers;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static testhelpers.Endpoints.API_CREATE;
import static testhelpers.Endpoints.API_LOGIN;

public class ClientCourier {


    public Response createCourier(NewCourier newCourier) {

        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post(API_CREATE);

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
                        .post(API_LOGIN);

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
                        .post(API_LOGIN)
                        .body()
                        .as(CourierResponse.class);

//       System.out.println(courierResponse.getId());

                given()
                .auth().oauth2("")
                .delete( API_CREATE + "/" + courierResponse.getId());

    }
}
