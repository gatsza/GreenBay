package org.gfa.greenbay.controllers;

import org.gfa.greenbay.dtos.ErrorDTO;
import org.gfa.greenbay.exceptions.AuctionCreationMultipleFieldMissingException;
import org.gfa.greenbay.exceptions.AuctionCreationPayloadMissingException;
import org.gfa.greenbay.exceptions.LoginInformationWrongException;
import org.gfa.greenbay.exceptions.LoginPayloadMissingException;
import org.gfa.greenbay.exceptions.MultipleLoginFieldMissingException;
import org.gfa.greenbay.exceptions.PhotoUrlNotValidException;
import org.gfa.greenbay.exceptions.TimeFormatIsNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GreenbayExceptionHandler {

  @ExceptionHandler(value = MultipleLoginFieldMissingException.class)
  public ResponseEntity<ErrorDTO> loginInformationMissingExceptionHandler(
      MultipleLoginFieldMissingException ex) {
    return ResponseEntity.badRequest().body(
        new ErrorDTO("Cannot login, additional information is required: "
            + ex.getMissingFields().toString())
    );
  }

  @ExceptionHandler(value = LoginPayloadMissingException.class)
  public ResponseEntity<ErrorDTO> loginPayloadMissingExceptionHandler() {
    return ResponseEntity.badRequest()
        .body(
            new ErrorDTO("Cannot login, additional information is required: [username, password"));
  }

  @ExceptionHandler(value = LoginInformationWrongException.class)
  public ResponseEntity<ErrorDTO> loginInformationWrongExceptionHandler() {
    return ResponseEntity.ok().body(new ErrorDTO("Incorrect username or password!"));
  }

  @ExceptionHandler(value = AuctionCreationMultipleFieldMissingException.class)
  public ResponseEntity<ErrorDTO> auctionCreationInformationMissingExceptionHandler(
      AuctionCreationMultipleFieldMissingException ex) {
    return ResponseEntity.badRequest().body(
        new ErrorDTO("Auction cannot created, because additional information is required: "
            + ex.getMissingFields().toString())
    );
  }

  @ExceptionHandler(value = AuctionCreationPayloadMissingException.class)
  public ResponseEntity<ErrorDTO> auctionCreationPayloadMissingExceptionHandler() {
    return ResponseEntity.badRequest()
        .body(new ErrorDTO("Auction cannot created, all field are missing!"));
  }

  @ExceptionHandler(value = PhotoUrlNotValidException.class)
  public ResponseEntity<ErrorDTO> photoUrlNotValidExceptionHandler() {
    return ResponseEntity.badRequest().body(new ErrorDTO("Photo url is not valid!"));
  }

  @ExceptionHandler(value = TimeFormatIsNotValidException.class)
  public ResponseEntity<ErrorDTO> timeFormatIsNotValidExceptionHandler() {
    return ResponseEntity.badRequest()
        .body(new ErrorDTO("Timestamp is given in wrong format! (yyyy-MM-dd HH:mm:ss)"));
  }

  @ExceptionHandler(value = IllegalStateException.class)
  public void logIllegalStateException(IllegalStateException ex) {
    System.err.println(ex.getMessage());
  }
}
