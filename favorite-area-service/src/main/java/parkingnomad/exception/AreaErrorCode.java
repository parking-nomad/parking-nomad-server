package parkingnomad.exception;

public enum AreaErrorCode {
    INVALID_NAME_EXCEPTION("AREA0001", "즐겨찾는 구역의 이름은 1글자 이상 20글자 이하여야 합니다. { invalid_name : %s }"),
    NON_EXISTENT_MEMBER("AREA0002", "존재하지 않는 회원입니다. { non_existent_member_id : %d }");

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
