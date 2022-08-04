package com.phucviet.authorizationserver.service;

import com.phucviet.authorizationserver.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

  User findByUsername(String username);

  UserDetails loadUserById(Integer id);
}
