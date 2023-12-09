package wordle.bot;

import java.util.List;

import wordle.bot.exceptions.ExceptionNoReader;
import wordle.bot.logic.SelectedLanguage;

public interface IWordsController {
	public void loadLanguage(SelectedLanguage language);
	
	public List<String> getWords() throws ExceptionNoReader;
}
