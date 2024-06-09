package ru.praktikum;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.CourierSteps;

public class LoginCourierTest {

    private String login;
    private String password;

    private String randomLogin;
    private String randomPassword;
    private CourierSteps courierSteps = new CourierSteps();

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    public void loginCourier_ShouldReturnCorrectResult200() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier(login, password)
                .statusCode(200)
                .body("id", CoreMatchers.notNullValue());
    }

    @Test
    public void loginCourierNotPassword_ShouldReturnIncorrectResult400() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier(login, "")
                .statusCode(400);
    }

    @Test
    public void loginCourierNotLogin_ShouldReturnIncorrectResult400() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier("", password)
                .statusCode(400);
    }

    @Test
    public void loginCourierNotLoginAndPassword_ShouldReturnIncorrectResult400() {
        courierSteps
                .createCourier(login, password, null);
        courierSteps
                .loginCourier("", "")
                .statusCode(400);
    }


    @Test
    public void loginCourierInvalidPassword_ShouldReturnIncorrectResult404() {
        courierSteps
                .createCourier(login, password, null);
        randomPassword = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .loginCourier(login, randomPassword)
                .statusCode(404);
    }

    @Test
    public void loginCourierInvalidLogin_ShouldReturnIncorrectResult404() {
        courierSteps
                .createCourier(login, password, null);
        randomLogin = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .loginCourier(randomLogin, password)
                .statusCode(404);
    }


    @Test
    public void loginCourierNonExistentPasswordLogin_ShouldReturnIncorrectResult404() {
        courierSteps
                .createCourier(login, password, null);
        randomLogin = RandomStringUtils.randomAlphabetic(10);
        randomPassword = RandomStringUtils.randomAlphabetic(10);
        courierSteps
                .loginCourier(randomLogin, randomPassword)
                .statusCode(404);
    }

    @After
    public void tearDown(){
        Integer id = courierSteps.loginCourier(login, password)
                .extract().body().path("id");
        if (id != null){
            courierSteps.deleteCourier(id);
        }
    }
}
