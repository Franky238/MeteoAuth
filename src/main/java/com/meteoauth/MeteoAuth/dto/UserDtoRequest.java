package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDtoRequest {
    public String username;
    @NotBlank
    public String password;
    @Email
    @NotBlank
    public String email;
    public String city;
}
