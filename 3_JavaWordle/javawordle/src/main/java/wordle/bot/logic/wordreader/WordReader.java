package wordle.bot.logic.wordreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WordReader {
    private ArrayList<String> wordsList;
    public static int spanishWords = 10836;
    public static int englishWords = 12972;
    public static int englishFiltered = 2315;

    private SelectedLanguage selectedLanguage;

    public static int getAmmountWords(SelectedLanguage language) {
        switch (language) {
            case SPANISH:
                return spanishWords;
            case ENGLISH:
                return englishWords;
            case ENGLISH_FILTERED:
                return englishFiltered;
        }
        return 0;
    }

    public WordReader(SelectedLanguage selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
        wordsList = new ArrayList<String>();
        switch (selectedLanguage) {
            case SPANISH:
                loadFile("palabras5letras.txt");
                break;
            case ENGLISH:
                loadFile("palabrasENG.txt");
                break;
            case ENGLISH_FILTERED:
                loadFile("palabrasENGFil.txt");
                break;
        }
    }

    private void loadFile(String filename) {
    try {
        InputStream is = getClass().getResourceAsStream("/" + filename);
        if (is == null) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is, "UTF-8")); // Specify the encoding as UTF-8, Ñ failed
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            wordsList.add(line);
        }
        bufferedReader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public SelectedLanguage getSelectedLanguage() {
        return selectedLanguage;
    }

    public ArrayList<String> getWordsList() {
        return wordsList;
    }
}
