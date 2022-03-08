package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

@Data
public class MeasuredValuesDtoRequest {
    private int humidity;
    private int temperature;
    private int air_quality;
    private int wind_speed;
    private int wind_gusts;
    private int wind_direction;
    private int rainfall;
}
