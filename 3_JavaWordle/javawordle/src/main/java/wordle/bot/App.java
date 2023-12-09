package wordle.bot;

import java.util.ArrayList;

/**
 * Hello world!
 */
public final class App {
    private App() {
    }

    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        ArrayList<String> wordsList;
        String line = System.console().readLine();
        if (line.startsWith("engF")) {
            wordsList = new WordReader(SelectedLanguage.ENGLISH_FILTERED).getWordsList();
        } else if (line.startsWith("eng")) {
            wordsList = new WordReader(SelectedLanguage.ENGLISH).getWordsList();
        } else {
            wordsList = new WordReader(SelectedLanguage.SPANISH).getWordsList();
        }
        System.out.println(wordsList.size());
    }
}
