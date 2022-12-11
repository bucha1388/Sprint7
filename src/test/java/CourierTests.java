import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class CourierTests {

    String login = "OldGnome3";
    String password = "og123";
    String firstName = "Zanuda3";


    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier and JSON contains true")
    @Description("Проверка, что со всеми параметрами курьер создается")
    public void createNewCourierPositiveTest(){
        NewCourier newCourier = new NewCourier(login,password,firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Check error when create courier copy")
    @Description("Проверка, что при создании дубля курьера , курьер не создается, статус 409 и нужное сообщение об ошибке")
    public void createNewCourierDoubleRegTest(){
        NewCourier newCourier = new NewCourier(login,password,firstName);
                    given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response
                .then().assertThat().body("message", equalTo("Этот логин уже используется."))
                .and()
                .statusCode(409);
    }



    @Test
    @DisplayName("Check error when create courier without login")
    @Description("Проверка, что при попытке создания курьера без указания логина , курьер не создается, статус 400 и нужное сообщение об ошибке")
    public void createNewCourierWithoutLoginTest(){
        NewCourier newCourier = new NewCourier("",password,firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check error when create courier without password")
    @Description("Проверка, что при попытке создания курьера без указания пароля , курьер не создается, статус 400 и нужное сообщение об ошибке")
    public void createNewCourierWithoutPasswordTest(){
        NewCourier newCourier = new NewCourier(login,"",firstName);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check error when create courier without firstName")
    @Description("Проверка, что при попытке создания курьера без указания имени, курьер не создается, статус 400 и нужное сообщение об ошибке. Надо учитывать что требований по этому полю НЕТ!")
    public void createNewCourierWithoutFirstNameTest(){
        NewCourier newCourier = new NewCourier(login,password,null);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2("")
                        .and()
                        .body(newCourier)
                        .when()
                        .post("/api/v1/courier");
        response
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
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

     //    System.out.println(courierResponse.getId());

        given()
                .auth().oauth2("")
                .delete("/api/v1/courier/" + courierResponse.getId());

           }

}

