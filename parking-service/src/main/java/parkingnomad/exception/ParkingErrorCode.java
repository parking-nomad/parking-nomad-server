package parkingnomad.exception;

public enum ParkingErrorCode {
    INVALID_MEMBER_ID("PARKING0001", "member_id가 유효하지 않습니다. { invalid_member_id : %d }");

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
