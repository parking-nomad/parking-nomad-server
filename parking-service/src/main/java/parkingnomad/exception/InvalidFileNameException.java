package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class InvalidFileNameException extends BadRequestException {
    public InvalidFileNameException(final ParkingErrorCode errorCode, final String invalidFileName) {
        super(errorCode.getCode(), String.format(errorCode.getMessage(), invalidFileName));
    }
}
