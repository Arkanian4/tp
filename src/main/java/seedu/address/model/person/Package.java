package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Package in Client2Door
 */
public class Package {

    public static final String MESSAGE_CONTRAINTS = "Package names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}";

    public final String packageName;

    public Package(String packageName) {
        requireNonNull(packageName);
        checkArgument(isValidPackageName(packageName), MESSAGE_CONTRAINTS);
        this.packageName = packageName;
    }

    public static boolean isValidPackageName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Package)) {
            return false;
        }

        Package otherPackage = (Package) other;
        return packageName.equals(otherPackage.packageName);
    }

    @Override
    public int hashCode() {
        return packageName.hashCode();
    }

    /**
     * Formats state as text for viewing
     * @return Formatted package name
     */
    @Override
    public String toString() {
        return '[' + packageName + ']';
    }

}
