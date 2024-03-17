package parkingnomad.exception;

public class InvalidCoordinatesException extends BadRequestException {

    private final static String ADDITIONAL_EXCEPTION_INFORMATION = "{ invalid_value: %.6f }";

    public InvalidCoordinatesException(
            final CoordinatesExceptionCode coordinatesExceptionCode,
            final double invalidValue
    ) {
        super(
                coordinatesExceptionCode.getCode(),
                coordinatesExceptionCode.getMessage() + String.format(ADDITIONAL_EXCEPTION_INFORMATION, invalidValue)
        );
    }
}
