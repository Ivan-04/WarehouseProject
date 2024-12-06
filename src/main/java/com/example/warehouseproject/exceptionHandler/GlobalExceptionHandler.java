package com.example.warehouseproject.exceptionHandler;

import com.example.warehouseproject.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DuplicateEntityException.class)
    public ResponseEntity<ApiException> handleDuplicateEntityException(DuplicateEntityException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiException> handleEntityNotFoundException(EntityNotFoundException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<ApiException> handleAuthorizationException(AuthorizationException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = UnauthorizedOperationException.class)
    public ResponseEntity<ApiException> handleUnauthorizedOperationException(UnauthorizedOperationException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = AuthenticationFailureException.class)
    public ResponseEntity<ApiException> handleAuthenticationFailureException(AuthenticationFailureException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = InsufficientPartException.class)
    public ResponseEntity<ApiException> handleInsufficientPartException(InsufficientPartException ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiException> handleException(Exception ex, HttpServletRequest request) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiException apiException = new ApiException(
                httpStatus.value(),
                ex.getMessage(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiException, httpStatus);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException exc, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "File size exceeds the limit!");
        return "redirect:/uploadError";
    }

}
