package ru.praktikum.steps;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import ru.praktikum.model.OrderCreateReqest;
import ru.praktikum.model.OrderResponse;

import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {
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
                .post("/api/v1/orders")
                ;
    }

    public Integer getTrackOrder(Response response){
        return response.body().as(OrderResponse.class).getTrack();
    }

    public ValidatableResponse deleteOrder(Integer track) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setTrack(track);
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(orderResponse)
                .when()
                .delete("/api/v1/orders/cancel")
                .then();
    }

    public ValidatableResponse getOrder(){
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get("/api/v1/orders")
                .then();
    }
}
