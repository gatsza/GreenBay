package org.gfa.greenbay.exceptions;

import java.util.List;

public class AuctionCreationMultipleFieldMissingException extends
    MultipleParameterMissingException {

  public AuctionCreationMultipleFieldMissingException(List<String> missingFields) {
    super(missingFields);
  }
}
