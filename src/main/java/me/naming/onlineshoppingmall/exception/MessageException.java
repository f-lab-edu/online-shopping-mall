package me.naming.onlineshoppingmall.exception;

import org.springframework.http.HttpStatus;

public class MessageException extends RuntimeException{

  private int statusCode;

  public MessageException(String message) {
    super(message);
    this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
  }

  public MessageException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public int getStatusCode() {
    return statusCode;
  }

}
