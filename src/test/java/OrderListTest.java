import testhelpers.ClientOrder;
import testhelpers.Endpoints;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    Endpoints endPoint = new Endpoints();
    ClientOrder orders = new ClientOrder();

    @Before
    public void setUp() {
        RestAssured.baseURI = endPoint.BASE;
   }


    @Test
    @DisplayName("Check status code of /api/v1/orders and orders in JSON not null")
    @Description("Проверка , что в тело ответа возвращается список заказов.")
    public void checkOrderLisIsNotEmpty(){
        orders.getOrderList()
                .then().assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

}
