package ru.praktikum;

import io.restassured.RestAssured;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.steps.OrderSteps;

public class GettingListOrdersTest {

    private OrderSteps orderSteps = new OrderSteps();
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void getOrderList_ShouldReturnCorrectResult200(){
        orderSteps
                .getOrder()
                .statusCode(200)
                .body("orders", CoreMatchers.notNullValue());
    }
}
