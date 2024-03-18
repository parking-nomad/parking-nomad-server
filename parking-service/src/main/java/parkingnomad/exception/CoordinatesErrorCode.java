package parkingnomad.exception;

public enum CoordinatesErrorCode {
    INVALID_LATITUDE("COORDINATE0001", "최소 위도는 -90, 최대 위도는 90입니다."),
    INVALID_LONGITUDE("COORDINATE0002", "최소 경도는 -180, 최대 경도는 180입니다."),
    CANT_FIND_ADDRESS("COORDINATE0003", "좌표에 해당하는 주소를 찾을 수 없습니다. { lat : %.6f, lng : %.6f }");

    private final String code;
    private final String message;

    CoordinatesErrorCode(String code, String message) {
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
