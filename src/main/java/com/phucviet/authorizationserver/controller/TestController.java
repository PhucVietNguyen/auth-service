package com.phucviet.authorizationserver.controller;

import com.phucviet.authorizationserver.model.entity.User;
import com.phucviet.authorizationserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  // Todo Test get data from database before config oauth2 login, logout, social, local
//  @Autowired private UserService userService;
//
//  @GetMapping("/get-user/{name}")
//  public User testApi1(@PathVariable String name) {
//    return userService.findByUsername(name);
//  }
//
//  @GetMapping("/get-user/{id}")
//  public User testApi2(@PathVariable Long id) {
//    return userService.findByUsername(String.valueOf(id));
//  }
}
