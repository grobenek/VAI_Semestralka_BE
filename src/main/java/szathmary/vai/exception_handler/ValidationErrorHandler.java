package szathmary.vai.exception_handler;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@ControllerAdvice
public class ValidationErrorHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public final ResponseEntity<Object> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    log.error(exception.getMessage());

    return ResponseEntity.badRequest().headers(new HttpHeaders())
        .body(getMessages(exception.getBindingResult()));
  }

  private String getMessages(BindingResult bindingResult) {
    return Optional.ofNullable(bindingResult.getFieldError())
        .map(DefaultMessageSourceResolvable::getDefaultMessage).orElse("");
  }
}
