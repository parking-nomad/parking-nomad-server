package parkingnomad.adaptor.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import parkingnomad.dto.error.ErrorResponse;
import parkingnomad.exception.BadRequestException;

@RestControllerAdvice
public class ParkingControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.internalServerError(exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
