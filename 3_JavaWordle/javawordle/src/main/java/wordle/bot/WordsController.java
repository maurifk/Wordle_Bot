package wordle.bot;

import java.util.List;

import wordle.bot.exceptions.ExceptionReader;
import wordle.bot.logic.wordreader.SelectedLanguage;
import wordle.bot.logic.wordreader.WordReader;

public class WordsController implements IWordsController {
	private static WordReader wReader = null;

	@Override
	public void loadLanguage(SelectedLanguage language) {
		wReader = new WordReader(language);
	}

	@Override
	public List<String> getWords() throws ExceptionReader {
		if (wReader == null)
			throw new ExceptionReader("Words were not loaded");

		return wReader.getWordsList();
	}


}
