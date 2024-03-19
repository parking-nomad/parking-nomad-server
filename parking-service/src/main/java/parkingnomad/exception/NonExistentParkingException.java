package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class NonExistentParkingException extends BadRequestException {
    public NonExistentParkingException(final ParkingErrorCode parkingErrorCode, final Long id) {
        super(parkingErrorCode.getCode(), String.format(parkingErrorCode.getMessage(), id));
    }
}
