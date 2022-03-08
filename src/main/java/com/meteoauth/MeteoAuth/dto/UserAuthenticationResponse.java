package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserAuthenticationResponse implements Serializable {
    private final String jwt;
    private final String refreshToken;
}
