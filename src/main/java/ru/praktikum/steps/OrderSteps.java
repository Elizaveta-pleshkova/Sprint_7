package ru.praktikum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.OrderCreateReqest;
import ru.praktikum.model.OrderResponse;

import java.util.List;

import static ru.praktikum.config.EndPoint.*;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    @Step("Отправка запроса на создание заказа")
    public Response createOrder(String firstName, String lastName, String address, String metroStation,
                                String phone, Integer rentTime, String deliveryDate, String comment,
                                List<String> color){
        OrderCreateReqest orderCreateReqest = new OrderCreateReqest();
        orderCreateReqest.setFirstName(firstName);
        orderCreateReqest.setLastName(lastName);
        orderCreateReqest.setAddress(address);
        orderCreateReqest.setMetroStation(metroStation);
        orderCreateReqest.setPhone(phone);
        orderCreateReqest.setRentTime(rentTime);
        orderCreateReqest.setDeliveryDate(deliveryDate);
        orderCreateReqest.setComment(comment);
        orderCreateReqest.setColor(color);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderCreateReqest)
                .when()
                .post(ORDERS_URL)
                ;
    }

    @Step("Получение Track номера заказа")
    public Integer getTrackOrder(Response response){
        return response.body().as(OrderResponse.class).getTrack();
    }

    @Step("Отправка запроса на удаление заказа")
    public ValidatableResponse deleteOrder(Integer track) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setTrack(track);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderResponse)
                .when()
                .delete(DELETE_ORDERS_URL)
                .then();
    }

    @Step("Отправка запроса на получение списка заказов")
    public ValidatableResponse getOrder(){
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(ORDERS_URL)
                .then();
    }
}
