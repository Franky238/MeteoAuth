package com.meteoauth.MeteoAuth.dto;

import lombok.Data;


@Data
public class UserDtoResponse {
    public Long id;
    public String fname;
    public String lname;
    public String email;
    public String city;
}
