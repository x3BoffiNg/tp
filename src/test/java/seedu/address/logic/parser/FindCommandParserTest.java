package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        // correct syntax with prefix
        assertParseSuccess(parser, "find n/Alice Bob", expectedFindCommand);

        // allow surrounding whitespace
        assertParseSuccess(parser, "find   n/Alice Bob   ", expectedFindCommand);
    }

    @Test
    public void parse_tagArgs_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new TagContainsPredicate("friends"));

        assertParseSuccess(parser, "find t/friends", expected);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "find Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }


}
