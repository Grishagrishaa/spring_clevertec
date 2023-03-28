package ru.clevertec.ecl.controler.handler;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.ecl.dto.errors.ErrorMessage;
import ru.clevertec.ecl.dto.errors.StructuredError;
import ru.clevertec.ecl.service.util.ErrorMessagesUtils;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;


@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<ErrorMessage> handle(PSQLException e){
        return ResponseEntity.status(400).body(new ErrorMessage(e.getServerErrorMessage().toString()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handle(EntityNotFoundException e){
        return ResponseEntity.status(400).body(new ErrorMessage(ErrorMessagesUtils.TAG_NOT_FOUND));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StructuredError> handle(ConstraintViolationException e){
        return ResponseEntity.status(400).body(new StructuredError(buildErrorMessages(e.getConstraintViolations())));
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

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorMessage> handle(EmptyResultDataAccessException e){
        return ResponseEntity.status(204).body(new ErrorMessage(e.getMessage()));
    }
}
