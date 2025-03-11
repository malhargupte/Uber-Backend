package com.guptem.UberBackend.advices;

import com.guptem.UberBackend.exceptions.ResourceNotFoundException;
import com.guptem.UberBackend.exceptions.RuntimeConflictException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<APIResponse<?>> buildErrorResponseEntity(APIError apiError) {
        return new ResponseEntity<>(new APIResponse<>(apiError), apiError.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFoundException(ResourceNotFoundException exception) {

        APIError apiError = APIError
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(RuntimeConflictException.class)
    public ResponseEntity<APIResponse<?>> handleRuntimeConflictException(RuntimeConflictException exception) {

        APIError apiError = APIError
                .builder()
                .status(HttpStatus.CONFLICT)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);

    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<APIResponse<?>> handleAuthenticationException(AuthenticationException ex) {
        APIError apiError = APIError
                .builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();
        return buildErrorResponseEntity(apiError);

    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<APIResponse<?>> handleJwtException(JwtException ex) {

        APIError apiError = APIError
                .builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<APIResponse<?>> handleAccessDeniedException(AccessDeniedException ex) {

        APIError apiError = APIError
                .builder()
                .status(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleInternalServerError(Exception exception) {

        APIError apiError = APIError
                .builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();

        return buildErrorResponseEntity(apiError);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        List<String> errors = exception
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        APIError apiError = APIError
                .builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation FAILED!")
                .subErrors(errors)
                .build();

        return buildErrorResponseEntity(apiError);
    }

}
