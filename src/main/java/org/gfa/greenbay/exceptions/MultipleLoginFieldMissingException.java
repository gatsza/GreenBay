package org.gfa.greenbay.exceptions;

import java.util.List;

public class MultipleLoginFieldMissingException extends MultipleParameterMissingException {

  public MultipleLoginFieldMissingException(List<String> missingFields) {
    super(missingFields);
  }
}
