package com.phucviet.authorizationserver.service.impl;

import com.phucviet.authorizationserver.model.entity.User;
import com.phucviet.authorizationserver.model.entity.UserDetailsImpl;
import com.phucviet.authorizationserver.reponsitory.UserRepository;
import com.phucviet.authorizationserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  @Autowired private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<User> optionalUser = userRepository.findByUsername(username);

    optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

    UserDetails userDetails = new UserDetailsImpl(optionalUser.get());
    new AccountStatusUserDetailsChecker().check(userDetails);
    return userDetails;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username).get();
  }

  @Override
  public UserDetails loadUserById(Integer id) {
    Optional<User> optionalUser = userRepository.findById(id);

    optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

    UserDetails userDetails = new UserDetailsImpl(optionalUser.get());
    new AccountStatusUserDetailsChecker().check(userDetails);
    return userDetails;
  }
}
