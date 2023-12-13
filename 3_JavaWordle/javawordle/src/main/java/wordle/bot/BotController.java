package wordle.bot;

import wordle.bot.exceptions.ExceptionAlgorithm;
import wordle.bot.exceptions.ExceptionReader;
import wordle.bot.logic.algorithm.IAlgorithm;
import wordle.bot.logic.wordreader.SelectedLanguage;

public class BotController {
	public static boolean startBot(SelectedLanguage language, IAlgorithm algorithm) {
		IAlgorithmController algController = Factory.getAlgorithmController();
		IWordsController wordsController = Factory.getWordsController();

		try {
			wordsController.loadLanguage(SelectedLanguage.SPANISH);
			algController.setDictionary();
			algController.setAlgorithm(algorithm);
		} catch (ExceptionReader | ExceptionAlgorithm e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
