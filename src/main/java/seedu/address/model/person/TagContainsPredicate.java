package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Tag} matches any of the keywords given.
 */
public class TagContainsPredicate implements Predicate<Person> {

    private String tag;

    public TagContainsPredicate(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().stream()
                .anyMatch(t -> t.tagName.equalsIgnoreCase(this.tag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagContainsPredicate)) {
            return false;
        }

        TagContainsPredicate otherPredicate = (TagContainsPredicate) other;
        return tag.equals(otherPredicate.tag);
    }
}
