import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }


    @Test
    @DisplayName("Check status code of /api/v1/orders and orders in JSON not null")
    @Description("Проверка , что в тело ответа возвращается список заказов.")
    public void authorizationNewCourierPositiveTest(){
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body("")
                        .when()
                        .get("/api/v1/orders");

        response
                .then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

}
