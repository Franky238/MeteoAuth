package com.meteoauth.MeteoAuth.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class UserDtoRequest {
    @NotBlank
    public String fname;
    @NotBlank
    public String lname;
    @NotBlank
    public String password; //todo
    @Email
    public String email;
    public String city;
}
