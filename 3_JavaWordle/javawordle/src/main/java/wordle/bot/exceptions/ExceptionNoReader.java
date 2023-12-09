package wordle.bot.exceptions;

public class ExceptionNoReader extends Exception {

	private static final long serialVersionUID = -7081384586412228596L;
	
	public ExceptionNoReader(String error) {
		super(error);
	}

}
