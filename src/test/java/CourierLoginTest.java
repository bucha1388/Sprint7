import testhelpers.ClientCourier;
import testhelpers.Endpoints;
import testhelpers.NewCourier;
import testhelpers.TestsData;
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
    Endpoints endPoint = new Endpoints();
    ClientCourier clientCourier = new ClientCourier();
    TestsData data = new TestsData();
    NewCourier newCourier = new NewCourier(data.LOGIN, data.PASSWORD, data.FIRST_NAME);


    @Before
    public void setUp() {
        RestAssured.baseURI = endPoint.BASE;
        clientCourier.createCourier(newCourier);
    }



    @Test
    @DisplayName("Check courier login")
    @Description("Проверка, логина курьера со всеми валидными параметрами")
    public void authorizationNewCourierPositiveTest(){
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check courier login with wrong password")
    @Description("Проверка, что при логине курьера с неверным паролем статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongPassCheck(){
        NewCourier newCourier = new NewCourier(data.LOGIN, data.WRONG_PASSWORD, data.FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login with wrong login")
    @Description("Проверка, что при логине курьера с неверным логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginCheck(){
        NewCourier newCourier = new NewCourier(data.WRONG_LOGIN, data.PASSWORD, data.FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test    @DisplayName("Check courier login with wrong password & logpn")
    @Description("Проверка, что при логине курьера с неверным паролем и логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginWrongPasswordCheck(){
        NewCourier newCourier = new NewCourier(data.WRONG_LOGIN, data.WRONG_PASSWORD, data.FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login without login")
    @Description("Проверка, что при логине курьера без логина статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutLoginCheck(){
        NewCourier newCourier = new NewCourier(null, data.PASSWORD, data.FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check courier login without password")
    @Description("Проверка, что при логине курьера без пароля статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutPasswordCheck(){
        NewCourier newCourier = new NewCourier(data.LOGIN, "", data.FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }


    @After
    public void deleteNewCourier() {
        clientCourier.deleteCourier(newCourier);
    }

}
