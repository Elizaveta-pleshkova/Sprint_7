package ru.praktikum.steps;


import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.CourierCreateRequest;
import ru.praktikum.model.CourierLoginRequest;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    public ValidatableResponse createCourier(String login, String password, String firstName){
        CourierCreateRequest courierCreateRequest = new CourierCreateRequest();
        courierCreateRequest.setLogin(login);
        courierCreateRequest.setPassword(password);
        courierCreateRequest.setFirstName(firstName);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierCreateRequest)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    public ValidatableResponse loginCourier(String login, String password){
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest();
        courierLoginRequest.setLogin(login);
        courierLoginRequest.setPassword(password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLoginRequest)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    public ValidatableResponse deleteCourier(Integer id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .pathParams("id", id)
                .when()
                .delete("/api/v1/courier/{id}")
                .then();
    }

}
