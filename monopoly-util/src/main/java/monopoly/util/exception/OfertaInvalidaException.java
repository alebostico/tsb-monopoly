/**
 * 
 */
package monopoly.util.exception;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class OfertaInvalidaException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public OfertaInvalidaException(){
		super();
	}
	
	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public OfertaInvalidaException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public OfertaInvalidaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public OfertaInvalidaException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public OfertaInvalidaException(Throwable cause) {
		super(cause);
	}

}
