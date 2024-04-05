package parkingnomad.exception.auth;

import parkingnomad.exception.base.AuthException;

public class ExpiredAccessTokenException extends AuthException {
    public ExpiredAccessTokenException(final String code) {
        super(code);
    }

    @Override
    public String getMessage() {
        return "만료된 access_token 입니다.";
    }
}
