package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class StationsDtoRequest {
    private String destination;
    private String model_description;
    private Timestamp registration_time;
    private short phone;
}
