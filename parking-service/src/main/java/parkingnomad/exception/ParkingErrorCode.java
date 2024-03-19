package parkingnomad.exception;

public enum ParkingErrorCode {
    INVALID_MEMBER_ID("PARKING0001", "member_id가 유효하지 않습니다. { invalid_member_id : %d }"),
    NON_EXISTENT_PARKING("PARKING0002", "존재하지 않는 parkingId 입니다. { invalid_parking_id : %d }"),
    MEMBER_LOADER("PARKING0003", "member loader와 정상적인 통신이 이뤄지지 않았습니다. { message_from_member_loader : %s }");

    private final String code;
    private final String message;

    ParkingErrorCode(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
