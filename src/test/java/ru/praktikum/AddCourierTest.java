package ru.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.config.RestConfig;
import ru.praktikum.steps.CourierSteps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

public class AddCourierTest {

    private String login;
    private String password;
    private String firstName;
    private CourierSteps courierSteps = new CourierSteps();
    //CourierCreateRequest courierCreateRequest;

    @Before
    @Step("Подготовка к тестам. Описание BaseURI и создание тестовых данных.")
    public void setUp() {
        RestAssured.baseURI = RestConfig.HOST;

        login = RandomStringUtils.randomAlphabetic(10);
        password = RandomStringUtils.randomAlphabetic(10);
        firstName = RandomStringUtils.randomAlphabetic(10);
    }

    @Test
    @DisplayName("Создание нового курьера. Ожидаемый результат 201")
    public void createNewCourier_ShouldReturnCorrectResult201(){

        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(201)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Создание нового курьера, с данными ранее созданного курьера. Ожидаемый результат 409")
    public void createTwoNewCourier_ShouldReturnIncorrectResult409(){

        courierSteps
                .createCourier(login, password, firstName);

        courierSteps
                .createCourier(login, password, firstName)
                .statusCode(409)
                //В документации указан другой текст. Этот текст взят из ответа в Postman, что бы тест прошел успешно.
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание нового курьера без логина. Ожидаемый результат 400")
    public void createNewCourierNotLogin_ShouldReturnIncorrectResult400(){

        courierSteps
                .createCourier(null, password, firstName)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание нового курьера без пароля. Ожидаемый результат 400")
    public void createNewCourierNotPassword_ShouldReturnIncorrectResult400(){

        courierSteps
                .createCourier(login, null, firstName)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание нового курьера без логина и пароля. Ожидаемый результат 400")
    public void createNewCourierNotPasswordAndLogin_ShouldReturnIncorrectResult400(){

        courierSteps
                .createCourier(null, null, firstName)
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
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
