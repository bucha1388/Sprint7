package testhelpers;

import static io.restassured.RestAssured.given;

public class TestsData {

    public static final String LOGIN = "OldGnome_" + (10000 + (int)(Math.random() *99999));
    public static final String PASSWORD = "og"+ (100000 + (int)(Math.random() *999999));
    public static final String WRONG_PASSWORD = "wrong"+ (100000 + (int)(Math.random() *999999));
    public static final String WRONG_LOGIN = "Girl_" + (10000 + (int)(Math.random() *99999));
    public static final String FIRST_NAME = "Zanuda";

}
