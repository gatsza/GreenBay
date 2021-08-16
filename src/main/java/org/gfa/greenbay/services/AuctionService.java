package org.gfa.greenbay.services;

import org.gfa.greenbay.dtos.AuctionCreationRequestDTO;
import org.gfa.greenbay.dtos.AuctionCreationResponseDTO;

public interface AuctionService {

  AuctionCreationResponseDTO addItem(
      AuctionCreationRequestDTO auctionCreationRequest, String username);
}
