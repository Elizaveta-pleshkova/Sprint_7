package ru.praktikum;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.is;

public class AddCourierTest {

    private String login;
    private String password;
    private String firstName;
    private CourierSteps courierSteps = new CourierSteps();
    //CourierCreateRequest courierCreateRequest;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);

    }

    @Test
    public void createNewCourier_ShouldReturnCorrectResult201(){

        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    public void createTwoNewCourier_ShouldReturnIncorrectResult409(){

        courierSteps
                .createCourier(login, password, firstName);

        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(409);
    }

    @Test
    public void createNewCourierNotLogin_ShouldReturnIncorrectResult400(){

        courierSteps
                .createCourier(null, password, firstName)
                .statusCode(400);
    }

    @Test
    public void createNewCourierNotPassword_ShouldReturnIncorrectResult400(){

        courierSteps
                .createCourier(login, null, firstName)
                .statusCode(400);
    }

    @Test
    public void createNewCourierNotPasswordAndLogin_ShouldReturnIncorrectResult400(){

        courierSteps
                .createCourier(null, null, firstName)
                .statusCode(400);
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
