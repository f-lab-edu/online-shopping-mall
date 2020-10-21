package me.naming.onlineshoppingmall.config;

import java.time.LocalDateTime;
import me.naming.onlineshoppingmall.domain.ResponseData;
import me.naming.onlineshoppingmall.exception.MessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// @Controller or @RestController에서 발생하는 예외를 관리 할 수 있게 만들어주는 어노테이션
// @ExceptionHandler를 통해 각각의 예외처리 메세지를 원하는 형태로 작성 할 수 있다.
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = { MessageException.class })
  protected ResponseEntity handleConflict(MessageException messageException, WebRequest request) {
    String path = ((ServletWebRequest)request).getRequest().getRequestURI();
    int code;

    if(messageException.getStatusCode() != 0) {
      code = messageException.getStatusCode();
    } else {
      code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    ResponseData responseData = ResponseData.builder()
        .localDateTime(LocalDateTime.now())
        .statusCode(code)
        .path(path)
        .message(messageException.getMessage())
        .build();

    return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}