package parkingnomad.domain;

import org.apache.logging.log4j.util.Strings;
import parkingnomad.exception.InvalidNameException;

import static parkingnomad.exception.AreaErrorCode.INVALID_NAME_EXCEPTION;

public class Name {
    public static final int NAME_MAX_LENGTH = 20;
    private final String value;

    private Name(final String value) {
        this.value = value;
    }

    public static Name from(final String value) {
        if (Strings.isBlank(value) || value.length() > NAME_MAX_LENGTH) {
            throw new InvalidNameException(INVALID_NAME_EXCEPTION, value);
        }
        return new Name(value);
    }

    public String getValue() {
        return value;
    }
}
