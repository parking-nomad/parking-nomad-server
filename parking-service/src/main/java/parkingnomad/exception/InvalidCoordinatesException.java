package parkingnomad.exception;

public class InvalidCoordinatesException extends BadRequestException {

    private final static String ADDITIONAL_EXCEPTION_INFORMATION = "{ invalid_value: %.6f }";

    public InvalidCoordinatesException(
            final CoordinatesErrorCode coordinatesErrorCode,
            final double invalidValue
    ) {
        super(
                coordinatesErrorCode.getCode(),
                coordinatesErrorCode.getMessage() + String.format(ADDITIONAL_EXCEPTION_INFORMATION, invalidValue)
        );
    }
}
