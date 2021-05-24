package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StationDtoRequest {
    private String title;
    private String destination;
    private String model_description;
    private Timestamp registration_time;
    private String phone;
}
