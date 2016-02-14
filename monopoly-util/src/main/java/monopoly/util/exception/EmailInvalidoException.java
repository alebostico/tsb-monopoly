/**
 * 
 */
package monopoly.util.exception;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class EmailInvalidoException extends Exception {

	private static final long serialVersionUID = 4883262323302464365L;

	public EmailInvalidoException() {
		super();
	}

	public EmailInvalidoException(String message) {
		super(message);
	}

	public EmailInvalidoException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public EmailInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailInvalidoException(Throwable cause) {
		super(cause);
	}

}
