package com.rivi.blueprint.utils.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author Armaan (armaan6651)
 */
@ControllerAdvice
@RestController
public class ApiAdviceController {

  /*
   * Thrown when bad request exception
   */
  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleBadRequestException(HttpServletRequest request, BadRequestException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception.getMessage(),request.getRequestURI(),BAD_REQUEST), BAD_REQUEST);
  }

  /*
   * Thrown when validation exception
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(BAD_REQUEST)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException exception) {
    BindingResult result = exception.getBindingResult();
    List<FieldError> fieldErrors = result.getFieldErrors();
    return new ResponseEntity<>(buildErrorResponse(processFieldErrors(fieldErrors),request.getRequestURI(),BAD_REQUEST), BAD_REQUEST);
  }

  /*
   * Thrown when not found exception
   */
  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ResponseEntity<ErrorResponse> handleNotFoundException(HttpServletRequest request, NotFoundException exception) {
    return new ResponseEntity<>(buildErrorResponse(exception.getMessage(),request.getRequestURI(),NOT_FOUND), NOT_FOUND);
  }

  private ErrorResponse buildErrorResponse(String errorMsg, String details, HttpStatus httpStatus) {
    return new ErrorResponse(new Date(), httpStatus.value(),httpStatus.getReasonPhrase(),Arrays.asList(errorMsg) ,details);
  }

  private ErrorResponse buildErrorResponse(List<String> errorMsg, String details, HttpStatus httpStatus) {
    return new ErrorResponse(new Date(), httpStatus.value(),httpStatus.getReasonPhrase(), errorMsg ,details);
  }

  private List<String> processFieldErrors(List<FieldError> fieldErrors) {
    List<String> errorMsg = new ArrayList<>();
    for (FieldError fieldError: fieldErrors) {
      errorMsg.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
    }
    return errorMsg;
  }
}