package ru.praktikum;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.config.RestConfig;
import ru.praktikum.steps.OrderSteps;

public class GettingListOrdersTest {

    private OrderSteps orderSteps = new OrderSteps();
    @Before
    @Step("Подготовка к тестам. Описание BaseURI.")
    public void setUp() {
        RestAssured.baseURI = RestConfig.HOST;
    }

    @Test
    @DisplayName("Проверка наличия списка заказов. Ожидаемый результат 200")
    public void getOrderList_ShouldReturnCorrectResult200(){
        orderSteps
                .getOrder()
                .statusCode(200)
                .body("orders", CoreMatchers.notNullValue());
    }
}
