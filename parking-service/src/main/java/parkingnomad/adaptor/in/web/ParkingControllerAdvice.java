package parkingnomad.adaptor.in.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import parkingnomad.dto.error.ErrorResponse;
import parkingnomad.exception.base.BadRequestException;

import java.util.Map;

@RestControllerAdvice
public class ParkingControllerAdvice {

    private final ObjectMapper objectMapper;

    public ParkingControllerAdvice(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(final BadRequestException exception) {
        final ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), exception.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleFeignException(final FeignException exception) throws JsonProcessingException {
        final String innerMessage = parseFeignException(exception);
        final ErrorResponse errorResponse = new ErrorResponse("FEIGN_EXCEPTION", innerMessage);
        return ResponseEntity.internalServerError().body(errorResponse);
    }

    private String parseFeignException(final FeignException exception) throws JsonProcessingException {
        final String url = exception.request().url();
        final String contented = exception.contentUTF8();
        final Map<String, String> map = objectMapper.readValue(contented, Map.class);
        map.put("request_url", url);
        return map.toString();
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleUnexpectedException(final Exception exception) {
        final ErrorResponse errorResponse = ErrorResponse.internalServerError(exception.getMessage());
        return ResponseEntity.internalServerError().body(errorResponse);
    }
}
