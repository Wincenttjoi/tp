package seedu.address.logic.commands.results;

import static java.util.Objects.requireNonNull;

/**
 * Represents the result of the Help command execution.
 */
public class HelpCommandResult extends CommandResult {

    private String popUpContent = "";
    private String dataToUser;

    /**
     * Constructs a {@code HelpCommandResult} with the specified {@code dataToUser}, {@code feedbackToUser}, and
     * {@code displayType}.
     * @param feedbackToUser feedback to user.
     * @param showHelp whether it is showHelp.
     * @param dataToUser data to show user.
     * @param popUpContent the content to show in UI.
     */
    public HelpCommandResult(String feedbackToUser, boolean showHelp, boolean showPreview, boolean exit, String dataToUser, String popUpContent) {
        super(feedbackToUser, showHelp, showPreview, exit);
        this.dataToUser = requireNonNull(dataToUser);
        this.popUpContent = requireNonNull(popUpContent);
    }

    public String getPopUpContent() {
        return popUpContent;
    }
}
