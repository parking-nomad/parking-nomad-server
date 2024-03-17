package parkingnomad.exception.member;

import parkingnomad.exception.BadRequestException;

public class NonExistentMemberException extends BadRequestException {

    private static final String FIXED_MESSAGE = "존재하지 않는 회원입니다. {input_member_id : %d}";

    public NonExistentMemberException(final String code, final long inputMemberId) {
        super(code, String.format(FIXED_MESSAGE, inputMemberId));
    }
}
