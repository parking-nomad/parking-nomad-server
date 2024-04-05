package parkingnomad.exception;

public enum AreaErrorCode {
    INVALID_NAME_EXCEPTION("AREA0001", "즐겨찾는 구역의 이름은 1글자 이상 20글자 이하여야 합니다. { invalid_name : %s }");

    private final String code;

    private final String message;

    AreaErrorCode(final String code, final String message) {
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
