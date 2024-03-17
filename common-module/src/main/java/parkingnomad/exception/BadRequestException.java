package parkingnomad.exception;

public abstract class BadRequestException extends RuntimeException {
    private final String errorCode;
    private final String message;

    public BadRequestException(String errorCode, String message) {
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
