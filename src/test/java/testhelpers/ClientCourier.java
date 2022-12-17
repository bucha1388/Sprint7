package testhelpers;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ClientCourier {
    Endpoints endPoint = new Endpoints();

    public Response createCourier(NewCourier newCourier) {

        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post(endPoint.API_CREATE);

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
                        .post(endPoint.API_LOGIN);

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
                        .post(endPoint.API_LOGIN)
                        .body()
                        .as(CourierResponse.class);

//       System.out.println(courierResponse.getId());

                given()
                .auth().oauth2("")
                .delete( endPoint.API_CREATE + "/" + courierResponse.getId());


    }
}
