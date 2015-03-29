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
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name = "tarjeta_estacion", catalog = "monopoly_db")
@PrimaryKeyJoinColumn(name = "tarjetaPropiedadID")
public class TarjetaEstacion extends TarjetaPropiedad implements Serializable {

	private static final long serialVersionUID = 1459008914691723627L;

	@Column(name = "precioAlquiler")
	private int precioAlquiler;

	@Column(name = "valorDosEstacion")
	private int valorDosEstacion;

	@Column(name = "valorTresEstacion")
	private int valorTresEstacion;

	@Column(name = "valorCuatroEstacion")
	private int valorCuatroEstacion;

	public TarjetaEstacion() {
		super();
	}

	/**
	 * @param jugador
	 * @param nombre
	 * @param valorHipotecario
	 * @param precioAlquiler
	 * @param valorUnaEstacion
	 * @param valorDosEstacion
	 * @param valorTresEstacion
	 * @param valorCuatroEstacion
	 */
	public TarjetaEstacion(Jugador jugador, String nombre,
			int valorHipotecario, int precioAlquiler, int valorDosEstacion,
			int valorTresEstacion, int valorCuatroEstacion,
			String nombreImagen, int valorPropiedad, Casillero casillero) {
		super(jugador, nombre, valorHipotecario, nombreImagen, valorPropiedad,
				casillero);
		this.precioAlquiler = precioAlquiler;
		this.valorDosEstacion = valorDosEstacion;
		this.valorTresEstacion = valorTresEstacion;
		this.valorCuatroEstacion = valorCuatroEstacion;
	}

	/**
	 * @return the precioAlquiler
	 */
	public int getPrecioAlquiler() {
		return precioAlquiler;
	}

	/**
	 * @param precioAlquiler
	 *            the precioAlquiler to set
	 */
	public void setPrecioAlquiler(int precioAlquiler) {
		this.precioAlquiler = precioAlquiler;
	}

	/**
	 * @return the valorDosEstacion
	 */
	public int getValorDosEstacion() {
		return valorDosEstacion;
	}

	/**
	 * @param valorDosEstacion
	 *            the valorDosEstacion to set
	 */
	public void setValorDosEstacion(int valorDosEstacion) {
		this.valorDosEstacion = valorDosEstacion;
	}

	/**
	 * @return the valorTresEstacion
	 */
	public int getValorTresEstacion() {
		return valorTresEstacion;
	}

	/**
	 * @param valorTresEstacion
	 *            the valorTresEstacion to set
	 */
	public void setValorTresEstacion(int valorTresEstacion) {
		this.valorTresEstacion = valorTresEstacion;
	}

	/**
	 * @return the valorCuatroEstacion
	 */
	public int getValorCuatroEstacion() {
		return valorCuatroEstacion;
	}

	/**
	 * @param valorCuatroEstacion
	 *            the valorCuatroEstacion to set
	 */
	public void setValorCuatroEstacion(int valorCuatroEstacion) {
		this.valorCuatroEstacion = valorCuatroEstacion;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		TarjetaEstacion tp = (TarjetaEstacion) object;
		if (super.getIdTarjeta() != tp.getIdTarjeta())
			return false;

		return true;
	}


	/* (non-Javadoc)
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
		return super.toString() + "\n\tTarjetaEstacion [precioAlquiler="
				+ precioAlquiler + ", valorDosEstacion=" + valorDosEstacion
				+ ", valorTresEstacion=" + valorTresEstacion
				+ ", valorCuatroEstacion=" + valorCuatroEstacion + "]";
	}

}
