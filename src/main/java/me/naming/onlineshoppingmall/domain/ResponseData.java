package me.naming.onlineshoppingmall.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Builder
@ToString
@Getter
public class ResponseData {
  private LocalDateTime localDateTime;
  private int statusCode;
  private String path;
  private String message;

  public ResponseData(LocalDateTime localDateTime, int statusCode, String path, String message) {
    this.localDateTime = localDateTime;
    this.statusCode = statusCode;
    this.path = path;
    this.message = message;
  }
}
