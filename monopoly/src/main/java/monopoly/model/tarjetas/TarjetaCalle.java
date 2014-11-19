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

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
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

	/**
	 * @param precioAlquiler
	 * @param valorUnaCasa
	 * @param valorDosCasas
	 * @param valorTresCasas
	 * @param valorCuatroCasas
	 * @param valorHotel
	 * @param precioCadaCasa
	 * @param precioCadaHotel
	 */
	public TarjetaCalle(Jugador jugador, String nombre,
			int valorHipotecario, int precioAlquiler,
			int valorUnaCasa, int valorDosCasas,
			int valorTresCasas, int valorCuatroCasas,
			int valorHotel, int precioCadaCasa,
			int precioCadaHotel, String nombreImagen,
			int valorPropiedad, Color color) {
		super(jugador, nombre, valorHipotecario, nombreImagen, valorPropiedad);
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
	 * @return the valorUnaCasa
	 */
	public int getValorUnaCasa() {
		return valorUnaCasa;
	}

	/**
	 * @param valorUnaCasa
	 *            the valorUnaCasa to set
	 */
	public void setValorUnaCasa(int valorUnaCasa) {
		this.valorUnaCasa = valorUnaCasa;
	}

	/**
	 * @return the valorDosCasas
	 */
	public int getValorDosCasas() {
		return valorDosCasas;
	}

	/**
	 * @param valorDosCasas
	 *            the valorDosCasas to set
	 */
	public void setValorDosCasas(int valorDosCasas) {
		this.valorDosCasas = valorDosCasas;
	}

	/**
	 * @return the valorTresCasas
	 */
	public int getValorTresCasas() {
		return valorTresCasas;
	}

	/**
	 * @param valorTresCasas
	 *            the valorTresCasas to set
	 */
	public void setValorTresCasas(int valorTresCasas) {
		this.valorTresCasas = valorTresCasas;
	}

	/**
	 * @return the valorCuatroCasas
	 */
	public int getValorCuatroCasas() {
		return valorCuatroCasas;
	}

	/**
	 * @param valorCuatroCasas
	 *            the valorCuatroCasas to set
	 */
	public void setValorCuatroCasas(int valorCuatroCasas) {
		this.valorCuatroCasas = valorCuatroCasas;
	}

	/**
	 * @return the valorHotel
	 */
	public int getValorHotel() {
		return valorHotel;
	}

	/**
	 * @param valorHotel
	 *            the valorHotel to set
	 */
	public void setValorHotel(int valorHotel) {
		this.valorHotel = valorHotel;
	}

	/**
	 * @return the precioCadaCasa
	 */
	public int getPrecioCadaCasa() {
		return precioCadaCasa;
	}

	/**
	 * @param precioCadaCasa
	 *            the precioCadaCasa to set
	 */
	public void setPrecioCadaCasa(int precioCadaCasa) {
		this.precioCadaCasa = precioCadaCasa;
	}

	/**
	 * @return the precioCadaHotel
	 */
	public int getPrecioCadaHotel() {
		return precioCadaHotel;
	}

	/**
	 * @param precioCadaHotel
	 *            the precioCadaHotel to set
	 */
	public void setPrecioCadaHotel(int precioCadaHotel) {
		this.precioCadaHotel = precioCadaHotel;
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

}
