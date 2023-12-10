package wordle.bot.logic.wordreader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import wordle.bot.logic.algorithm.helpers.Base3;
import java.io.FileWriter;
import java.io.IOException;

public class GenerateMatchings {

    public static int getColors(String playedWord, String answer) {
        StringBuilder sb = new StringBuilder(answer);
        StringBuilder colors = new StringBuilder();

        for (int i = 0; i < playedWord.length(); i++) {
            if (playedWord.charAt(i) == sb.charAt(i)) {
                colors.append(2);
                sb.setCharAt(i, ' ');
            } else if (sb.indexOf(playedWord.charAt(i) + "") != -1) {
                colors.append(1);
                sb.setCharAt(sb.indexOf(String.valueOf(playedWord.charAt(i))), ' ');
            } else {
                colors.append(0);
            }
        }
        return Base3.toInteger(colors.toString());
    }

    public static int[][] generateMatrix(SelectedLanguage language) {
        WordReader wordReader = new WordReader(language);
        List<String> constantWords = wordReader.getWordsList();

        int[][] matchings = new int[constantWords.size()][constantWords.size()];
        for (int i = 0; i < constantWords.size(); i++) {
            for (int j = 0; j < constantWords.size(); j++) {
                matchings[i][j] = getColors(constantWords.get(i), constantWords.get(j));
            }
        }
        return matchings;
    }

    private int[][] saveMatrix(SelectedLanguage language) {
        int[][] matchings = generateMatrix(language);
        String lang = language == SelectedLanguage.ENGLISH ? "ENG" : language == SelectedLanguage.SPANISH ? "ESP" : "ENGF";
        System.out.println("Saving matrix" + lang + ".csv");
        try {
            FileWriter writer = new FileWriter("./matrix" + lang + ".csv");

            for (int i = 0; i < matchings.length; i++) {
                for (int j = 0; j < matchings[i].length; j++) {
                    writer.append(String.valueOf(matchings[i][j]));
                    if (j != matchings[i].length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return matchings;
    }

    public void test() {
        
    }

    public int[][] readMatrix(SelectedLanguage language) {
        String lang = language == SelectedLanguage.ENGLISH ? "ENG" : language == SelectedLanguage.SPANISH ? "ESP" : "ENGF";
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("./matrix" + lang + ".csv");
        } catch (FileNotFoundException e) {
            return saveMatrix(language);
        }

        int[][] matchings = new int[WordReader.getAmmountWords(language)][WordReader.getAmmountWords(language)];
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                for (int j = 0; j < data.length; j++) {
                        matchings[i][j] = Integer.parseInt(data[j].trim());
                }
                i++;
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchings;
    }
}
