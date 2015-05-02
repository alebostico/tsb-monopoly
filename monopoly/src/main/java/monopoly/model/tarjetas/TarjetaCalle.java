/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.model.Jugador;
import monopoly.model.tablero.Casillero;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
@Entity
@Table(name = "tarjeta_calle", catalog = "monopoly_db")
// @AttributeOverrides({
// @AttributeOverride(name="jugador", column=@Column(name="jugadorID")),
// @AttributeOverride(name="nombre", column=@Column(name="nombre")),
// @AttributeOverride(name="valorHipoticario",
// column=@Column(name="valorHipoticario"))
// @AttributeOverride(name="valorPropiedad",
// column=@Column(name="valorPropiedad"))
// })
@PrimaryKeyJoinColumn(name = "tarjetaPropiedadID")
public class TarjetaCalle extends TarjetaPropiedad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "precioAlquiler")
	private int precioAlquiler;

	@Column(name = "valorUnaCasa")
	private int valorUnaCasa;

	@Column(name = "valorDosCasas")
	private int valorDosCasas;

	@Column(name = "valorTresCasas")
	private int valorTresCasas;

	@Column(name = "valorCuatroCasas")
	private int valorCuatroCasas;

	@Column(name = "valorHotel")
	private int valorHotel;

	@Column(name = "precioCadaCasa")
	private int precioCadaCasa;

	@Column(name = "precioCadaHotel")
	private int precioCadaHotel;

	@Column(name = "color")
	private String colorTarjeta;

	@Transient
	private Color color;

	public enum Color {
		C_MARRON("marron"), C_CELESTE("celeste"), C_FUCSIA("fucsia"), C_NARANJA(
				"naranja"), C_ROJO("rojo"), C_AMARILLO("amarillo"), C_VERDE(
				"verde"), C_AZUL("azul");

		private final String color;

		Color(String color) {
			this.color = color;
		}

		public String getColor() {
			return color;
		}

	}

	public TarjetaCalle() {
		super();
	}

	public TarjetaCalle(Jugador jugador, String nombre, int valorHipotecario,
			int precioAlquiler, int valorUnaCasa, int valorDosCasas,
			int valorTresCasas, int valorCuatroCasas, int valorHotel,
			int precioCadaCasa, int precioCadaHotel, String nombreImagen,
			int valorPropiedad, Color color, Casillero casillero) {
		super(jugador, nombre, valorHipotecario, nombreImagen, valorPropiedad,
				casillero);
		this.precioAlquiler = precioAlquiler;
		this.valorUnaCasa = valorUnaCasa;
		this.valorDosCasas = valorDosCasas;
		this.valorTresCasas = valorTresCasas;
		this.valorCuatroCasas = valorCuatroCasas;
		this.valorHotel = valorHotel;
		this.precioCadaCasa = precioCadaCasa;
		this.precioCadaHotel = precioCadaHotel;
		this.color = color;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero no
	 * est&aacute; edificado.
	 * 
	 * @return the El precio del alquiler
	 */
	public int getPrecioAlquiler() {
		return precioAlquiler;
	}

	/**
	 * Setea el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero no
	 * est&aacute; edificado.
	 * 
	 * @param precioAlquiler
	 *            El precio del alquiler
	 */
	public void setPrecioAlquiler(int precioAlquiler) {
		this.precioAlquiler = precioAlquiler;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>una casa</strong> edificada.
	 * 
	 * @return El valor de alquiler con una casa edificada
	 */
	public int getValorUnaCasa() {
		return valorUnaCasa;
	}

	/**
	 * Setea el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>una casa</strong> edificada.
	 * 
	 * @param valorUnaCasa
	 *            El valor de alquiler con una casa edificada.
	 */
	public void setValorUnaCasa(int valorUnaCasa) {
		this.valorUnaCasa = valorUnaCasa;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>dos casas</strong> edificadas.
	 * 
	 * @return El valor de alquiler con dos casas edificadas
	 */
	public int getValorDosCasas() {
		return valorDosCasas;
	}

	/**
	 * Setea el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>dos casas</strong> edificadas.
	 * 
	 * @param valorDosCasas
	 *            El valor de alquiler con dos casas edificadas
	 */
	public void setValorDosCasas(int valorDosCasas) {
		this.valorDosCasas = valorDosCasas;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>tres casas</strong> edificadas.
	 * 
	 * @return El valor de alquiler con tres casas edificadas
	 */
	public int getValorTresCasas() {
		return valorTresCasas;
	}

	/**
	 * Setea el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>tres casas</strong> edificada.
	 * 
	 * @param valorTresCasas
	 *            El valor de alquiler con tres casas edificadas
	 */
	public void setValorTresCasas(int valorTresCasas) {
		this.valorTresCasas = valorTresCasas;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>cuatro casas</strong> edificadas.
	 * 
	 * @return El valor de alquiler con cuatro casas edificadas
	 */
	public int getValorCuatroCasas() {
		return valorCuatroCasas;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>cuatro casas</strong> edificadas.
	 * 
	 * @param valorCuatroCasas
	 *            El valor de alquiler con cuatro casas edificadas
	 */
	public void setValorCuatroCasas(int valorCuatroCasas) {
		this.valorCuatroCasas = valorCuatroCasas;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>un hotel</strong> edificado.
	 * 
	 * @return El valor de alquiler con un hotel edificado
	 */
	public int getValorHotel() {
		return valorHotel;
	}

	/**
	 * Devuelve el precio que paga un jugador cuando cae en un casillero de otro
	 * jugador que posee todas las propiedades del mismo color y el casillero
	 * tiene <strong>un hotel</strong> edificado.
	 * 
	 * @param valorHotel
	 *            El valor de alquiler con un hotel edificado
	 */
	public void setValorHotel(int valorHotel) {
		this.valorHotel = valorHotel;
	}

	/**
	 * El precio que se paga por cada casa que se edifica en el casillero.
	 * 
	 * @return El precio de construir una casa
	 */
	public int getPrecioCadaCasa() {
		return precioCadaCasa;
	}

	/**
	 * El precio que se paga por cada casa que se edifica en el casillero.
	 * 
	 * @param precioCadaCasa
	 *            El precio de construir una casa
	 */
	public void setPrecioCadaCasa(int precioCadaCasa) {
		this.precioCadaCasa = precioCadaCasa;
	}

	/**
	 * El precio que se paga por un hotel que se edifica en el casillero.
	 * 
	 * @return El precio de construir una hotel
	 */
	public int getPrecioCadaHotel() {
		return precioCadaHotel;
	}

	/**
	 * El precio que se paga por un hotel que se edifica en el casillero.
	 * 
	 * @param precioCadaHotel
	 *            El precio de construir una hotel
	 */
	public void setPrecioCadaHotel(int precioCadaHotel) {
		this.precioCadaHotel = precioCadaHotel;
	}

	/**
	 * El precio que se paga por vender una casa del casillero.
	 * 
	 * @return El precio de venta de la casa
	 */
	public int getPrecioVentaCadaCasa() {
		return (int) ((double) getPrecioCadaCasa() / 2.0);
	}

	/**
	 * El precio que se paga por vender un hotel del casillero.
	 * 
	 * @return El precio de venta del hotel
	 */
	public int getPrecioVentaHotel() {
		return (int) ((double) getPrecioCadaHotel() / 2.0);
	}

	/**
	 * @return the colorTarjeta
	 */
	public String getColorTarjeta() {
		return colorTarjeta;
	}

	/**
	 * @param colorTarjeta
	 *            the colorTarjeta to set
	 */
	public void setColorTarjeta(String colorTarjeta) {
		this.colorTarjeta = colorTarjeta;
	}

	/**
	 * 
	 * @param colorTarjeta
	 */
	public void setColor(Color colorTarjeta) {
		this.color = colorTarjeta;
		// this.colorTarjeta = this.color.getColor();
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	public int calcularAlquiler(int nroCasas) {
		int monto = 0;
		switch (nroCasas) {
		case 0:
			monto = this.precioAlquiler;
			break;
		case 1:
			monto = this.valorUnaCasa;
			break;
		case 2:
			monto = this.valorDosCasas;
			break;
		case 3:
			monto = this.valorTresCasas;
			break;
		case 4:
			monto = this.valorCuatroCasas;
			break;
		case 5:
			monto = this.valorHotel;
			break;
		default:
			monto = 0;
			break;
		}
		return monto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "\n\tTarjetaCalle [precioAlquiler="
				+ precioAlquiler + ", valorUnaCasa=" + valorUnaCasa
				+ ", valorDosCasas=" + valorDosCasas + ", valorTresCasas="
				+ valorTresCasas + ", valorCuatroCasas=" + valorCuatroCasas
				+ ", valorHotel=" + valorHotel + ", precioCadaCasa="
				+ precioCadaCasa + ", precioCadaHotel=" + precioCadaHotel + "]";
	}

	@Override
	public boolean equals(Object object) {

		if (object == this)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;

		TarjetaCalle tp = (TarjetaCalle) object;
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

}
