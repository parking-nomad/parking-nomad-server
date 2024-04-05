package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class InvalidNameException extends BadRequestException {
    public InvalidNameException(final AreaErrorCode errorCode, final String invalidName) {
        super(errorCode.getCode(), String.format(errorCode.getMessage(), invalidName));
    }
}
