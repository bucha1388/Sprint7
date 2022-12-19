import testhelpers.ClientCourier;
import testhelpers.NewCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static testhelpers.Endpoints.BASE;
import static testhelpers.TestsData.*;

public class CourierLoginTest {

    ClientCourier clientCourier = new ClientCourier();
    NewCourier newCourierReal = new NewCourier(LOGIN, PASSWORD, FIRST_NAME);


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE;
        clientCourier.createCourier(newCourierReal);
    }



    @Test
    @DisplayName("Check courier login")
    @Description("Проверка, логина курьера со всеми валидными параметрами")
    public void authorizationNewCourierPositiveTest(){
        clientCourier.loginCourier(newCourierReal)
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(200);
    }

    @Test
    @DisplayName("Check courier login with wrong password")
    @Description("Проверка, что при логине курьера с неверным паролем статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongPassCheck(){
        NewCourier newCourier = new NewCourier(LOGIN, WRONG_PASSWORD, FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login with wrong login")
    @Description("Проверка, что при логине курьера с неверным логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginCheck(){
        NewCourier newCourier = new NewCourier(WRONG_LOGIN, PASSWORD, FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test    @DisplayName("Check courier login with wrong password & logpn")
    @Description("Проверка, что при логине курьера с неверным паролем и логином статус код 404 и соответствующее сообщение об ошибке")
    public void authorizationCourierWrongLoginWrongPasswordCheck(){
        NewCourier newCourier = new NewCourier(WRONG_LOGIN, WRONG_PASSWORD, FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(404);
    }

    @Test
    @DisplayName("Check courier login without login")
    @Description("Проверка, что при логине курьера без логина статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutLoginCheck(){
        NewCourier newCourier = new NewCourier(null, PASSWORD, FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check courier login without password")
    @Description("Проверка, что при логине курьера без пароля статус код 400 и соответствующее сообщение об ошибке")
    public void authorizationCourierWithoutPasswordCheck(){
        NewCourier newCourier = new NewCourier(LOGIN, "", FIRST_NAME);
        clientCourier.loginCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(400);
    }


    @After
    public void deleteNewCourier() {
        clientCourier.deleteCourier(newCourierReal);
    }

}
