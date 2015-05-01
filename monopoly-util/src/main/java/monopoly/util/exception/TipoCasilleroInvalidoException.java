package monopoly.util.exception;

public class TipoCasilleroInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8185976924760529502L;

	public TipoCasilleroInvalidoException() {
	}

	public TipoCasilleroInvalidoException(String message) {
		super(message);
	}

	public TipoCasilleroInvalidoException(Throwable cause) {
		super(cause);
	}

	public TipoCasilleroInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public TipoCasilleroInvalidoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
