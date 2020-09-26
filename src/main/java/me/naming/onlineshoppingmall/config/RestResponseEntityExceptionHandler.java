package me.naming.onlineshoppingmall.config;

import com.google.gson.Gson;
import java.time.LocalDateTime;
import me.naming.onlineshoppingmall.domain.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
  protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
    HttpHeaders httpHeaders = new HttpHeaders();
    Gson gson = new Gson();

    httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
    String path = ((ServletWebRequest)request).getRequest().getRequestURI();

    ResponseData responseData = ResponseData.builder()
        .localDateTime(LocalDateTime.now())
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .path(path)
        .message(ex.getMessage())
        .build();
    return handleExceptionInternal(ex, gson.toJson(responseData), httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}