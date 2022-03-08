package com.meteoauth.MeteoAuth.dto;

import lombok.Value;

import java.util.List;

@Value
public class UserInfo {
    String id, displayName, email;
    List<String> roles;
}