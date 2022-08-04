package com.phucviet.authorizationserver.service.impl;

import com.phucviet.authorizationserver.model.entity.Role;
import com.phucviet.authorizationserver.model.entity.User;
import com.phucviet.authorizationserver.model.enums.ERole;
import com.phucviet.authorizationserver.model.enums.ESocialProvider;
import com.phucviet.authorizationserver.model.request.SignUpRequest;
import com.phucviet.authorizationserver.model.response.MessageResponse;
import com.phucviet.authorizationserver.reponsitory.PermissionRepository;
import com.phucviet.authorizationserver.reponsitory.RoleRepository;
import com.phucviet.authorizationserver.reponsitory.UserRepository;
import com.phucviet.authorizationserver.service.AuthenticationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class AuthenticationServiceImpl implements AuthenticationService {

  @Autowired private UserRepository userRepository;

  @Autowired private RoleRepository roleRepository;

  @Autowired private PermissionRepository permissionRepository;

  @Autowired private PasswordEncoder encoder;

  @Override
  public ResponseEntity<?> singUpAccount(SignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Username is already taken!"));
    }
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity.badRequest()
          .body(new MessageResponse("Error: Email is already in use!"));
    }
    // Create new user's account
    User user =
        User.builder()
            .username(signUpRequest.getUsername())
            .email(signUpRequest.getEmail())
            .password(encoder.encode(signUpRequest.getPassword()))
            .provider(ESocialProvider.local)
            .enabled(true)
            .accountNonExpired(true)
            .credentialsNonExpired(true)
            .accountNonLocked(true)
            .build();
    List<String> strRoles = signUpRequest.getRoles();
    List<Role> roles = new ArrayList<>();
    if (strRoles == null) {
      Role userRole =
          roleRepository
              .findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(
          role -> {
            switch (role) {
              case "admin":
                Role adminRole =
                    roleRepository
                        .findByName(ERole.ROLE_ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(adminRole);
                break;
              case "operator":
                Role modRole =
                    roleRepository
                        .findByName(ERole.ROLE_OPERATOR)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(modRole);
                break;
              default:
                Role userRole =
                    roleRepository
                        .findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
          });
    }
    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @Override
  public User getCurrentUser(Integer id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new RuntimeException("User not exist: " + id));
  }
}
