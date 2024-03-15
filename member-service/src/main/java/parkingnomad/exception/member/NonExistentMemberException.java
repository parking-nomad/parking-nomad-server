package parkingnomad.exception.member;

public class NonExistentMemberException extends MemberException {

    private static final String FIXED_MESSAGE = "존재하지 않는 회원입니다. {input_member_id : %d}";

    private final long inputMemberId;

    public NonExistentMemberException(final String code, final long inputMemberId) {
        super(code);
        this.inputMemberId = inputMemberId;
    }

    @Override
    public String getMessage() {
        return String.format(FIXED_MESSAGE, inputMemberId);
    }
}
