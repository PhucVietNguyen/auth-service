package com.phucviet.authorizationserver.service;

import com.phucviet.authorizationserver.model.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  UserEntity findByUsername(String username);

  UserDetails loadUserById(Long id);
}
