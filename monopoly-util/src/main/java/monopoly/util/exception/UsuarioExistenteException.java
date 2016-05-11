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
public class UsuarioExistenteException extends Exception implements Serializable{
	
	private static final long serialVersionUID = -5245856939447935917L;

	public UsuarioExistenteException() {
	}

	public UsuarioExistenteException(String message) {
		super(message);
	}

	public UsuarioExistenteException(Throwable cause) {
		super(cause);
	}

	public UsuarioExistenteException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioExistenteException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
