package TestHelpers;

import TestHelpers.Hands;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ClientOrder {

    Hands hand = new Hands();

    public Response getOrderList() {
        Response response =
                        given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body("")
                        .when()
                        .get("/api/v1/orders");
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
                        .post(hand.apiOrders);
        return response;
//        System.out.println(response.asString());

    }

    public static class Courier {

        private String login;
        private String password;

        public Courier(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }
}
