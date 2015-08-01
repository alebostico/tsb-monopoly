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
	
	private boolean pagada;
	private int monto;
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
	public Deuda(int monto, Jugador jugadorAPagar) {
		super();
		this.pagada = false;
		this.monto = monto;
		this.jugadorAPagar = jugadorAPagar;
	}
	
	public Deuda(int monto) {
		super();
		this.pagada = false;
		this.monto = monto;
		this.jugadorAPagar = null;
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

	public Jugador getJugadorAPagar() {
		return jugadorAPagar;
	}

	public void setJugadorAPagar(Jugador jugadorAPagar) {
		this.jugadorAPagar = jugadorAPagar;
	}
	
	
}
