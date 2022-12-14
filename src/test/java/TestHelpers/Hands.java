package TestHelpers;

public class Hands {

    String base = "http://qa-scooter.praktikum-services.ru";
    String apiCreateCourier = "/api/v1/courier";
    String apiCourierLogin = "/api/v1/courier/login";
    String apiOrders = "/api/v1/orders";

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getApiCreateCourier() {
        return apiCreateCourier;
    }

    public void setApiCreateCourier(String apiCreateCourier) {
        this.apiCreateCourier = apiCreateCourier;
    }

    public String getApiCourierLogin() {
        return apiCourierLogin;
    }

    public void setApiCourierLogin(String apiCourierLogin) {
        this.apiCourierLogin = apiCourierLogin;
    }

    public String getApiOrders() {
        return apiOrders;
    }

    public void setApiOrders(String apiOrders) {
        this.apiOrders = apiOrders;
    }

}
