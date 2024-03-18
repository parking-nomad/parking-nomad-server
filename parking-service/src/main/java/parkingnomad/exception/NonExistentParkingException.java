package parkingnomad.exception;

public class NonExistentParkingException extends BadRequestException {
    public NonExistentParkingException(final ParkingErrorCode parkingErrorCode, final Long id) {
        super(parkingErrorCode.getCode(), String.format(parkingErrorCode.getMessage(), id));
    }
}
