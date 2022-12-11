import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

        private final String firstName;
        private final String lastName;
        private final String address;
        private final String metroStation;
        private final String phone;
        private final int rentTime;
        private final String deliveryDate;
        private final String comment;
        private final String[]  color;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Выбор цвета: {7}")
    public static Object[][] getCredentials() {
        return new Object[][] {
                { "Максим" , "Иванов", "Москва, Красная Площадь д.1", "Коломенская", "+7955555555555", 5, "2022-12-15", "Черный", new String[] {"BLACK"}},
                { "Максим" , "Иванов", "Москва, Красная Площадь д.1", "Коломенская", "+7955555555555", 5, "2022-12-15", "Серый", new String[] {"GREY"}},
                { "Максим" , "Иванов", "Москва, Красная Площадь д.1", "Коломенская", "+7955555555555", 5, "2022-12-15","Черный и серый", new String[] {"BLACK", "GREY"} },
                { "Максим" , "Иванов", "Москва, Красная Площадь д.1", "Коломенская", "+7955555555555", 5, "2022-12-15", "Цвет не указан", new String[] {""} },
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Parameterize tests for create orders")
    @Description("Параметризированный тест для создания заказов с разными вариантами параметра цвета самоката")
    public void createOrderPositiveTest(){
        NewOrder newOrder = new NewOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newOrder)
                        .when()
                        .post("/api/v1/orders");
//        System.out.println(response.asString());

        response
                .then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(201);

    }

}
