package ru.yandex.praktikum;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Suite;
import ru.yandex.praktikum.model.OrderResponse;
import ru.yandex.praktikum.steps.CourierSteps;
import ru.yandex.praktikum.steps.OrderSteps;

import java.util.List;

@RunWith(Parameterized.class)
public class AddOrdersTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;
    private Integer track;

    private OrderSteps orderSteps = new OrderSteps();

    public AddOrdersTest(List<String> color){
        this.color = color;
    }

    @Parameterized.Parameters(name = "Color: {0}")
    public static Object[][] data(){
        return new Object[][]{
                {List.of()},
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
        firstName = "testName";
        lastName = "testLastName";
        address = "Moscow, 124";
        metroStation = "4";
        phone = "+7 800 355 35 35";
        rentTime = 6;
        deliveryDate = "2024-06-15";
        comment = "Hello";
    }

    @Test
    public void createOrder_ShouldReturnCorrectResult201(){
        Response response = orderSteps
                .createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        this.track = orderSteps.getTrackOrder(response);
        response.then().body("track", CoreMatchers.notNullValue())
                        .and()
                       .statusCode(201);
    }

    @After
    public void tearDown(){
        if (track != null){
            orderSteps.deleteOrder(track);}
    }
}
