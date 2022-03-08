package com.meteoauth.MeteoAuth.dto;

import lombok.Value;

@Value
public class JwtAuthenticationResponse {
	String accessToken;
	UserInfo user;
}