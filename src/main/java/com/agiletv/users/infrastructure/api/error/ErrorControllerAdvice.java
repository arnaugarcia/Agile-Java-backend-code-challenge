package com.agiletv.users.infrastructure.api.error;

import com.agiletv.users.infrastructure.client.RandomUserApiException;
import java.util.NoSuchElementException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
class ErrorControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body(new ErrorResponse("BAD_REQUEST", exception.getMessage()));
    }

    @ExceptionHandler(RandomUserApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiException(RandomUserApiException exception) {
        return ResponseEntity.internalServerError().body(new ErrorResponse("EXTERNAL_API_ERROR",
            exception.getMessage()));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.notFound().build();
    }

}
