package parkingnomad.exception;

import parkingnomad.exception.base.InternalServerException;

public class MemberLoaderException extends InternalServerException {

    public MemberLoaderException(final String errorCode, final String message) {
        super(errorCode, message);
    }

    public static MemberLoaderException of(final ParkingErrorCode errorCode, final Exception exception) {
        System.out.println(exception.getMessage());
        return new MemberLoaderException(errorCode.getCode(), "adsf");
    }
}
