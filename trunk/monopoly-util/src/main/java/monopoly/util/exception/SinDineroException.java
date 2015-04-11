package monopoly.util.exception;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SinDineroException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2045593342125870951L;

	public SinDineroException() {
		super();
	}

	public SinDineroException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SinDineroException(String message, Throwable cause) {
		super(message, cause);
	}

	public SinDineroException(String message) {
		super(message);
	}

	public SinDineroException(Throwable cause) {
		super(cause);
	}

}
