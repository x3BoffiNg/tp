package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    private static final String VALID_MAX_LENGTH_PHONE = "+123 456-789 01";
    private static final String INVALID_OVER_MAX_LENGTH_PHONE = "+123 456-789 012";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // Equivalent Partitioning (invalid): blank, over max length, disallowed chars, no digit
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("+--")); // allowed symbols only, but no digit
        assertFalse(Phone.isValidPhone(INVALID_OVER_MAX_LENGTH_PHONE)); // exceeds max length
        assertFalse(Phone.isValidPhone("+65(9123)4567")); // parentheses are not allowed
        assertFalse(Phone.isValidPhone("1234/5678")); // slash is not allowed

        // Equivalent Partitioning (valid): only allowed chars, with at least one digit, within max length
        assertTrue(Phone.isValidPhone("91"));
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("+65 9123-4567"));
        assertTrue(Phone.isValidPhone("1800 123 4567"));

        // Boundary Value Analysis: exactly max length should be accepted
        assertTrue(Phone.isValidPhone(VALID_MAX_LENGTH_PHONE));
    }

    @Test
    public void equals() {
        Phone phone = new Phone("91234567");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("91234567")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("81234567")));
    }
}
