package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeasuredValuesDtoResponse {
    private Long id;
    private Timestamp measurement_time;
    private int humidity;
    private int temperature;
    private int air_quality;
    private int wind_speed;
    private int wind_gusts;
    private int wind_direction;
    private int rainfall;
}
