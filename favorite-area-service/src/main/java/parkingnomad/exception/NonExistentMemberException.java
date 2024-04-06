package parkingnomad.exception;

import parkingnomad.exception.base.BadRequestException;

public class NonExistentMemberException extends BadRequestException {
    public NonExistentMemberException(final AreaErrorCode errorCode, final Long invalidMemberId) {
        super(errorCode.getCode(), String.format(errorCode.getMessage(), invalidMemberId));
    }
}
