package wordle.bot;

import java.util.LinkedList;
import java.util.List;

import org.javatuples.Pair;

import wordle.bot.exceptions.ExceptionAlgorithm;
import wordle.bot.exceptions.ExceptionReader;
import wordle.bot.logic.algorithm.IAlgorithm;
import wordle.bot.logic.algorithm.helpers.Base3;
import wordle.bot.logic.algorithm.helpers.Color;

public class AlgorithmController implements IAlgorithmController {
	List<String> dictionary = null;
	List<String> wordsLeft = null;
	IAlgorithm algorithm = null;
	List<Pair<String, Base3>> history = null;
	boolean[] strategy = {true, true, false, false, false, false}; // True means it can play words already filtered out.
	int maxWords = 5; // Default value is 5 words

	@Override
	public void setDictionary() throws ExceptionReader {
		this.dictionary = Factory.getWordsController().getWords();
		this.wordsLeft = this.dictionary;
	}

	@Override
	public void setAlgorithm(IAlgorithm algorithmI) throws ExceptionReader, ExceptionAlgorithm {
		if (this.dictionary == null) {
			setDictionary();
		}

		if (algorithmI == null) {
			throw new ExceptionAlgorithm("Algorithm is null");
		}

		history = new LinkedList<Pair<String, Base3>>();

		algorithm.setDictionary(this.dictionary);
		this.algorithm = algorithmI;
	}

	@Override
	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}

	@Override
	public void setStrategy(boolean[] strategy) {

		this.strategy = strategy;
	}

	private boolean isPossible(String playedWord, Base3 pattern, String possibleWord) {
		StringBuilder sb = new StringBuilder(possibleWord);
		for (int i = 0; i<5; ++i) {
			if ((pattern.getColor(i) == Color.AMARILLO || pattern.getColor(i) == Color.GRIS) && playedWord.charAt(i) != sb.charAt(i)) {
				return false;
			}
			if (pattern.getColor(i) == Color.VERDE) {
				if (playedWord.charAt(i) != sb.charAt(i)) {
					return false;
				} else {
					sb.setCharAt(i, ' ');
				}
			}
		}

		boolean marque;
		for (int i = 0; i<5; ++i) {
			if (pattern.getColor(i) == Color.AMARILLO) {
				marque = false;
				for (int j = 0; j<5; ++j) {
					if (playedWord.charAt(i) == sb.charAt(j)) {
						sb.setCharAt(j, ' ');
						marque = true;
						break;
					}
				}
				if (!marque) {
					return false;
				}
			}
		}

		for (int i = 0; i<5; ++i) {
			if (pattern.getColor(i) == Color.VERDE) {
				for (int j = 0; j<5; ++j) {
					if (playedWord.charAt(i) == sb.charAt(j)) {
						return false;
					}
				}
			}
		}

		return true;
	}


	private List<String> filterWordsLeft(String playedWord, Base3 pattern) {
		wordsLeft = wordsLeft.parallelStream()
					.filter(x -> isPossible(playedWord, pattern, x))
					.toList();

		return wordsLeft;
	}

	private int minInt (int n, int m) {
		return n < m ? n : m;
	}

	@Override
	public List<String> playWord(String word, Base3 pattern) throws ExceptionAlgorithm {
		if (algorithm == null) {
			throw new ExceptionAlgorithm("Algorithm is null");
		}

		List<String> bestWords = algorithm.getBestWords(filterWordsLeft(word, pattern), maxWords, strategy[minInt(history.size(), strategy.length - 1)]);
		history.add(new Pair<String, Base3>(word, pattern));

		return bestWords;
	}

	@Override
	public List<Pair<String, Base3>> getHistory() {
		return history;
	}

	@Override
	public void reset() {
		history = new LinkedList<Pair<String, Base3>>();
		wordsLeft = dictionary;
		algorithm = null;
		maxWords = 5;
		strategy = new boolean[] {true, true, false, false, false, false};
	}

}
