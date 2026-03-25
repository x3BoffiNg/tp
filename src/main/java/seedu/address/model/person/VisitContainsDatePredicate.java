package seedu.address.model.person;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code VisitDateTime} is within the given date range.
 */
public class VisitContainsDatePredicate implements Predicate<Person> {
    private final LocalDate startDate;
    private final LocalDate endDate;

    /**
     * Creates a {@code VisitContainsDatePredicate} with the specified start and end dates.
     *
     * @param startDate The start of the date range (inclusive).
     * @param endDate The end of the date range (inclusive).
     */
    public VisitContainsDatePredicate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public boolean test(Person person) {
        VisitDateTime visit = person.getVisitDateTime();

        if (visit == null || !visit.isPresent()) {
            return false;
        }

        LocalDateTime dateTimeValue = visit.value;
        LocalDate visitDate = dateTimeValue.toLocalDate();

        return (visitDate.isEqual(startDate) || visitDate.isAfter(startDate))
                && (visitDate.isEqual(endDate) || visitDate.isBefore(endDate));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof VisitContainsDatePredicate)) {
            return false;
        }

        VisitContainsDatePredicate otherPredicate = (VisitContainsDatePredicate) other;
        return startDate.equals(otherPredicate.startDate)
                && endDate.equals(otherPredicate.endDate);
    }
}
