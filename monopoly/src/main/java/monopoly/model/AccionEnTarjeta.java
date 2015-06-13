/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 *         Enumerador para determinar el tipo de acción a realizar en base al
 *         objetivo de la tarjeta.
 *
 */
public enum AccionEnTarjeta implements Serializable {

	COBRAR(0), PAGAR(0), COBRAR_TODOS(0), MOVER(0, false), IR_A_CARCEL(), PAGAR_POR_CASA_HOTEL(
			0, 0);

	private String mensaje;

	private int monto;

	private int nroCasilleros;

	private boolean cobraSalida;

	private int precioPorCasa;

	private int precioPorHotel;

	private AccionEnTarjeta() {
	}

	private AccionEnTarjeta(int monto) {
		this.monto = monto;
	}

	private AccionEnTarjeta(int nroCasilleros, boolean cobraSalida) {
		this.nroCasilleros = nroCasilleros;
		this.cobraSalida = cobraSalida;
	}

	private AccionEnTarjeta(int precioPorCasa, int precioPorHotel) {
		this.precioPorCasa = precioPorCasa;
		this.precioPorHotel = precioPorHotel;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getMonto() {
		return monto;
	}

	public void setMonto(int monto) {
		this.monto = monto;
	}

	public int getNroCasilleros() {
		return nroCasilleros;
	}

	public void setNroCasilleros(int nroCasilleros) {
		this.nroCasilleros = nroCasilleros;
	}

	public boolean isCobraSalida() {
		return cobraSalida;
	}

	public void setCobraSalida(boolean cobraSalida) {
		this.cobraSalida = cobraSalida;
	}

	public int getPrecioPorCasa() {
		return precioPorCasa;
	}

	public void setPrecioPorCasa(int precioPorCasa) {
		this.precioPorCasa = precioPorCasa;
	}

	public int getPrecioPorHotel() {
		return precioPorHotel;
	}

	public void setPrecioPorHotel(int precioPorHotel) {
		this.precioPorHotel = precioPorHotel;
	}

}
