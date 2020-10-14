package seedu.address.logic.parser.deliveryparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ORDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.deliverycommand.DeliveryFindCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DeliveryContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DeliveryFindCommand object
 */
public class DeliveryFindCommandParser implements Parser<DeliveryFindCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeliveryFindCommand
     * and returns a DeliveryFindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeliveryFindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_ORDER);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_ORDER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeliveryFindCommand.MESSAGE_USAGE));
        }

        Prefix type = null;
        String trimmedArgs = null;

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            trimmedArgs = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()).fullName;
            type = PREFIX_NAME;
        } else if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            trimmedArgs = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()).value;
            type = PREFIX_ADDRESS;
        } else if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            trimmedArgs = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()).value;
            type = PREFIX_PHONE;
        } else if (argMultimap.getValue(PREFIX_ORDER).isPresent()) {
            trimmedArgs = ParserUtil.parseOrder(argMultimap.getValue(PREFIX_ORDER).get()).value;
            type = PREFIX_ORDER;
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new DeliveryFindCommand(new DeliveryContainsKeywordsPredicate(Arrays.asList(nameKeywords), type));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}