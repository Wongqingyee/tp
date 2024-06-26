package seedu.teachstack.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.teachstack.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.teachstack.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.teachstack.logic.commands.CommandTestUtil.GROUP_DESC_1;
import static seedu.teachstack.logic.commands.CommandTestUtil.GROUP_DESC_2B;
import static seedu.teachstack.model.util.SampleDataUtil.getGroupSet;
import static seedu.teachstack.testutil.Assert.assertThrows;
import static seedu.teachstack.testutil.TypicalStudentIds.ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.teachstack.logic.commands.AddCommand;
import seedu.teachstack.logic.commands.ArchiveCommand;
import seedu.teachstack.logic.commands.ClearCommand;
import seedu.teachstack.logic.commands.DeleteCommand;
import seedu.teachstack.logic.commands.EditCommand;
import seedu.teachstack.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.teachstack.logic.commands.ExitCommand;
import seedu.teachstack.logic.commands.FindCommand;
import seedu.teachstack.logic.commands.HelpCommand;
import seedu.teachstack.logic.commands.ListCommand;
import seedu.teachstack.logic.commands.RandomCommand;
import seedu.teachstack.logic.commands.SummaryCommand;
import seedu.teachstack.logic.commands.ViewCommand;
import seedu.teachstack.logic.parser.exceptions.ParseException;
import seedu.teachstack.model.person.Person;
import seedu.teachstack.model.person.PersonInGroupPredicate;
import seedu.teachstack.testutil.EditPersonDescriptorBuilder;
import seedu.teachstack.testutil.PersonBuilder;
import seedu.teachstack.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + ID_FIRST_PERSON);
        assertEquals(new DeleteCommand(ID_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + ID_FIRST_PERSON + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(ID_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + GROUP_DESC_1 + GROUP_DESC_2B);
        assertEquals(new FindCommand(new PersonInGroupPredicate(getGroupSet("Group 2B", "Group 1"))), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_view() throws Exception {
        ViewCommand command = (ViewCommand) parser.parseCommand(
                ViewCommand.COMMAND_WORD + " " + ID_FIRST_PERSON);
        assertEquals(new ViewCommand(ID_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_random() throws Exception {
        RandomCommand command = (RandomCommand) parser.parseCommand(
                RandomCommand.COMMAND_WORD + " 1 " + PersonUtil.getGroupDetails("Consultation Group"));
        assertEquals(new RandomCommand("Consultation Group", 1), command);
    }

    @Test
    public void parseCommand_archive() throws Exception {
        ArchiveCommand command = (ArchiveCommand) parser.parseCommand(
                ArchiveCommand.COMMAND_WORD + " " + ID_FIRST_PERSON);
        assertEquals(new ArchiveCommand(ID_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }

    @Test
    public void parseCommand_summary() throws Exception {
        AddressBookParser parser = new AddressBookParser();
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD) instanceof SummaryCommand);
        assertTrue(parser.parseCommand(SummaryCommand.COMMAND_WORD + " 3") instanceof SummaryCommand);
    }
}
