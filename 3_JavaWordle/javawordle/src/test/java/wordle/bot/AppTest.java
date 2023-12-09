package wordle.bot;

import org.junit.jupiter.api.Test;

import wordle.bot.logic.SelectedLanguage;
import wordle.bot.logic.WordReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */

class AppTest {

    @Test
    void testApp() {
        assertEquals(1, 1);
    }
    
    @Test
    void testReadSpanishWords() {
        WordReader wordReader = new WordReader(SelectedLanguage.SPANISH);
        assertEquals(WordReader.spanishWords, wordReader.getWordsList().size());
    }

    @Test
    void testReadEnglishWords() {
        WordReader wordReader = new WordReader(SelectedLanguage.ENGLISH);
        assertEquals(WordReader.englishWords, wordReader.getWordsList().size());
    }

    @Test
    void testReadEnglishFilteredWords() {
        WordReader wordReader = new WordReader(SelectedLanguage.ENGLISH_FILTERED);
        assertEquals(WordReader.englishFiltered, wordReader.getWordsList().size());
    }
}
