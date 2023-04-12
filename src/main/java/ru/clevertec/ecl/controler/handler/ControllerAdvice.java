package ru.clevertec.ecl.controler.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.ecl.dto.errors.ErrorMessage;
import ru.clevertec.ecl.dto.errors.StructuredError;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;


@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorMessage> handle(PSQLException e){
        return ResponseEntity.badRequest().body(ErrorMessage.builder()
                                                           .logref("error")
                                                           .message(e.getServerErrorMessage().getDetail() != null ?
                                                                   e.getServerErrorMessage().getDetail() :
                                                                   e.getServerErrorMessage().toString())
                                                           .build());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException e){
        return ResponseEntity.badRequest().body(ErrorMessage.builder()
                                                           .logref("error")
                                                           .message("ENTITY NOT FOUND")
                                                           .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredError> handle(ConstraintViolationException e){
        return ResponseEntity.badRequest().body(StructuredError.builder()
                                                              .logref("structured_error")
                                                              .errors(buildErrorMessages(e.getConstraintViolations()))
                                                              .build());
    }

    private Set<ErrorMessage> buildErrorMessages(Set<ConstraintViolation<?>> violations) {
        return violations.stream()
                .map(violation -> ErrorMessage.builder()
                                .field(StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                                                           .reduce((first, second) -> second)
                                                           .orElse(null)
                                                           .toString())
                                .message(violation.getMessage())
                                .build())
                .collect(toSet());
      }
}
