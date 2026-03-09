package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's expiry date in the address book.
 */
public class ExpiryDate {

    public static final String MESSAGE_CONSTRAINTS =
            "Expiry date should be in YYYY-MM-DD format, or blank.";

    /*
     * Accepts blank string, or dates in ISO format yyyy-MM-dd.
     */
    public static final String VALIDATION_REGEX = "^$|\\d{4}-\\d{2}-\\d{2}$";

    public final String value;

    /**
     * Constructs an {@code ExpiryDate}.
     *
     * @param expiryDate A valid expiry date.
     */
    public ExpiryDate(String expiryDate) {
        requireNonNull(expiryDate);
        checkArgument(isValidExpiryDate(expiryDate), MESSAGE_CONSTRAINTS);
        value = expiryDate;
    }

    /**
     * Returns true if a given string is a valid expiry date.
     */
    public static boolean isValidExpiryDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ExpiryDate
                && value.equals(((ExpiryDate) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}