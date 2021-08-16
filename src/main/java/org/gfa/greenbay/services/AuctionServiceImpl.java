package org.gfa.greenbay.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.validator.routines.UrlValidator;
import org.gfa.greenbay.dtos.AuctionCreationRequestDTO;
import org.gfa.greenbay.dtos.AuctionCreationResponseDTO;
import org.gfa.greenbay.exceptions.AuctionCreationMultipleFieldMissingException;
import org.gfa.greenbay.exceptions.AuctionCreationPayloadMissingException;
import org.gfa.greenbay.exceptions.PhotoUrlNotValidException;
import org.gfa.greenbay.exceptions.TimeFormatIsNotValidException;
import org.gfa.greenbay.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionServiceImpl implements AuctionService {

  private final ItemService itemService;
  private final UserService userService;
  private final UrlValidator urlValidator;

  @Autowired
  public AuctionServiceImpl(
      ItemService itemService,
      UserService userService) {
    this.itemService = itemService;
    this.userService = userService;
    this.urlValidator = new UrlValidator();
  }

  @Override
  public AuctionCreationResponseDTO addItem(
      AuctionCreationRequestDTO auctionCreationRequest, String username) {
    validateAuctionCreationRequest(auctionCreationRequest);
    LocalDateTime endingTimestamp = getTimestamp(auctionCreationRequest.getEndingDateTime());
    auctionCreationRequest.setEndingTimestamp(endingTimestamp);
    User seller = userService.getUserbyUsername(username)
        .orElseThrow(() -> new IllegalStateException("user does not exist"));
    auctionCreationRequest.setSeller(seller);
    return itemService.addItem(auctionCreationRequest);
  }

  private LocalDateTime getTimestamp(String endingDateTime) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
      return LocalDateTime.parse(endingDateTime, formatter);
    } catch (DateTimeParseException ex) {
      throw new TimeFormatIsNotValidException();
    }
  }

  private void validateAuctionCreationRequest(AuctionCreationRequestDTO auctionCreationRequest) {
    if (auctionCreationRequest == null) {
      throw new AuctionCreationPayloadMissingException();
    }

    List<String> missingFields = getMissingField(auctionCreationRequest);

    if (missingFields.size() != 0) {
      throw new AuctionCreationMultipleFieldMissingException(missingFields);
    }

    if (!urlValidator.isValid(auctionCreationRequest.getPhotoUrl())) {
      throw new PhotoUrlNotValidException();
    }
  }

  public List<String> getMissingField(AuctionCreationRequestDTO auctionCreationRequest) {
    List<String> missingFields = new ArrayList<>();
    if (auctionCreationRequest.getName() == null) {
      missingFields.add("name");
    }
    if (auctionCreationRequest.getDescription() == null) {
      missingFields.add("description");
    }
    if (auctionCreationRequest.getPhotoUrl() == null) {
      missingFields.add("photo url");
    }
    if (auctionCreationRequest.getEndingDateTime() == null) {
      missingFields.add("ending date time");
    }
    if (auctionCreationRequest.getBidPrice() == null) {
      missingFields.add("starting price");
    }
    if (auctionCreationRequest.getPurchasePrice() == null) {
      missingFields.add("purchase price");
    }
    return missingFields;
  }
}
