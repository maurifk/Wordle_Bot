package wordle.bot;

import java.util.List;

import wordle.bot.exceptions.ExceptionNoReader;
import wordle.bot.logic.wordreader.SelectedLanguage;
import wordle.bot.logic.wordreader.WordReader;

public class WordsController implements IWordsController {
	private static WordReader wReader = null;

	@Override
	public void loadLanguage(SelectedLanguage language) {
		wReader = new WordReader(language);
	}

	@Override
	public List<String> getWords() throws ExceptionNoReader {
		if (wReader == null)
			throw new ExceptionNoReader("Words were not loaded");
		
		return wReader.getWordsList();
	}
	
	
}
