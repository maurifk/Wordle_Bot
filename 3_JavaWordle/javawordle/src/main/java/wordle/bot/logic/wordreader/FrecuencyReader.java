package wordle.bot.logic.wordreader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class FrecuencyReader {
    private Map<String, Integer> wordFrequencies;

    public FrecuencyReader() {
        wordFrequencies = new HashMap<>();
    }

    public Map<String, Integer> getWordFrequencies() {
        return wordFrequencies;
    }

    public void loadCSV(SelectedLanguage language) {
        String lang = language == SelectedLanguage.ENGLISH ? "ENG" : language == SelectedLanguage.SPANISH ? "ESP" : "ENGF";
        try {
            InputStream inputStream = getClass().getResourceAsStream("/wordsFrequency" + lang + ".csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String word = data[1].trim();
                    int frequency = Integer.parseInt(data[0].trim());

                    wordFrequencies.put(word, frequency);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
