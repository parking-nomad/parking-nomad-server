package parkingnomad.exception;

public abstract class ForbiddenException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public ForbiddenException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
