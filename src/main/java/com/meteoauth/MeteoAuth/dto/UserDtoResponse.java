package com.meteoauth.MeteoAuth.dto;

import lombok.Data;


@Data
public class UserDtoResponse {
    public Long id;
    public String username;
    public String email;
    public String city;
}
