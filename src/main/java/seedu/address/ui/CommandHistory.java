package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and manages navigation through the history of commands entered by the
 * user.
 */
public class CommandHistory {

    private final List<String> history = new ArrayList<>();
    private int pointer = 0;

    /**
     * Appends {@code command} to the history and resets the navigation pointer past
     * the newest entry so that subsequent UP presses start from the most-recently
     * added command.
     *
     * Blank commands are silently ignored.
     */
    public void add(String command) {
        if (command == null || command.isBlank()) {
            return;
        }
        history.add(command);
        pointer = history.size();
    }

    /**
     * Moves the pointer one step toward older commands and returns the command at
     * that position.
     * If the pointer is already at the oldest command it stays there and that
     * command is returned.
     *
     * @return the older command at the new pointer position, or {@code ""} if
     *         history is empty.
     */
    public String navigateUp() {
        if (history.isEmpty()) {
            return "";
        }
        pointer = Math.max(0, pointer - 1);
        return history.get(pointer);
    }

    /**
     * Moves the pointer one step toward newer commands and returns the command at
     * that position.
     * If the pointer moves past the newest entry (i.e. reaches
     * {@code history.size()}),
     * {@code ""} is returned so the caller can clear the text field.
     *
     * @return the newer command at the new pointer position, or {@code ""} when
     *         past the newest entry.
     */
    public String navigateDown() {
        if (pointer < history.size()) {
            pointer++;
        }
        if (pointer >= history.size()) {
            return "";
        }
        return history.get(pointer);
    }

    /**
     * Returns the number of commands currently stored in the history.
     */
    public int size() {
        return history.size();
    }
}
