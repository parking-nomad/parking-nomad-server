package parkingnomad.exception.auth;

public enum AuthExceptionCode {
    EXPIRED_ACCESS_TOKEN("AUTH0001"),
    INVALID_ACCESS_TOKEN("AUTH0002"),
    INVALID_REFRESH_TOKEN("AUTH0003");


    private final String code;

    AuthExceptionCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
