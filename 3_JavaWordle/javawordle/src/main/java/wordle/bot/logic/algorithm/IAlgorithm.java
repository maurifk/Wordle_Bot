package wordle.bot.logic.algorithm;

import java.util.List;

public interface IAlgorithm {
	public void setDictionary (List<String> words);

	public List<String> getBestWords(List<String> wordsLeft, int maxWords, boolean useAllWords);

	public void startAlgorithm();
}
