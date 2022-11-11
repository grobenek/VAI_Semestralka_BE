package szathmary.vai.exception_handler;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import szathmary.vai.exception.ItemNotFoundException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ItemNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleItemNotFoundException(
      ItemNotFoundException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(NonTransientDataAccessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleNonTransientDataAccessException(
      NonTransientDataAccessException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(RecoverableDataAccessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleRecoverableDataAccessException(
      RecoverableDataAccessException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(ScriptException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleScriptException(
      ScriptException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(TransientDataAccessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleTransientDataAccessException(
      TransientDataAccessException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(RestClientException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleRestClientException(
      RestClientException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(HttpMessageConversionException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<Object> handleRestClientException(
      HttpMessageConversionException exception,
      WebRequest request
  ) {
    log.error(exception.getMessage());
    return handleExceptionInternal(exception,
        findCauseOfException(exception).toString(),
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  public static Throwable findCauseOfException(Throwable throwable) {
    log.info("Finding cause for: {}", throwable.toString());
    Objects.requireNonNull(throwable);
    Throwable rootCause = throwable;
    while (rootCause.getCause() != null && rootCause.getCause() != rootCause) {
      rootCause = rootCause.getCause();
    }
    return rootCause;
  }

}
