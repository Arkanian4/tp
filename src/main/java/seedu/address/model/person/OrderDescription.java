package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's order description in the Client2Door.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrderDescription(String)}
 */
public class OrderDescription {

    public static final String MESSAGE_CONSTRAINTS =
            "Order descriptions should only contain alphanumeric characters and spaces, and it should not be blank";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String value;

    /**
     * Constructs an {@code OrderDescription}.
     *
     * @param description A valid order description.
     */
    public OrderDescription(String description) {
        requireNonNull(description);
        checkArgument(isValidOrderDescription(description), MESSAGE_CONSTRAINTS);
        this.value = description;
    }

    /**
     * Returns true if a given string is a valid order description.
     */
    public static boolean isValidOrderDescription(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof OrderDescription)) {
            return false;
        }

        OrderDescription otherOrderDescription = (OrderDescription) other;
        return value.equals(otherOrderDescription.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }


}
