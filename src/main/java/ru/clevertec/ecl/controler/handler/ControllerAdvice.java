package ru.clevertec.ecl.controler.handler;

import jakarta.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.clevertec.ecl.dto.read.ErrorMessage;
import ru.clevertec.ecl.dto.read.StructuredError;
import ru.clevertec.ecl.exceptions.OptimisticLockException;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(PSQLException e){
        return new ErrorMessage(e.getServerErrorMessage().toString());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(IllegalArgumentException e){
        return new ErrorMessage(
                e.getMessage()
        );
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessage handle(OptimisticLockException e){
        return new ErrorMessage(
                e.getMessage()
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public StructuredError handle(ConstraintViolationException e){
        return new StructuredError(e.getConstraintViolations().stream()
                .map(exc -> new ErrorMessage(
                        exc.getPropertyPath().toString().split("\\.")[2],
                        exc.getMessage()))
                .collect(Collectors.toSet()));
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(NO_CONTENT)
    public ErrorMessage handle(EmptyResultDataAccessException e){
        return new ErrorMessage(
                "NO ENTITY WAS FOUND"
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpRequestMethodNotSupportedException e){
        return new ErrorMessage(
                "CHECK URL / HTTP METHOD"
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorMessage handle(HttpMessageNotReadableException e){
        return new ErrorMessage(
                e.getLocalizedMessage()
        );
    }

}
