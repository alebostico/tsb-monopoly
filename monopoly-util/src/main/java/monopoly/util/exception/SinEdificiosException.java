package monopoly.util.exception;

/**
 * Se lanza cuando el banco no dispone de las casas u hoteles necesarios para
 * una compra.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SinEdificiosException extends Exception {

	private static final long serialVersionUID = 7350028050889641413L;

	public SinEdificiosException() {
		super();
	}

	public SinEdificiosException(String message) {
		super(message);
	}

	public SinEdificiosException(Throwable cause) {
		super(cause);
	}

	public SinEdificiosException(String message, Throwable cause) {
		super(message, cause);
	}

	public SinEdificiosException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
