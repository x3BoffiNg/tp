package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    @Test
    public void add_blankCommand_notStored() {
        history.add("  ");
        assertEquals(0, history.size());
    }

    @Test
    public void add_nullCommand_notStored() {
        history.add(null);
        assertEquals(0, history.size());
    }

    @Test
    public void add_validCommand_stored() {
        history.add("list");
        assertEquals(1, history.size());
    }

    @Test
    public void navigateUp_emptyHistory_returnsEmpty() {
        assertEquals("", history.navigateUp());
    }

    @Test
    public void navigateDown_emptyHistory_returnsEmpty() {
        assertEquals("", history.navigateDown());
    }

    @Test
    public void navigateUp_singleEntry_returnsThatEntry() {
        history.add("list");
        assertEquals("list", history.navigateUp());
    }

    @Test
    public void navigateUp_repeatedAtOldest_returnsOldestEntry() {
        history.add("cmd1");
        history.add("cmd2");
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"
        // pressing UP again should stay at "cmd1"
        assertEquals("cmd1", history.navigateUp());
    }

    @Test
    public void navigateUp_multipleEntries_returnsInReverseOrder() {
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");
        assertEquals("cmd3", history.navigateUp());
        assertEquals("cmd2", history.navigateUp());
        assertEquals("cmd1", history.navigateUp());
    }

    @Test
    public void navigateDown_withoutPriorNavigateUp_returnsEmpty() {
        history.add("list");
        assertEquals("", history.navigateDown());
    }

    @Test
    public void navigateDown_afterNavigateUpToOldest_returnsNewerThenEmpty() {
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");
        history.navigateUp(); // "cmd3"
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"
        assertEquals("cmd2", history.navigateDown());
        assertEquals("cmd3", history.navigateDown());
        // past newest -> empty string (clears the field)
        assertEquals("", history.navigateDown());
    }

    @Test
    public void navigateDown_pastNewest_returnsEmpty() {
        history.add("list");
        history.navigateUp(); // "list"
        history.navigateDown(); // past newest -> ""
        assertEquals("", history.navigateDown()); // already past newest, stays ""
    }

    @Test
    public void add_afterNavigation_resetsPointer() {
        history.add("cmd1");
        history.add("cmd2");
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"

        // Adding a new command should reset the pointer
        history.add("cmd3");
        // UP from the fresh pointer hits "cmd3" first
        assertEquals("cmd3", history.navigateUp());
    }
}
