package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BOX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.util.BoxUtil;
import seedu.address.logic.commands.util.CommandPersonUtil;
import seedu.address.model.Model;
import seedu.address.model.commons.name.Name;
import seedu.address.model.person.Box;
import seedu.address.model.person.Person;

/**
 * Adds one or more boxes to a person in the address book.
 */
public class AddBoxCommand extends Command {

    public static final String COMMAND_WORD = "addbox";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one or more boxes with a fixed subscription "
            + "to the person identified by the name used in the displayed person list.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_BOX + "BOX_NAME:MONTHS_SUBSCRIBED "
            + "[" + PREFIX_BOX + "BOX_NAME:MONTHS_SUBSCRIBED]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_BOX + "box-1:2 "
            + PREFIX_BOX + "box-2:3";

    public static final String MESSAGE_SUCCESS = "Added %1$s to Person: %2$s";
    public static final String MESSAGE_EXISTING_BOX_NAME = "One or more of the box names added already exists under "
            + "Person: %1$s. Use the editbox command to edit details for existing boxes.";
    public static final String MESSAGE_PERSON_NOT_FOUND = "The person with the input name does not exist.";

    private final Name subscriberName;
    private final Set<Box> boxesToAdd;

    /**
     * Creates an AddBoxCommand to add the boxes to a specified subscriber.
     */
    public AddBoxCommand(Name subscriberName, Set<Box> boxesToAdd) {
        requireNonNull(subscriberName);
        requireNonNull(boxesToAdd);
        this.subscriberName = subscriberName;
        this.boxesToAdd = boxesToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToEdit = findSubscriber(model);
        checkNoDuplicateBoxNames(personToEdit);

        Set<Box> updatedBoxes = BoxUtil.addBoxes(personToEdit, boxesToAdd);
        Person editedPerson = CommandPersonUtil.withBoxes(personToEdit, updatedBoxes);

        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, boxesToAdd, personToEdit.getName()));
    }

    private Person findSubscriber(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        return CommandPersonUtil.findPersonByName(lastShownList, subscriberName)
                .orElseThrow(() -> new CommandException(MESSAGE_PERSON_NOT_FOUND));
    }

    private void checkNoDuplicateBoxNames(Person personToEdit) throws CommandException {
        if (BoxUtil.hasMatchingBoxNames(personToEdit, boxesToAdd)) {
            throw new CommandException(String.format(MESSAGE_EXISTING_BOX_NAME, personToEdit.getName()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof AddBoxCommand)) {
            return false;
        }

        AddBoxCommand otherAddBoxCommand = (AddBoxCommand) other;
        return subscriberName.equals(otherAddBoxCommand.subscriberName)
                && boxesToAdd.equals(otherAddBoxCommand.boxesToAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("subscriberName", subscriberName)
                .add("boxesToAdd", boxesToAdd)
                .toString();
    }
}
