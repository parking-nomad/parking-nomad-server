package parkingnomad.adaptor.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import parkingnomad.dto.error.ErrorResponse;
import parkingnomad.exception.BadRequestException;
import parkingnomad.exception.ForbiddenException;

@ControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException badRequestException) {
        ErrorResponse errorResponse = new ErrorResponse(badRequestException.getErrorCode(), badRequestException.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleForbiddenException(final ForbiddenException forbiddenException) {
        ErrorResponse errorResponse = new ErrorResponse(forbiddenException.getErrorCode(), forbiddenException.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.internalServerError(exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
