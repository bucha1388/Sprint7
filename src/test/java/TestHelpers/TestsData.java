package TestHelpers;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TestsData {

    String login = "OldGnome_" + (10000 + (int)(Math.random() *99999));
    String password = "og"+ (100000 + (int)(Math.random() *999999));

    public String getWrongPassword() {
        return wrongPassword;
    }

    public void setWrongPassword(String wrongPassword) {
        this.wrongPassword = wrongPassword;
    }

    public String getWrongLogin() {
        return wrongLogin;
    }

    public void setWrongLogin(String wrongLogin) {
        this.wrongLogin = wrongLogin;
    }

    String wrongPassword = "wrong"+ (100000 + (int)(Math.random() *999999));

    String wrongLogin = "Girl_" + (10000 + (int)(Math.random() *99999));
    String firstName = "Zanuda";



    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


}
