import TestHelpers.ClientCourier;
import TestHelpers.Hands;
import TestHelpers.NewCourier;
import TestHelpers.TestsData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {
    Hands hand = new Hands();
    ClientCourier clientCourier = new ClientCourier();
    TestsData data = new TestsData();
    NewCourier newCourier = new NewCourier(data.getLogin(), data.getPassword(), data.getFirstName());


    @Before
    public void setUp() {
        RestAssured.baseURI = hand.getBase();
        clientCourier.createCourier(newCourier);
    }



    @Test
    @DisplayName("Check courier login")
    @Description("Проверка, логина курьера со всеми валидными параметрами")
    public void authorizationNewCourierPositiveTest(){
        clientCourier.loginCourier(new NewCourier(data.getLogin(), data.getPassword(), data.getFirstName()))
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check courier login with wrong password")
    @Description("Проверка, что при логине курьера с неверным паролем статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongPassCheck(){
        clientCourier.loginCourier(new NewCourier(data.getLogin(), data.getWrongPassword(), data.getFirstName()))
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login with wrong login")
    @Description("Проверка, что при логине курьера с неверным логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginCheck(){
        clientCourier.loginCourier(new NewCourier(data.getWrongLogin(), data.getPassword(), data.getFirstName()))
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test    @DisplayName("Check courier login with wrong password & logpn")
    @Description("Проверка, что при логине курьера с неверным паролем и логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginWrongPasswordCheck(){
        clientCourier.loginCourier(new NewCourier(data.getWrongLogin(), data.getWrongPassword(), data.getFirstName()))
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login without login")
    @Description("Проверка, что при логине курьера без логина статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutLoginCheck(){
        clientCourier.loginCourier(new NewCourier(null, data.getPassword(), data.getFirstName()))
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check courier login without password")
    @Description("Проверка, что при логине курьера без пароля статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutPasswordCheck(){
        clientCourier.loginCourier(new NewCourier(data.getLogin(), "", data.getFirstName()))
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }


    @After
    public void deleteNewCourier() {
        clientCourier.deleteCourier(newCourier);
    }

}
