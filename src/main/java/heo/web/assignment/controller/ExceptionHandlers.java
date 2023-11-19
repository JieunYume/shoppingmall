package heo.web.assignment.controller;

import heo.web.assignment.dto.error.ErrorDto;
import heo.web.assignment.dto.error.UnknownErrorDto;
import heo.web.assignment.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static heo.web.assignment.exception.ErrorCode.ID_OR_PASSWORD_NOT_MATCH;
import static heo.web.assignment.exception.ErrorCode.INTERNAL_SERVER_ERROR;


@RestControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity handleCustomException(CustomException exception) {
        return new ResponseEntity(new ErrorDto(exception.getErrorCode().getStatus(), exception.getErrorCode().getMessage()), HttpStatus.valueOf(exception.getErrorCode().getStatus()));
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception exception) {
        System.out.println(exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity(new UnknownErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage(), exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ BadCredentialsException.class })
    protected ResponseEntity handleAuthenticationException(BadCredentialsException exception) {
        System.out.println(exception.getMessage());
        exception.printStackTrace();
        return new ResponseEntity(new ErrorDto(ID_OR_PASSWORD_NOT_MATCH.getStatus(), ID_OR_PASSWORD_NOT_MATCH.getMessage()), HttpStatus.valueOf(ID_OR_PASSWORD_NOT_MATCH.getStatus()));
    }
}
