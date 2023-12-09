package wordle.bot.logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.javatuples.Pair;

public class WordReader {
    private ArrayList<String> wordsList;
    public static int spanishWords = 10836;
    public static int englishWords = 12972;
    public static int englishFiltered = 2315;
    
    private SelectedLanguage selectedLanguage;

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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            wordsList.add(line);
        }
        bufferedReader.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    public Pair<SelectedLanguage, Integer> getWordsData() {
        return new Pair<SelectedLanguage, Integer>(selectedLanguage, wordsList.size());
    }

    public ArrayList<String> getWordsList() {
        return wordsList;
    }
}
