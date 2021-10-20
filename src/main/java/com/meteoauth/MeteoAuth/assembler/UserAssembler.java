package com.meteoauth.MeteoAuth.assembler;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class UserAssembler {
    private final PasswordEncoder passwordEncoder;
    //private final PasswordEncoder passwordEncoder;

    public UserAssembler() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    public UserDtoResponse getUserDtoResponse(User user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setUsername(user.getUsername());
        userDtoResponse.setEmail(user.getEmail());
        userDtoResponse.setCity(user.getCity());
        return userDtoResponse;
    }

    public User getUser(UserDtoRequest userDtoRequest) {
        User user = new User();
        user.setUsername(userDtoRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));
        user.setEmail(userDtoRequest.getEmail());
        user.setCity(userDtoRequest.getCity());

        return user;
    }

    public List<UserDtoResponse> getUserDtoRequestList(Iterable<User> userList) {
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();
        for (User user : userList) {
            UserDtoResponse temp = new UserDtoResponse();
            temp.setUsername(user.getUsername());
            temp.setEmail(user.getEmail());
            temp.setCity(user.getCity());
            userDtoResponseList.add(temp);
        }
        return userDtoResponseList;
    }

//    public User getGoogleUser(String idToken) {
//        User user = new User();
//        user.setUsername(userDtoRequest.getUsername());
//        user.setPassword(passwordEncoder.encode(userDtoRequest.getPassword()));
//        user.setEmail(userDtoRequest.getEmail());
//        user.setCity(userDtoRequest.getCity());
//
//
//        GoogleIdToken.Payload payload = idToken.getPayload();
//
//        // Print user identifier
//        String userId = payload.getSubject();
//        System.out.println("User ID: " + userId);
//
//        // Get profile information from payload
//        String email = payload.getEmail();
//        boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
//        String name = (String) payload.get("name");
//        String pictureUrl = (String) payload.get("picture");
//        String locale = (String) payload.get("locale");
//        String familyName = (String) payload.get("family_name");
//        String givenName = (String) payload.get("given_name");
//
//        // Use or store profile information
//
//        System.out.print(name);
//
//        return user;
//    }
}
