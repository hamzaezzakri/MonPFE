package cigma.pfe.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(buildResponse(ex, status),HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(buildResponse(ex, status),HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(buildResponse(ex, status),HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(buildResponse(ex, status),HttpStatus.METHOD_NOT_ALLOWED);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(ex.getMessage(),ex);
        return new ResponseEntity<>(buildResponse(ex, status),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Object buildResponse(Exception ex, HttpStatus status){
        Map<String,Object> details = new HashMap<>();
        details.put("status",status);
        details.put("error",status.getReasonPhrase());
        details.put("message",ex.getMessage());
        details.put("timestamp", LocalDateTime.now());
        return details;
    }
}
