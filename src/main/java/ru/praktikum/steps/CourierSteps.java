package ru.praktikum.steps;


import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.CourierCreateRequest;
import ru.praktikum.model.CourierLoginRequest;

import static io.restassured.RestAssured.given;
import static ru.praktikum.config.EndPoint.*;

public class CourierSteps {

    @Step("Отправка запроса на создание курьера")
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
                .post(CREATE_COURIER_URL)
                .then();
    }

    @Step("Отправка запроса на логин курьера")
    public ValidatableResponse loginCourier(String login, String password){
        CourierLoginRequest courierLoginRequest = new CourierLoginRequest();
        courierLoginRequest.setLogin(login);
        courierLoginRequest.setPassword(password);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courierLoginRequest)
                .when()
                .post(LOGIN_COURIER_URL)
                .then();
    }

    @Step("Отправка запроса на удаление курьера")
    public ValidatableResponse deleteCourier(Integer id) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .pathParams("id", id)
                .when()
                .delete(DELETE_COURIER_URL)
                .then();
    }

}
