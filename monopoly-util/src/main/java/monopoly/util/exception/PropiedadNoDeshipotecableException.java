package monopoly.util.exception;

/**
 * Se lanza cuando se quiere hipotecar una propiedad que no es hipotecable
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class PropiedadNoDeshipotecableException extends Exception {

	private static final long serialVersionUID = -4198827718738413273L;

	public PropiedadNoDeshipotecableException() {
		super();
	}

	public PropiedadNoDeshipotecableException(String message) {
		super(message);
	}

	public PropiedadNoDeshipotecableException(Throwable cause) {
		super(cause);
	}

	public PropiedadNoDeshipotecableException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropiedadNoDeshipotecableException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
