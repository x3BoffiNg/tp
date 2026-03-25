package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.time.LocalDate;
import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsPredicate;
import seedu.address.model.person.VisitContainsDatePredicate;
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG,
                        PREFIX_DATE, PREFIX_START_DATE, PREFIX_END_DATE);

        // Checks if there exist any tags for finding
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String keyword = argMultimap.getValue(PREFIX_NAME).get();
            NameContainsKeywordsPredicate predicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(keyword.split("\\s+")));

            return new FindCommand(predicate);

        } else if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            String tag = argMultimap.getValue(PREFIX_TAG).get();
            TagContainsPredicate predicate = new TagContainsPredicate(tag);

            return new FindCommand(predicate);

        } else if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            String dateValue = argMultimap.getValue(PREFIX_DATE).get().trim();
            LocalDate targetDate;

            if (dateValue.equalsIgnoreCase("today")) {
                targetDate = LocalDate.now();
            } else {
                targetDate = ParserUtil.parseDate(dateValue);
            }

            return new FindCommand(new VisitContainsDatePredicate(targetDate, targetDate));

        } else if (argMultimap.getValue(PREFIX_START_DATE).isPresent()
                && argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            LocalDate startDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_START_DATE).get());
            LocalDate endDate = ParserUtil.parseDate(argMultimap.getValue(PREFIX_END_DATE).get());

            if (startDate.isAfter(endDate)) {
                throw new ParseException("Start date cannot be after end date!");
            }

            return new FindCommand(new VisitContainsDatePredicate(startDate, endDate));

        } else {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }
}
