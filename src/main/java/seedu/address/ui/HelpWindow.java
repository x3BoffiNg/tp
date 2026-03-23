package seedu.address.ui;

import java.util.logging.Logger;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NoteCommand;
import seedu.address.logic.commands.TagCommand;

/**
 * Controller for a help page
 */
public class HelpWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay2526s2-cs2103-f11-1.github.io/tp/UserGuide.html";
    public static final String HELP_MESSAGE = "Refer to the user guide: " + USERGUIDE_URL;
    private static final String COPY_BUTTON_DEFAULT_TEXT = "Copy URL";
    private static final String COPY_BUTTON_COPIED_TEXT = "Copied!";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "HelpWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label helpMessage;

    @FXML
    private ScrollPane commandScrollPane;

    @FXML
    private VBox commandContainer;

    private final PauseTransition copyFeedbackReset = new PauseTransition(Duration.seconds(1.2));

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public HelpWindow(Stage root) {
        super(FXML, root);
        helpMessage.setText(HELP_MESSAGE);
        copyButton.setText(COPY_BUTTON_DEFAULT_TEXT);
        copyButton.setOnAction(event -> copyUrl());
        copyFeedbackReset.setOnFinished(event -> copyButton.setText(COPY_BUTTON_DEFAULT_TEXT));
        populateCommandHelp();
    }

    /**
     * Creates a new HelpWindow.
     */
    public HelpWindow() {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     *     <ul>
     *         <li>
     *             if this method is called on a thread other than the JavaFX Application Thread.
     *         </li>
     *         <li>
     *             if this method is called during animation or layout processing.
     *         </li>
     *         <li>
     *             if this method is called on the primary stage.
     *         </li>
     *         <li>
     *             if {@code dialogStage} is already showing.
     *         </li>
     *     </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);

        copyButton.setText(COPY_BUTTON_COPIED_TEXT);
        copyFeedbackReset.stop();
        copyFeedbackReset.playFromStart();
    }

    /**
     * Populates the command help section with styled command information.
     */
    private void populateCommandHelp() {
        commandContainer.getChildren().clear();
        commandContainer.setSpacing(15);
        commandContainer.setPadding(new Insets(10));
        commandContainer.setFillWidth(true);

        addCommandSection("add", "Add a new person to the address book", AddCommand.MESSAGE_USAGE);
        addCommandSection("edit", "Edit an existing person's details", EditCommand.MESSAGE_USAGE);
        addCommandSection("delete", "Delete a person by index", DeleteCommand.MESSAGE_USAGE);
        addCommandSection("list", "List all persons (with optional sorting)", ListCommand.MESSAGE_USAGE);
        addCommandSection("find", "Find persons by name or tag", FindCommand.MESSAGE_USAGE);
        addCommandSection("tag", "Add or delete tags for a person", TagCommand.MESSAGE_USAGE);
        addCommandSection("note", "Edit a person's note", NoteCommand.MESSAGE_USAGE);
        addCommandSection("clear", "Clear all contacts from the address book",
                "Usage: " + ClearCommand.COMMAND_WORD);
        addCommandSection("help", "Open this help window", "Usage: " + HelpCommand.COMMAND_WORD);
        addCommandSection("exit", "Exit the application", "Usage: " + ExitCommand.COMMAND_WORD);
    }

    /**
     * Adds a command section to the help container.
     *
     * @param commandName Name of the command
     * @param description Brief description
     * @param usageAndExamples Usage details and optional examples
     */
    private void addCommandSection(String commandName, String description, String usageAndExamples) {
        Label nameLabel = createCommandNameLabel(commandName);
        String[] parsedHelpText = parseHelpText(usageAndExamples);
        String descriptionText = parsedHelpText[0].isEmpty() ? description : parsedHelpText[0];

        Label descriptionLabel = createDescriptionLabel(descriptionText);
        Label usageLabel = createCodeBlockLabel(parsedHelpText[1], "#9ad0ff");
        Label examplesLabel = createCodeBlockLabel(parsedHelpText[2], "#ffd479");

        VBox commandBox = createCommandBox(nameLabel, descriptionLabel, usageLabel, examplesLabel);
        commandContainer.getChildren().add(commandBox);
    }

    private Label createCommandNameLabel(String commandName) {
        Label nameLabel = new Label(commandName.toUpperCase());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #4db8ff;");
        return nameLabel;
    }

    private Label createDescriptionLabel(String description) {
        Label descriptionLabel = new Label(description);
        descriptionLabel.setStyle("-fx-text-fill: #e0e0e0; -fx-font-size: 15;");
        descriptionLabel.setWrapText(true);
        return descriptionLabel;
    }

    private String[] parseHelpText(String usageAndExamples) {
        String usageWithoutExamples = usageAndExamples;
        String examplesText = "";

        int firstExampleIndex = usageAndExamples.indexOf("\nExample:");
        if (firstExampleIndex >= 0) {
            usageWithoutExamples = usageAndExamples.substring(0, firstExampleIndex).trim();
            examplesText = usageAndExamples.substring(firstExampleIndex + 1).trim();
        }

        String descriptionText = "";
        String usageText = usageWithoutExamples;
        int parametersIndex = usageWithoutExamples.indexOf("Parameters:");

        if (parametersIndex >= 0) {
            String descriptionFromUsage = usageWithoutExamples.substring(0, parametersIndex).trim();
            usageText = usageWithoutExamples.substring(parametersIndex).trim();

            int colonIndex = descriptionFromUsage.indexOf(':');
            if (colonIndex >= 0 && colonIndex + 1 < descriptionFromUsage.length()) {
                descriptionFromUsage = descriptionFromUsage.substring(colonIndex + 1).trim();
            }
            descriptionText = descriptionFromUsage.replace("\n", " ").trim();
        }

        return new String[] {descriptionText, usageText, examplesText};
    }

    private Label createCodeBlockLabel(String text, String textColor) {
        Label codeBlockLabel = new Label(text);
        codeBlockLabel.setStyle("-fx-text-fill: " + textColor + "; -fx-font-family: 'Courier New'; "
                + "-fx-font-size: 15; -fx-background-color: #2a2a2a; -fx-padding: 10;");
        codeBlockLabel.setWrapText(true);
        codeBlockLabel.setMaxWidth(Double.MAX_VALUE);
        codeBlockLabel.setManaged(!text.isEmpty());
        codeBlockLabel.setVisible(!text.isEmpty());
        return codeBlockLabel;
    }

    private VBox createCommandBox(Label nameLabel, Label descriptionLabel, Label usageLabel, Label examplesLabel) {
        VBox commandBox = new VBox(5);
        commandBox.setStyle("-fx-border-color: #4db8ff; -fx-border-width: 0 0 0 4; -fx-padding: 12;");
        commandBox.setMaxWidth(Double.MAX_VALUE);
        commandBox.getChildren().addAll(nameLabel, descriptionLabel, usageLabel, examplesLabel);
        return commandBox;
    }
}
