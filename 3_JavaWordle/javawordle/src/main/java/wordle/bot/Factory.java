package wordle.bot;

public class Factory {
	private static WordsController instanceWC = null;
	private static AlgorithmController instanceAlg = null;
	
	public static IWordsController getWordsController() {
		if (instanceWC == null) 
			instanceWC = new WordsController();
		
		return instanceWC;
	}
	
	public static IAlgorithmController getAlgorithmController() {
		if (instanceAlg == null)
			instanceAlg = new AlgorithmController();
		
		return instanceAlg;
	}
}
