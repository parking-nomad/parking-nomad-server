package parkingnomad.exception;

public class InvalidMemberIdException extends BadRequestException {

    public InvalidMemberIdException(final ParkingErrorCode parkingErrorCode, final Long invalidMemberId) {
        super(parkingErrorCode.getCode(), String.format(parkingErrorCode.getMessage(), invalidMemberId));
    }
}
