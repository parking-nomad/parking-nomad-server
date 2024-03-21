package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class ParkingImageAlreadyExists extends BadRequestException {
    public ParkingImageAlreadyExists(final ParkingErrorCode parkingErrorCode) {
        super(parkingErrorCode.getCode(), parkingErrorCode.getMessage());
    }
}
