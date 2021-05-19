package com.meteoauth.MeteoAuth.assembler;

import com.meteoauth.MeteoAuth.dto.UserDtoRequest;
import com.meteoauth.MeteoAuth.dto.UserDtoResponse;
import com.meteoauth.MeteoAuth.entities.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserAssembler {
    public UserDtoResponse getUserDtoResponse(User user) {
        UserDtoResponse userDtoResponse = new UserDtoResponse();
        userDtoResponse.setId(user.getId());
        userDtoResponse.setFname(user.getFname());
        userDtoResponse.setLname(user.getLname());
        userDtoResponse.setEmail(user.getEmail());
        userDtoResponse.setCity(user.getCity());
        return userDtoResponse;
    }

    public User getUser(UserDtoRequest userDtoRequest) {
        User user = new User();
        user.setFname(userDtoRequest.getFname());
        user.setFname(userDtoRequest.getLname());
        user.setPassword(userDtoRequest.getPassword());
        user.setEmail(userDtoRequest.getEmail());
        user.setCity(userDtoRequest.getCity());
        return user;
    }

    public List<UserDtoResponse> getUserDtoRequestList(Iterable<User> userList) {
        List<UserDtoResponse> userDtoResponseList = new ArrayList<>();
        for (User user : userList) {
            UserDtoResponse temp = new UserDtoResponse();
            temp.setFname(user.getFname());
            temp.setLname(user.getLname());
            temp.setEmail(user.getEmail());
            temp.setCity(user.getCity());
            userDtoResponseList.add(temp);
        }
        return userDtoResponseList;
    }
}
