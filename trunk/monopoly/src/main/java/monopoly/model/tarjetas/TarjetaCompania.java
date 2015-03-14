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

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
@Entity
@Table(name = "tarjeta_compania", catalog = "monopoly_db")
@PrimaryKeyJoinColumn(name = "tarjetaPropiedadID")
public class TarjetaCompania extends TarjetaPropiedad implements Serializable {

	private static final long serialVersionUID = 1L;

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

	/**
	 * @param jugador
	 * @param nombre
	 * @param valorHipotecario
	 * @param vecesPorUnaCarta
	 * @param vecesPorDosCartas
	 */
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\n\tTarjetaCompania [vecesPorUnaCarta="
				+ vecesPorUnaCarta + ", vecesPorDosCartas=" + vecesPorDosCartas
				+ "]";
	}

}
