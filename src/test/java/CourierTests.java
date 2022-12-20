import testhelpers.ClientCourier;
import testhelpers.NewCourier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static testhelpers.Endpoints.BASE;
import static testhelpers.TestsData.*;


public class CourierTests {


   ClientCourier clientCourier = new ClientCourier();
    NewCourier newCourier = new NewCourier(LOGIN, PASSWORD, FIRST_NAME);
    NewCourier newCourierWithoutLogin = new NewCourier("", PASSWORD, FIRST_NAME);
    NewCourier newCourierWithoutPass = new NewCourier(LOGIN, "", FIRST_NAME);
    NewCourier newCourierWithoutFirstName = new NewCourier(LOGIN, PASSWORD,null);


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE;
    }

    @Test
    @DisplayName("Check status code of /api/v1/courier and JSON contains true")
    @Description("Проверка, что со всеми параметрами курьер создается")
    public void createNewCourierPositiveTest(){
                clientCourier.createCourier(newCourier)
                .then().assertThat().body("ok", equalTo(true))
                .and()
                .statusCode(201);
    }

    @Test
    @DisplayName("Check error when create courier copy")
    @Description("Проверка, что при создании дубля курьера , курьер не создается, статус 409 и нужное сообщение об ошибке")
    public void createNewCourierDoubleRegTest(){
        clientCourier.createCourier(newCourier);
        clientCourier.createCourier(newCourier)
                .then().assertThat().body("message", equalTo("Этот логин уже используется."))
                .and()
                .statusCode(409);
    }


    @Test
    @DisplayName("Check error when create courier without login")
    @Description("Проверка, что при попытке создания курьера без указания логина , курьер не создается, статус 400 и нужное сообщение об ошибке")
    public void createNewCourierWithoutLoginTest(){
        NewCourier newCourier = new NewCourier("", PASSWORD, FIRST_NAME);
        clientCourier.createCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check error when create courier without password")
    @Description("Проверка, что при попытке создания курьера без указания пароля , курьер не создается, статус 400 и нужное сообщение об ошибке")
    public void createNewCourierWithoutPasswordTest(){
        NewCourier newCourier = new NewCourier(LOGIN, "", FIRST_NAME);
                clientCourier.createCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }

    @Test
    @DisplayName("Check error when create courier without firstName")
    @Description("Проверка, что при попытке создания курьера без указания имени, курьер не создается, статус 400 и нужное сообщение об ошибке. Надо учитывать что требований по этому полю НЕТ!")
    public void createNewCourierWithoutFirstNameTest(){
        NewCourier newCourier = new NewCourier(LOGIN, PASSWORD,null);
        clientCourier.createCourier(newCourier)
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(400);
    }


    @After
    public void deleteNewCourier() {
        clientCourier.deleteCourier(newCourier);
           }

}

