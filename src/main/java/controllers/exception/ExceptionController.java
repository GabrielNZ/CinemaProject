package controllers.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import services.exception.DataBaseException;
import services.exception.ResourceNotFound;

import java.time.Instant;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<StandardException> resourceNotFoundException(ResourceNotFound exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new StandardException(Instant.now(), HttpStatus.NOT_FOUND.value(), "Resource Not Found", exception.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(DataBaseException.class)
    public ResponseEntity<StandardException> dataBaseException(DataBaseException exception, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StandardException(Instant.now(), HttpStatus.BAD_REQUEST.value(), "DataBase integrity violation", exception.getMessage(), request.getRequestURI()));
    }
}
