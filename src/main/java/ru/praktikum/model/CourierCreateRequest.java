package ru.praktikum.model;

import lombok.*;

@Data
public class CourierCreateRequest {
    private String login;
    private String password;
    private String firstName;
}
