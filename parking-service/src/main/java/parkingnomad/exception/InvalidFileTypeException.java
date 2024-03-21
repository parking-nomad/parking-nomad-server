package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class InvalidFileTypeException extends BadRequestException {
    public InvalidFileTypeException(final ParkingErrorCode parkingErrorCode, final String invalidFileType) {
        super(parkingErrorCode.getCode(), String.format(parkingErrorCode.getMessage(), invalidFileType));
    }
}
