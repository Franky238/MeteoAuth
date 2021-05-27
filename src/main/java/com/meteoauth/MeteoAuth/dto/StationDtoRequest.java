package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

@Data
public class StationDtoRequest {
    private String title;
    private String destination;
    private String model_description;
    private String phone;
}
