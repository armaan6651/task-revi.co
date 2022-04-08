package com.rivi.blueprint.utils.exception;

/**
 * @author Armaan (armaan6651)
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String errorMessage) {
    super(errorMessage);
  }

}
