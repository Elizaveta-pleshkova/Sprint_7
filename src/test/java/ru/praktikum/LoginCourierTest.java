package ru.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.config.RestConfig;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;

public class LoginCourierTest {

    private String login;
    private String password;

    private String randomLogin;
    private String randomPassword;
    private CourierSteps courierSteps = new CourierSteps();

    @Before
    @Step("Подготовка к тестам. Описание BaseURI и создание тестовых данных.")
    public void setUp() {
        RestAssured.baseURI = RestConfig.HOST;
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @DisplayName("Логин курьера в система с верными данными. Ожидаемый результат 200")
    public void loginCourier_ShouldReturnCorrectResult200() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier(login, password)
                .statusCode(200)
                .body("id", CoreMatchers.notNullValue());
    }

    @Test
    @DisplayName("Логин курьера в система без указания пароля. Ожидаемый результат 400")
    public void loginCourierNotPassword_ShouldReturnIncorrectResult400() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier(login, "")
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера в система без указания логина. Ожидаемый результат 400")
    public void loginCourierNotLogin_ShouldReturnIncorrectResult400() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier("", password)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Логин курьера в система без указания пароля и логина. Ожидаемый результат 400")
    public void loginCourierNotLoginAndPassword_ShouldReturnIncorrectResult400() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier("", "")
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Логин курьера в система с неверным указанием пароля. Ожидаемый результат 404")
    public void loginCourierInvalidPassword_ShouldReturnIncorrectResult404() {
        courierSteps
                .createCourier(login, password, null);
        randomPassword = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .loginCourier(login, randomPassword)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Логин курьера в система с неверным указанием логина. Ожидаемый результат 404")
    public void loginCourierInvalidLogin_ShouldReturnIncorrectResult404() {
        courierSteps
                .createCourier(login, password, null);
        randomLogin = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .loginCourier(randomLogin, password)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }


    @Test
    @DisplayName("Логин курьера в система с неверным указанием пароля и логина. Ожидаемый результат 404")
    public void loginCourierNonExistentPasswordLogin_ShouldReturnIncorrectResult404() {
        courierSteps
                .createCourier(login, password, null);
        randomLogin = RandomStringUtils.randomAlphabetic(10);
        randomPassword = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .loginCourier(randomLogin, randomPassword)
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    @Step("Удаление созданного курьера")
    public void tearDown(){
        Integer id = courierSteps.loginCourier(login, password)
                .extract().body().path("id");
        if (id != null){
            courierSteps.deleteCourier(id);
        }
    }
}
