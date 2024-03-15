package parkingnomad.exception.auth;

public class InvalidAccessTokenException extends AuthException {
    public InvalidAccessTokenException(final String code) {
        super(code);
    }

    @Override
    public String getMessage() {
        return "유효하지 않은 access_token 입니다.";
    }
}
