package com.phucviet.authorizationserver.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class SignUpRequest {

  @NotNull @NotBlank private String username;

  @NotNull @NotBlank @Email private String email;

  @NotNull @NotBlank private String password;

  @NotNull private List<String> roles;
}
