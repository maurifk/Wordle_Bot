package wordle.bot;

import java.util.List;

import wordle.bot.exceptions.ExceptionAlgorithm;
import wordle.bot.exceptions.ExceptionReader;
import wordle.bot.logic.algorithm.OriginalAlgorithm;
import wordle.bot.logic.wordreader.SelectedLanguage;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        BotController.startBot(SelectedLanguage.SPANISH, new OriginalAlgorithm());
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime - startTime)/1000 + " s");

        IAlgorithmController algorithmController = Factory.getAlgorithmController();

        List<String> best = null;
        try {
            best = algorithmController.getBestWords();
        } catch (ExceptionAlgorithm | ExceptionReader e) {
            e.printStackTrace();
        }

        int i = 1;
        for (String word : best) {
            System.out.println(i++ + " " + word);
        }
    }
}
