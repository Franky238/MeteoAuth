package com.meteoauth.MeteoAuth.oAuth2;

import lombok.Value;

@Value
public class ApiResponse {
	private Boolean success;
	private String message;
}