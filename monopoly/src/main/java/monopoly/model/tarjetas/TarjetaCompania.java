/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import monopoly.model.Jugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tablero.CasilleroCompania;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
@Entity
@Table(name = "tarjeta_compania", catalog = "monopoly_db")
@PrimaryKeyJoinColumn(name = "tarjetaPropiedadID")
public class TarjetaCompania extends TarjetaPropiedad implements Serializable {

	private static final long serialVersionUID = -8926483320692881884L;

	@Column(name = "vecesPorUnaCarta")
	private int vecesPorUnaCarta;

	@Column(name = "vecesPorDosCartas")
	private int vecesPorDosCartas;

	/**
     * 
     */
	public TarjetaCompania() {
		super();
	}

	public TarjetaCompania(Jugador jugador, String nombre,
			int valorHipotecario, int vecesPorUnaCarta, int vecesPorDosCartas,
			String nombreImagen, int valorPropiedad, Casillero casillero) {
		super(jugador, nombre, valorHipotecario, nombreImagen, valorPropiedad,
				casillero);
		this.vecesPorUnaCarta = vecesPorUnaCarta;
		this.vecesPorDosCartas = vecesPorDosCartas;
	}

	/**
	 * @return the vecesPorUnaCarta
	 */
	public int getVecesPorUnaCarta() {
		return vecesPorUnaCarta;
	}

	/**
	 * @param vecesPorUnaCarta
	 *            the vecesPorUnaCarta to set
	 */
	public void setVecesPorUnaCarta(int vecesPorUnaCarta) {
		this.vecesPorUnaCarta = vecesPorUnaCarta;
	}

	/**
	 * @return the vecesPorDosCartas
	 */
	public int getVecesPorDosCartas() {
		return vecesPorDosCartas;
	}

	/**
	 * @param vecesPorDosCartas
	 *            the vecesPorDosCartas to set
	 */
	public void setVecesPorDosCartas(int vecesPorDosCartas) {
		this.vecesPorDosCartas = vecesPorDosCartas;
	}

	/**
	 * Método que calcula el valor del alquiler.
	 * 
	 * @param resultadoDados
	 *            El resultado de la última tirada de dados
	 * @return El monto a pagar por el alquiler
	 */
	public int calcularAlquiler(int resultadoDados) {
		int cantCompanias = 0;
		for (TarjetaPropiedad tarjProp : this.getJugador()
				.getTarjPropiedadList()) {
			if (tarjProp.getCasillero().getTipoCasillero() == TipoCasillero.C_COMPANIA) {
				CasilleroCompania casCom = (CasilleroCompania) tarjProp
						.getCasillero();
				if (casCom.getTarjetaCompania().getJugador()
						.equals(this.getJugador())) {
					cantCompanias++;
				}
			}
		}
		return this.calcularAlquiler(cantCompanias, resultadoDados);
	}

	/**
	 * Método que calcula el valor del alquiler.
	 * 
	 * @param nroCompania
	 *            La cantidad de compañías que posee el jugador que cobra
	 * @param resultadoDados
	 *            El resultado de la última tirada de dados
	 * @return El monto a pagar por el alquiler
	 */
	public int calcularAlquiler(int nroCompania, int resultadosDados) {
		int monto = 0;
		switch (nroCompania) {
		case 0:
			monto = this.vecesPorUnaCarta * resultadosDados;
			break;
		case 1:
			monto = this.vecesPorUnaCarta * resultadosDados;
			break;
		case 2:
			monto = this.vecesPorDosCartas * resultadosDados;
			break;
		default:
			monto = 0;
			break;
		}
		return monto;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		TarjetaCompania tp = (TarjetaCompania) object;
		if (super.getIdTarjeta() != tp.getIdTarjeta())
			return false;

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.getIdTarjeta();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\n\tTarjetaCompania [vecesPorUnaCarta="
				+ vecesPorUnaCarta + ", vecesPorDosCartas=" + vecesPorDosCartas
				+ "]";
	}

}
