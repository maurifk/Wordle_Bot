package wordle.bot;

import java.util.List;

import wordle.bot.exceptions.ExceptionReader;
import wordle.bot.logic.wordreader.MatchingsController;
import wordle.bot.logic.wordreader.SelectedLanguage;

public interface IWordsController {
	public MatchingsController getMatchingsController();

	public void loadLanguage(SelectedLanguage language);

	public List<String> getWords() throws ExceptionReader;
}
