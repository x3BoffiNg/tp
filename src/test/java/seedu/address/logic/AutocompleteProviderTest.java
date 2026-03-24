package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AutocompleteProviderTest {

    @Test
    public void suggestCompletion_partialCommand_returnsCompletedCommand() {
        assertEquals("add", AutocompleteProvider.suggestCompletion("a").orElseThrow());
    }

    @Test
    public void suggestCompletion_exactCommandWithExpectedPrefix_returnsPrefixSuggestion() {
        assertTrue(AutocompleteProvider.suggestCompletion("add").isEmpty());
    }

    @Test
    public void suggestCompletion_commandWithSpace_returnsPrefixSuggestion() {
        assertEquals("add n/", AutocompleteProvider.suggestCompletion("add ").orElseThrow());
    }

    @Test
    public void suggestCompletion_partialPrefix_returnsCompletedPrefix() {
        assertEquals("add n/", AutocompleteProvider.suggestCompletion("add n").orElseThrow());
    }

    @Test
    public void suggestCompletion_addAfterNamePrefixSuggestsNextPrefix() {
        assertEquals("add n/Amy p/", AutocompleteProvider.suggestCompletion("add n/Amy ").orElseThrow());
    }

    @Test
    public void suggestCompletion_addAfterAllPrefixesSuggestsRepeatableTag() {
        String input = "add n/Amy p/91234567 e/test@example.com a/Blk 1 nt/note v/2026-12-01 14:00 t/client ";
        assertEquals(input + "t/", AutocompleteProvider.suggestCompletion(input).orElseThrow());
    }

    @Test
    public void suggestCompletion_completedPrefix_returnsNoSuggestion() {
        assertTrue(AutocompleteProvider.suggestCompletion("add n/").isEmpty());
    }

    @Test
    public void suggestCompletion_unknownInput_returnsNoSuggestion() {
        assertTrue(AutocompleteProvider.suggestCompletion("zz").isEmpty());
    }

    @Test
    public void suggestCompletion_findCommandPrefixSuggestions_work() {
        assertEquals("find n/", AutocompleteProvider.suggestCompletion("find ").orElseThrow());
        assertEquals("find t/", AutocompleteProvider.suggestCompletion("find t").orElseThrow());
    }

    @Test
    public void suggestCompletion_listCommandPrefixSuggestions_work() {
        assertEquals("list s/", AutocompleteProvider.suggestCompletion("list ").orElseThrow());
        assertEquals("list s/", AutocompleteProvider.suggestCompletion("list s").orElseThrow());
        assertTrue(AutocompleteProvider.suggestCompletion("list s/name ").isEmpty());
    }

    @Test
    public void suggestCompletion_edit_requiresIndexBeforePrefixSuggestion() {
        assertTrue(AutocompleteProvider.suggestCompletion("edit ").isEmpty());
        assertTrue(AutocompleteProvider.suggestCompletion("edit 1").isPresent());
        assertEquals("edit 1 n/", AutocompleteProvider.suggestCompletion("edit 1").orElseThrow());
        assertEquals("edit 1 p/", AutocompleteProvider.suggestCompletion("edit 1 p").orElseThrow());
        assertEquals("edit 1 n/Bob p/", AutocompleteProvider.suggestCompletion("edit 1 n/Bob ").orElseThrow());

        String allPrefixesInput =
                "edit 1 n/Bob p/91234567 e/test@example.com a/Blk 1 nt/note v/2026-12-01 14:00 t/client ";
        assertEquals(allPrefixesInput + "t/", AutocompleteProvider.suggestCompletion(allPrefixesInput).orElseThrow());
    }

    @Test
    public void suggestCompletion_note_requiresIndexBeforePrefixSuggestion() {
        assertTrue(AutocompleteProvider.suggestCompletion("note ").isEmpty());
        assertEquals("note 1 nt/", AutocompleteProvider.suggestCompletion("note 1").orElseThrow());
        assertEquals("note 1 nt/", AutocompleteProvider.suggestCompletion("note 1 nt").orElseThrow());
        assertTrue(AutocompleteProvider.suggestCompletion("note 1 nt/hello ").isEmpty());
    }

    @Test
    public void suggestCompletion_tag_requiresIndexBeforePrefixSuggestion() {
        assertTrue(AutocompleteProvider.suggestCompletion("tag ").isEmpty());
        assertEquals("tag 1 at/", AutocompleteProvider.suggestCompletion("tag 1").orElseThrow());
        assertEquals("tag 1 dt/", AutocompleteProvider.suggestCompletion("tag 1 d").orElseThrow());
        assertTrue(AutocompleteProvider.suggestCompletion("tag 1 at/friend dt/work ").isEmpty());
    }

    @Test
    public void suggestCompletion_leadingWhitespace_stillSuggestsCommand() {
        assertEquals(" add", AutocompleteProvider.suggestCompletion(" a").orElseThrow());
        assertEquals("          add", AutocompleteProvider.suggestCompletion("          a").orElseThrow());
    }

    @Test
    public void suggestCompletion_leadingWhitespaceBeforeCommand_returnsPrefixSuggestion() {
        assertEquals(" add n/", AutocompleteProvider.suggestCompletion(" add ").orElseThrow());
        assertEquals("          add n/", AutocompleteProvider.suggestCompletion("          add ").orElseThrow());
    }

    @Test
    public void suggestCompletion_leadingWhitespaceBeforePrefix_returnsCompletedPrefix() {
        assertEquals("add   n/", AutocompleteProvider.suggestCompletion("add   n").orElseThrow());
        assertEquals("          add         n/",
                AutocompleteProvider.suggestCompletion("          add         n").orElseThrow());
    }
}
