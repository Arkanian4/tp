package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code MarkDeliveredCommand}.
 */
public class MarkDeliveredCommandTest {

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkDeliveredCommand command = new MarkDeliveredCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                MarkDeliveredCommand.MESSAGE_MARK_DELIVERED_SUCCESS,
                Messages.format(personToMark));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkDeliveredCommand command = new MarkDeliveredCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /*
    !!! will be implemented once delivery status field is connected

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToMark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        MarkDeliveredCommand command = new MarkDeliveredCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(
                MarkDeliveredCommand.MESSAGE_MARK_DELIVERED_SUCCESS,
                Messages.format(personToMark));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundIndex.getZeroBased()
                < model.getAddressBook().getPersonList().size());

        MarkDeliveredCommand command = new MarkDeliveredCommand(outOfBoundIndex);

        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        MarkDeliveredCommand firstCommand = new MarkDeliveredCommand(INDEX_FIRST_PERSON);
        MarkDeliveredCommand secondCommand = new MarkDeliveredCommand(INDEX_SECOND_PERSON);

        // same object -> true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> true
        MarkDeliveredCommand firstCommandCopy = new MarkDeliveredCommand(INDEX_FIRST_PERSON);
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> false
        assertFalse(firstCommand.equals(1));

        // null -> false
        assertFalse(firstCommand.equals(null));

        // different index -> false
        assertFalse(firstCommand.equals(secondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        MarkDeliveredCommand command = new MarkDeliveredCommand(targetIndex);
        String expected = MarkDeliveredCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, command.toString());
    }
}
