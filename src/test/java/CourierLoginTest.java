import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    String login = "OldGnome3";
    String password = "og123";
    String firstName = "Zanuda3";
    String wrongLogin = "YoungGnome";
    String wrongPassword = "og1234";


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";

        NewCourier newCourier = new NewCourier(login,password,firstName);
        given()
                .header("Content-type", "application/json")
                .auth().oauth2("")
                .and()
                .body(newCourier)
                .when()
                .post("/api/v1/courier");
    }



    @Test
    @DisplayName("Check courier login")
    @Description("Проверка, что логина курьера со всеми валидными параметрами")
    public void authorizationNewCourierPositiveTest(){
        Courier courier = new Courier(login,password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");

        response
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check courier login with wrong password")
    @Description("Проверка, что при логине курьера с неверным паролем статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongPassCheck(){
        Courier courier = new Courier(login,wrongPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");

        response
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login with wrong login")
    @Description("Проверка, что при логине курьера с неверным логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginCheck(){
        Courier courier = new Courier(wrongLogin,password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");

        response
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test    @DisplayName("Check courier login with wrong password & logpn")
    @Description("Проверка, что при логине курьера с неверным паролем и логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginWrongPasswordCheck(){
        Courier courier = new Courier(wrongLogin,wrongPassword);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");

        response
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }



    @Test
    @DisplayName("Check courier login without login")
    @Description("Проверка, что при логине курьера без логина статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutLoginCheck(){
        Courier courier = new Courier(null,password);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");

        response
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check courier login without password")
    @Description("Проверка, что при логине курьера без пароля статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutPasswordCheck(){
        Courier courier = new Courier(login,"");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login");

        response
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }



    @After
    public void deleteNewCourier() {
        Courier courier = new Courier(login,password);
        CourierResponse courierResponse =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(courier)
                        .when()
                        .post("/api/v1/courier/login")
                        .body()
                        .as(CourierResponse.class);

        given()
                .auth().oauth2("")
                .delete("/api/v1/courier/" + courierResponse.getId());

    }

}
