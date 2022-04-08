package com.rivi.blueprint.utils.exception;

/**
 * @author Armaan (armaan6651)
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String errorMessage) {
    super(errorMessage);
  }

}
