package parkingnomad.exception.member;

public enum MemberExceptionCode {
    NON_EXISTENT_MEMBER("MEMBER0001");

    private final String code;

    MemberExceptionCode(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
