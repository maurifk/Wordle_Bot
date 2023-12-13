package wordle.bot.logic.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.javatuples.Pair;

import wordle.bot.Factory;
import wordle.bot.logic.wordreader.MatchingsController;

public class OriginalAlgorithm implements IAlgorithm {
    List<String> dictionary;
    PriorityQueue<Pair<String, Integer>> bestWords = null;
    MatchingsController matchingsController = null;

    @Override
    public void startAlgorithm() {
        matchingsController = Factory.getWordsController().getMatchingsController();
    }

    @Override
    public void setDictionary(List<String> words) {
        this.dictionary = words;
    }

    public void setMatchingsController(MatchingsController matchingsController) {
        this.matchingsController = matchingsController;
    }

    @Override
    public List<String> getBestWords(List<String> wordsLeft, int maxWords, boolean useAllWords) {
        List<String> words = useAllWords ? this.dictionary : wordsLeft;
        HashMap<String, Integer> wordsMap = new HashMap<String, Integer>();
        bestWords = new PriorityQueue<Pair<String, Integer>>(words.size(), (Pair<String, Integer> p1, Pair<String, Integer> p2) -> p2.getValue1() - p1.getValue1());

        for (String word : words) {
            int score = 0;
            for (String wordPossible : wordsLeft) {
                score += matchingsController.getColors(word, wordPossible);
            }
            wordsMap.put(word, score);
            bestWords.add(new Pair<String,Integer>(word, score));
        }

        LinkedList<String> bestWordsList = new LinkedList<String>();
        for (int i = 0; i < maxWords; i++) {
            if (bestWords.isEmpty())
                break;

            bestWordsList.add(bestWords.poll().getValue0());
        }

        return bestWordsList;
    }
}
