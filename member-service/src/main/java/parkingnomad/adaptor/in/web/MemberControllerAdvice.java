package parkingnomad.adaptor.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import parkingnomad.dto.error.ErrorResponse;
import parkingnomad.exception.BadRequestException;

@ControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException badRequestException) {
        ErrorResponse errorResponse = new ErrorResponse(badRequestException.getErrorCode(), badRequestException.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
