package org.gfa.greenbay.controllers;

import org.gfa.greenbay.dtos.ErrorDTO;
import org.gfa.greenbay.exceptions.LoginInformationMissingException;
import org.gfa.greenbay.exceptions.LoginInformationWrongException;
import org.gfa.greenbay.exceptions.PasswordMissingException;
import org.gfa.greenbay.exceptions.UsernameMissingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GreenbayExceptionHandler {

  @ExceptionHandler(value = PasswordMissingException.class)
  public ResponseEntity<ErrorDTO> passwordMissingExceptionHandler() {
    return ResponseEntity.badRequest().body(
        new ErrorDTO("Login information is required: password!")
    );
  }

  @ExceptionHandler(value = UsernameMissingException.class)
  public ResponseEntity<ErrorDTO> usernameMissingExceptionHandler() {
    return ResponseEntity.badRequest().body(
        new ErrorDTO("Login information is required: username!")
    );
  }

  @ExceptionHandler(value = LoginInformationMissingException.class)
  public ResponseEntity<ErrorDTO> loginInformationMissingExceptionHandler() {
    return ResponseEntity.badRequest().body(
        new ErrorDTO("Login information is required: username and password!")
    );
  }

  @ExceptionHandler(value = LoginInformationWrongException.class)
  public ResponseEntity<ErrorDTO> loginInformationWrongExceptionHandler() {
    return ResponseEntity.ok().body(new ErrorDTO("Incorrect username or password!"));
  }
}
