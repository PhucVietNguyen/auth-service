package com.phucviet.authorizationserver.controller;

import com.phucviet.authorizationserver.model.entity.User;
import com.phucviet.authorizationserver.model.entity.UserDetailsImpl;
import com.phucviet.authorizationserver.model.request.SignUpRequest;
import com.phucviet.authorizationserver.security.CurrentUser;
import com.phucviet.authorizationserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired private AuthenticationService authenticationService;

  @PostMapping("/sing-up")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    return authenticationService.singUpAccount(signUpRequest);
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser UserDetailsImpl userDetails) {
    return authenticationService.getCurrentUser(userDetails.getId());
  }
}
