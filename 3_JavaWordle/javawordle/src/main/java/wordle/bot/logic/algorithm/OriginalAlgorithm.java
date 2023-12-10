package wordle.bot.logic.algorithm;

import java.util.List;

public class OriginalAlgorithm implements IAlgorithm{
    List<String> dictionary;

    @Override
    public void setDictionary(List<String> words) {
        this.dictionary = words;    
    }

    @Override
    public List<String> getBestWords(List<String> wordsLeft, int maxWords, boolean useAllWords) {
        return null;
    }
    
}
