package org.gfa.greenbay.controllers;

import org.gfa.greenbay.dtos.LoginRequestDTO;
import org.gfa.greenbay.dtos.LoginResponseDTO;
import org.gfa.greenbay.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final LoginService loginService;

  @Autowired
  public UserController(LoginService loginService) {
    this.loginService = loginService;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(
      @RequestBody(required = false) LoginRequestDTO loginRequest) {
    return ResponseEntity.ok(loginService.login(loginRequest));
  }

}
