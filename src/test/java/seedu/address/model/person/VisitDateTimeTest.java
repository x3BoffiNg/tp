package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VisitDateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        // EP (invalid): null input should be rejected.
        assertThrows(NullPointerException.class, () -> new VisitDateTime(null));
    }

    @Test
    public void constructor_invalidVisitDateTime_throwsIllegalArgumentException() {
        // EP (invalid): blank string is not a valid visit date-time.
        String invalidVisitDateTime = "";
        assertThrows(IllegalArgumentException.class, () -> new VisitDateTime(invalidVisitDateTime));
    }

    @Test
    public void isValidVisitDateTime() {
        // EP (invalid): null input.
        // null visit date time
        assertFalse(VisitDateTime.isValidVisitDateTime(null));

        // EP (invalid): blank/whitespace input.
        // blank visit date time
        assertFalse(VisitDateTime.isValidVisitDateTime(""));
        assertFalse(VisitDateTime.isValidVisitDateTime(" "));

        // EP (invalid): out-of-range fields or wrong format.
        // invalid visit date times
        assertFalse(VisitDateTime.isValidVisitDateTime("2026-13-01 14:30")); // invalid month
        assertFalse(VisitDateTime.isValidVisitDateTime("2026-12-32 14:30")); // invalid day
        assertFalse(VisitDateTime.isValidVisitDateTime("2026-12-01 25:00")); // invalid hour
        assertFalse(VisitDateTime.isValidVisitDateTime("2026-12-01 14:60")); // invalid minute
        assertFalse(VisitDateTime.isValidVisitDateTime("01-12-2026 14:30")); // wrong format
        assertFalse(VisitDateTime.isValidVisitDateTime("2026/12/01 14:30")); // wrong separator

        // EP (valid): canonical valid values and permissive-coercion values.
        // valid visit date times
        assertTrue(VisitDateTime.isValidVisitDateTime("2026-03-15 14:30"));
        assertTrue(VisitDateTime.isValidVisitDateTime("2026-12-01 00:00"));
        assertTrue(VisitDateTime.isValidVisitDateTime("2026-06-20 23:59"));
        assertTrue(VisitDateTime.isValidVisitDateTime("2026-04-31 14:30")); // rounds to month end
        assertTrue(VisitDateTime.isValidVisitDateTime("2026-12-01 24:00")); // rolls to next day midnight
    }

    @Test
    public void constructor_permissiveInput_normalizesValue() {
        // BVA: end-of-month overflow should coerce to month end.
        assertEquals("2026-04-30 14:30", new VisitDateTime("2026-04-31 14:30").toString());
        // BVA: 24:00 should roll over to next-day 00:00.
        assertEquals("2026-12-02 00:00", new VisitDateTime("2026-12-01 24:00").toString());
    }

    @Test
    public void equals() {
        VisitDateTime visitDateTime = new VisitDateTime("2026-03-15 14:30");

        // same values -> returns true
        assertTrue(visitDateTime.equals(new VisitDateTime("2026-03-15 14:30")));

        // same object -> returns true
        assertTrue(visitDateTime.equals(visitDateTime));

        // null -> returns false
        assertFalse(visitDateTime.equals(null));

        // different types -> returns false
        assertFalse(visitDateTime.equals(5.0f));

        // different values -> returns false
        assertFalse(visitDateTime.equals(new VisitDateTime("2026-12-01 10:00")));
    }

    @Test
    public void testEmptyVisitDateTime() {
        // EP (valid optional): empty object represents absence of value.
        VisitDateTime emptyVisit = new VisitDateTime();
        assertFalse(emptyVisit.isPresent());
        assertEquals("", emptyVisit.toString());
        assertEquals("", emptyVisit.getDisplayValue());
    }

    @Test
    public void testGetDisplayValue() {
        // EP (valid): present value should render in display format.
        VisitDateTime visitDateTime = new VisitDateTime("2026-03-15 14:30");
        assertEquals("15 Mar 2026, 02:30 PM", visitDateTime.getDisplayValue());
    }

    @Test
    public void equals_bothEmpty_true() {
        VisitDateTime empty1 = new VisitDateTime();
        VisitDateTime empty2 = new VisitDateTime();
        assertTrue(empty1.equals(empty2)); // covers both-null branch
    }

    @Test
    public void equals_oneEmptyOnePresent_false() {
        VisitDateTime empty = new VisitDateTime();
        VisitDateTime present = new VisitDateTime("2026-03-15 14:30");
        assertFalse(empty.equals(present)); // covers one-null branch
        assertFalse(present.equals(empty)); // symmetric check
    }

    @Test
    public void equals_bothPresentSameValue_true() {
        VisitDateTime first = new VisitDateTime("2026-03-15 14:30");
        VisitDateTime second = new VisitDateTime("2026-03-15 14:30");
        assertTrue(first.equals(second)); // covers value.equals(...) branch
    }

    @Test
    public void hashCode_empty_zero() {
        VisitDateTime empty = new VisitDateTime();
        assertEquals(0, empty.hashCode()); // covers null hash branch
    }

    @Test
    public void hashCode_present_matchesValueHash() {
        VisitDateTime present = new VisitDateTime("2026-03-15 14:30");
        assertNotEquals(0, present.hashCode()); // covers non-null hash branch
    }
}

