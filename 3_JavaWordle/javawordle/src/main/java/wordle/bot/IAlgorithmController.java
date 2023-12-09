package wordle.bot;

import java.util.List;

import org.javatuples.Pair;

import wordle.bot.exceptions.ExceptionAlgorithm;
import wordle.bot.exceptions.ExceptionReader;
import wordle.bot.logic.algorithm.IAlgorithm;
import wordle.bot.logic.algorithm.helpers.Base3;

public interface IAlgorithmController {
	public void setDictionary() throws ExceptionReader;

	public void setAlgorithm(IAlgorithm algorithm) throws ExceptionReader, ExceptionAlgorithm;

	public void setMaxWords(int maxWords);

	public void setStrategy(boolean[] strategy);

	public List<String> playWord(String word, Base3 pattern) throws ExceptionAlgorithm;

	public List<Pair<String, Base3>> getHistory();

	public void reset();

}
