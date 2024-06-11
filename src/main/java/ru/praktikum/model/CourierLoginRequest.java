package ru.praktikum.model;
import lombok.*;

@Data
public class CourierLoginRequest {
    private String login;
    private String password;
}