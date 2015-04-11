/**
 * 
 */
package monopoly.util.exception;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 */
public class CondicionInvalidaException extends Exception {

	/**
	 * 
	 */
	public CondicionInvalidaException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public CondicionInvalidaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CondicionInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public CondicionInvalidaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CondicionInvalidaException(Throwable cause) {
		super(cause);
	}

}
