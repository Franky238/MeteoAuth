package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String jwt;
}
