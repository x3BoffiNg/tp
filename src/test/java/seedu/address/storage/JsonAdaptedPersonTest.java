package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPerson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.VisitDateTime;

public class JsonAdaptedPersonTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+-- ";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_NOTE = "\u0000";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_VISIT_DATE_TIME = "2020-13-01 10:00";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_NOTE = BENSON.getNote().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final String VALID_VISIT_DATE_TIME = BENSON.getVisitDateTime().toString();
    private static final Boolean VALID_IS_ARCHIVED = BENSON.isArchived();

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        // EP (valid): all fields are valid and fully populated.
        JsonAdaptedPerson person = new JsonAdaptedPerson(BENSON);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        // EP (invalid): malformed name.
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(INVALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_NOTE, VALID_TAGS,
                        VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        // EP (invalid): missing required name.
        JsonAdaptedPerson person = new JsonAdaptedPerson(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        // EP (invalid): malformed phone.
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, INVALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_NOTE, VALID_TAGS,
                        VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        // EP (invalid): missing required phone.
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, null, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        // EP (invalid): malformed email.
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, INVALID_EMAIL, VALID_ADDRESS, VALID_NOTE, VALID_TAGS,
                        VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        // EP (invalid): missing required email.
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, null, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        // EP (invalid): malformed address.
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, INVALID_ADDRESS, VALID_NOTE, VALID_TAGS,
                        VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        // EP (invalid): missing required address.
        JsonAdaptedPerson person = new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, null,
                VALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullNote_createsDefaultNote() throws Exception {
        // EP (valid optional): missing note falls back to default empty note.
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                null, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);

        Person modelPerson = person.toModelType();
        assertEquals(new Note(""), modelPerson.getNote());
    }

    @Test
    public void toModelType_invalidNote_throwsIllegalValueException() {
        // EP (invalid): malformed note content.
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                INVALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);

        assertThrows(IllegalValueException.class, Note.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        // EP (invalid): at least one tag is invalid.
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPerson person =
                new JsonAdaptedPerson(VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS, VALID_NOTE, invalidTags,
                        VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        assertThrows(IllegalValueException.class, person::toModelType);
    }

    @Test
    public void toModelType_invalidVisitDateTime_throwsIllegalValueException() {
        // EP (invalid): malformed visit date-time.
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, INVALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        assertThrows(IllegalValueException.class, VisitDateTime.MESSAGE_CONSTRAINTS, person::toModelType);
    }

    @Test
    public void toModelType_validVisitDateTime_success() throws Exception {
        // EP (valid): valid visit date-time should deserialize successfully.
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, VALID_IS_ARCHIVED);
        assertEquals(BENSON, person.toModelType());
    }

    @Test
    public void toModelType_permissiveVisitDateTime_normalizesStoredValue() throws Exception {
        // BVA: day overflow at month end should be coerced to the last valid day.
        JsonAdaptedPerson person = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, "2026-04-31 10:00", VALID_IS_ARCHIVED);

        Person modelPerson = person.toModelType();
        assertEquals("2026-04-30 10:00", modelPerson.getVisitDateTime().toString());
    }

    @Test
    public void toModelType_archivedPerson_returnsArchived() throws Exception {
        // EP (valid): archived flag persisted as true.
        Person archivedPerson = new Person(BENSON.getName(), BENSON.getPhone(), BENSON.getEmail(),
                BENSON.getAddress(), BENSON.getNote(), BENSON.getTags(), BENSON.getVisitDateTime(),
                BENSON.isArchived());
        archivedPerson.setArchived(true);

        JsonAdaptedPerson adaptedPerson = new JsonAdaptedPerson(archivedPerson);
        Person modelPerson = adaptedPerson.toModelType();

        assertEquals(true, modelPerson.isArchived());
    }

    @Test
    public void toModelType_missingIsArchived_defaultsToFalse() throws Exception {
        // EP (valid optional/backward compatibility): missing archive flag defaults to false.
        // Simulate old JSON where isArchived is null
        JsonAdaptedPerson adaptedPerson = new JsonAdaptedPerson(
                VALID_NAME, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                VALID_NOTE, VALID_TAGS, VALID_VISIT_DATE_TIME, null); // null simulates old file

        Person modelPerson = adaptedPerson.toModelType();

        assertEquals(false, modelPerson.isArchived());
    }

    @Test
    public void toModelType_unarchivedPerson_returnsUnarchived() throws Exception {
        // EP (valid): archived flag persisted as false.
        Person person = new Person(BENSON.getName(), BENSON.getPhone(), BENSON.getEmail(),
                BENSON.getAddress(), BENSON.getNote(), BENSON.getTags(), BENSON.getVisitDateTime(),
                BENSON.isArchived());
        person.setArchived(false);

        JsonAdaptedPerson adaptedPerson = new JsonAdaptedPerson(person);
        Person modelPerson = adaptedPerson.toModelType();

        assertEquals(false, modelPerson.isArchived());
    }
}
