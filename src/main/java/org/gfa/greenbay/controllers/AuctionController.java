package org.gfa.greenbay.controllers;

import org.gfa.greenbay.dtos.AuctionCreationRequestDTO;
import org.gfa.greenbay.dtos.AuctionCreationResponseDTO;
import org.gfa.greenbay.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auctions/")
public class AuctionController {

  AuctionService auctionService;

  @Autowired
  public AuctionController(AuctionService auctionService) {
    this.auctionService = auctionService;
  }

  @PostMapping("add")
  public ResponseEntity<AuctionCreationResponseDTO> createAuction(
      @RequestBody(required = false) AuctionCreationRequestDTO auctionCreationRequest,
      Authentication authentication) {
    String username = authentication.getName();
    return ResponseEntity.ok(auctionService.addItem(auctionCreationRequest, username));
  }
}
