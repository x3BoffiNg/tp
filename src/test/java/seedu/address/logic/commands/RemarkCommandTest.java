package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

public class RemarkCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToEdit =
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Remark remark = new Remark("New remark");
        RemarkCommand remarkCommand =
                new RemarkCommand(INDEX_FIRST_PERSON, remark);

        Person editedPerson = new PersonBuilder(personToEdit)
                .withRemark("New remark")
                .build();

        Model expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage =
                String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                        Messages.format(editedPerson));

        assertCommandSuccess(remarkCommand, model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyRemark_success() {
        Person personToEdit =
                model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        Remark remark = new Remark("");
        RemarkCommand remarkCommand =
                new RemarkCommand(INDEX_FIRST_PERSON, remark);

        Person editedPerson = new PersonBuilder(personToEdit)
                .withRemark("")
                .build();

        Model expectedModel =
                new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage =
                String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                        Messages.format(editedPerson));

        assertCommandSuccess(remarkCommand, model,
                expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex =
                Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        RemarkCommand remarkCommand =
                new RemarkCommand(outOfBoundIndex, new Remark("Test"));

        assertCommandFailure(remarkCommand, model,
                MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Remark remark1 = new Remark("Remark 1");
        Remark remark2 = new Remark("Remark 2");

        RemarkCommand command1 =
                new RemarkCommand(INDEX_FIRST_PERSON, remark1);
        RemarkCommand command2 =
                new RemarkCommand(INDEX_FIRST_PERSON, remark1);
        RemarkCommand command3 =
                new RemarkCommand(INDEX_FIRST_PERSON, remark2);

        // same object
        assertTrue(command1.equals(command1));

        // same values
        assertTrue(command1.equals(command2));

        // different remark
        assertFalse(command1.equals(command3));

        // null
        assertFalse(command1.equals(null));

        // different type
        assertFalse(command1.equals(new ClearCommand()));
    }
}

