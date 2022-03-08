package com.meteoauth.MeteoAuth.oAuth2;

import com.meteoauth.MeteoAuth.entities.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;


public interface UserService {
    User registerNewUser(SignUpRequest signUpRequest) throws UserAlreadyExistAuthenticationException;

    User findUserByEmail(String email);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes, OidcIdToken idToken, OidcUserInfo userInfo);
}
