package wordle.bot;

public class Factory {
	private static WordsController instance = null;
	
	public static IWordsController getWordsController() {
		if (instance == null) 
			instance = new WordsController();
		
		return instance;
	}
}
