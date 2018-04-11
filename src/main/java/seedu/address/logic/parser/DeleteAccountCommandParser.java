package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;


import seedu.address.logic.commands.DeleteAccountCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.account.UniqueAccountList;


import static seedu.address.commons.core.Messages.MESSAGE_ACCOUNT_NOT_FOUND;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteAccountCommandParser implements Parser<DeleteAccountCommand> {
    /**
     * Parses input arguments and creates a new DeleteAccountCommand object
     * <p>
     * /**
     * Parses the given {@code String} of arguments in the context of the DeleteAccountCommand
     * and returns an DeleteAccountCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */

    public DeleteAccountCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAccountCommand.MESSAGE_USAGE));
        }

        int temp = -1;

        for (int i = 0; i < UniqueAccountList.internalList.size(); i++) {
            System.out.println(UniqueAccountList.internalList.size());
            System.out.println(UniqueAccountList.internalList.get(i).getName().fullName);
            if (trimmedArgs == UniqueAccountList.internalList.get(i).getName().fullName) {
                temp = i;
            }
            System.out.println(temp);
        }
        if (temp != -1) {
            return new DeleteAccountCommand(UniqueAccountList.internalList.get(temp));
        } else {
            System.out.println(trimmedArgs);
            throw new ParseException(
                    String.format(MESSAGE_ACCOUNT_NOT_FOUND));
        }
    }
}

