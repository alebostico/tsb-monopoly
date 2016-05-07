package monopoly.util.exception;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SinDineroException extends Exception implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2045593342125870951L;
	
	public final int monto;
	
	public Object accion;

	public SinDineroException() {
		super();
		this.monto= 0;
		this.accion = null;
	}
	
	public SinDineroException(int monto) {
		super();
		this.monto= monto;
		this.accion = null;
	}
	
	public SinDineroException(int monto, Object accion) {
		super();
		this.monto= monto;
		this.accion = accion;
	}

	public SinDineroException(String message) {
		super(message);
		this.monto= 0;
		this.accion = null;
	}

	public SinDineroException(String message, int monto) {
		super(message);
		this.monto= monto;
		this.accion = null;
	}

	public SinDineroException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.monto= 0;
		this.accion = null;
	}

	public SinDineroException(String message, Throwable cause) {
		super(message, cause);
		this.monto= 0;
		this.accion = null;
	}

	public SinDineroException(Throwable cause) {
		super(cause);
		this.monto= 0;
		this.accion = null;
	}

	public Object getAccion() {
		return accion;
	}

	public void setAccion(Object accion) {
		this.accion = accion;
	}

}
