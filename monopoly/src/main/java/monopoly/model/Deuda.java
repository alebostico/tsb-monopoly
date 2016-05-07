/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class Deuda implements Serializable {
	
	private static final long serialVersionUID = -2502135553679354179L;
	
	private AccionEnCasillero.Accion accion;
	private boolean pagada;
	private int monto;
	private int nroCasillero;
	private String mensajeAux;
	private Jugador jugadorAPagar;
	
	public Deuda()
	{
		pagada = false;
	}

	/**
	 * @param pagada
	 * @param monto
	 * @param jugadorAPagar
	 */
	public Deuda(AccionEnCasillero.Accion accion, int monto, int nroCasillero, Jugador jugadorAPagar) {
		super();
		this.accion = accion;
		this.pagada = false;
		this.monto = monto;
		this.nroCasillero = nroCasillero;
		this.jugadorAPagar = jugadorAPagar;
	}
	
	public Deuda(AccionEnCasillero.Accion accion, int monto) {
		super();
		this.accion = accion;
		this.pagada = false;
		this.monto = monto;
		this.jugadorAPagar = null;
		this.nroCasillero = 0;
	}

	public AccionEnCasillero.Accion getAccion() {
		return accion;
	}

	public void setAccion(AccionEnCasillero.Accion accion) {
		this.accion = accion;
	}

	public boolean isPagada() {
		return pagada;
	}

	public void setPagada(boolean pagada) {
		this.pagada = pagada;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public int getNroCasillero() {
		return nroCasillero;
	}

	public void setNroCasillero(int nroCasillero) {
		this.nroCasillero = nroCasillero;
	}

	public String getMensajeAux() {
		return mensajeAux;
	}

	public void setMensajeAux(String mensajeAux) {
		this.mensajeAux = mensajeAux;
	}

	public Jugador getJugadorAPagar() {
		return jugadorAPagar;
	}

	public void setJugadorAPagar(Jugador jugadorAPagar) {
		this.jugadorAPagar = jugadorAPagar;
	}
	
}
