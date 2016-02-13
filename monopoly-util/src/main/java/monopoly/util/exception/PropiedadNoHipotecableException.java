package monopoly.util.exception;

/**
 * Se lanza cuando se quiere hipotecar una propiedad que no es hipotecable
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class PropiedadNoHipotecableException extends Exception {

	private static final long serialVersionUID = -4198827718738413273L;

	public PropiedadNoHipotecableException() {
		super();
	}

	public PropiedadNoHipotecableException(String message) {
		super(message);
	}

	public PropiedadNoHipotecableException(Throwable cause) {
		super(cause);
	}

	public PropiedadNoHipotecableException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropiedadNoHipotecableException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
