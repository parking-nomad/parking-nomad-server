package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class LocationSearchException extends BadRequestException {
    public LocationSearchException(
            final CoordinatesErrorCode coordinatesErrorCode,
            final double latitude,
            final double longitude
    ) {
        super(coordinatesErrorCode.getCode(), String.format(coordinatesErrorCode.getMessage(), latitude, longitude));
    }
}
