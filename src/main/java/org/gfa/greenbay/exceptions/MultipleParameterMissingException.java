package org.gfa.greenbay.exceptions;

import java.util.List;

public abstract class MultipleParameterMissingException extends ParameterMissingException {

  private final List<String> missingFields;


  public MultipleParameterMissingException(List<String> missingFields) {
    this.missingFields = missingFields;
  }

  public List<String> getMissingFields() {
    return missingFields;
  }

}
