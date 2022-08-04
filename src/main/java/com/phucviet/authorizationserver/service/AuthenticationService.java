package com.phucviet.authorizationserver.service;

import com.phucviet.authorizationserver.model.entity.User;
import com.phucviet.authorizationserver.model.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

  ResponseEntity<?> singUpAccount(SignUpRequest signupRequest);

  User getCurrentUser(Integer id);
}
