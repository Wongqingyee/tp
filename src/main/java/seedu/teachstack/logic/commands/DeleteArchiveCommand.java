package seedu.teachstack.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.teachstack.commons.util.ToStringBuilder;
import seedu.teachstack.logic.Messages;
import seedu.teachstack.logic.commands.exceptions.CommandException;
import seedu.teachstack.model.Model;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.StudentId;

/**
 * Deletes a person identified using it's student id from the archived book.
 */
public class DeleteArchiveCommand extends Command {

    public static final String COMMAND_WORD = "delete_archived";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by their student id.\n"
            + "Parameters: STUDENT_ID (must be in format Axxxxxxx[A-Z] where x can be any digit, "
            + "[A-Z] can be any capital letter).\n"
            + "Example: " + COMMAND_WORD + " A0123456A";

    public static final String MESSAGE_DELETE_ARCHIVE_PERSON_SUCCESS = "Deleted Archived Person: %1$s";

    private final StudentId targetId;

    public DeleteArchiveCommand(StudentId targetId) {
        this.targetId = targetId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToDelete = model.getArchivedPerson(targetId);

        if (personToDelete == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_STUDENT_ID);
        }

        model.deleteArchivedPerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_ARCHIVE_PERSON_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteArchiveCommand)) {
            return false;
        }

        DeleteArchiveCommand otherDeleteArchiveCommand = (DeleteArchiveCommand) other;
        return targetId.equals(otherDeleteArchiveCommand.targetId);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetId", targetId)
                .toString();
    }
}
