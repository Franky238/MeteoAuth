package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StationsDtoResponse {
    private Long id;
    private String destination;
    private String model_description;
    private Timestamp registration_time;
    private short phone;
}
