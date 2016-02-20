/**
 * 
 */
package monopoly.model.tarjetas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.model.Jugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.CasilleroCalle;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
@Entity
@Table(name = "tarjeta_calle", catalog = "monopoly_db")
@PrimaryKeyJoinColumn(name = "tarjetaPropiedadID")
public class TarjetaCalle extends TarjetaPropiedad implements Serializable {

	private static final long serialVersionUID = 5915150133786803830L;

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
	private String color;

	@Transient
	private EnumColor enumColor;

	public enum EnumColor {
		C_MARRON("MARRON", 2), C_CELESTE("CELESTE", 3), C_FUCSIA("FUCSIA", 3), C_NARANJA(
				"NARANJA", 3), C_ROJO("ROJO", 3), C_AMARILLO("AMARILLO", 3), C_VERDE(
				"VERDE", 3), C_AZUL("AZUL", 2);

		private final String color;
		private final int cantMonopoly;

		EnumColor(String color, int cantMonopoly) {
			this.color = color;
			this.cantMonopoly = cantMonopoly;
		}

		/**
		 * Devuelve el nombre del EnumColor
		 * 
		 * @return el nombre del color
		 */
		public String getColor() {
			return color;
		}

		/**
		 * Devuelve la cantidad de casilleros de ese color que hay en tablero.
		 * 
		 * @return la cantidad de casilleros que conforman el monopolio
		 */
		public int getCantMonopoly() {
			return cantMonopoly;
		}

		/**
		 * Devuelve un String con el nombre del color y la cantidad de calles de
		 * ese color
		 * 
		 * @return El nombre del color y la cantidad de calles en el monopolio
		 */
		public String getColorCant() {
			return color + " (" + String.valueOf(cantMonopoly) + ")";
		}
	}

	private void setEnumColor() {
		for (EnumColor color : EnumColor.values()) {
			if (color.getColor().equals(this.getColor())) {
				this.enumColor = color;
				return;
			}
		}
	}

	/**
	 * Devuelve el {@code EnumColor} asociado a la calle. Si es {@code null}
	 * previamente lo asigna.
	 * 
	 * @return El {@code EnumColor} de la calle.
	 */
	public EnumColor getEnumColor() {
		if (this.enumColor == null)
			this.setEnumColor();
		return this.enumColor;
	}

	/**
	 * Devuelve el {@code EnumColor} correspondiende al {@code color} pasado por
	 * parámetro
	 * 
	 * @param color
	 *            El nombre del color
	 * @return El {@code EnumColor} correspondiende al color
	 */
	public static EnumColor getEnumColor(String color) {
		for (EnumColor colorAux : EnumColor.values()) {
			if (colorAux.getColor().equals(color)) {
				return colorAux;
			}
		}
		return null;
	}

	public TarjetaCalle() {
		super();
	}

	public TarjetaCalle(Jugador jugador, String nombre, int valorHipotecario,
			int precioAlquiler, int valorUnaCasa, int valorDosCasas,
			int valorTresCasas, int valorCuatroCasas, int valorHotel,
			int precioCadaCasa, int precioCadaHotel, String nombreImagen,
			int valorPropiedad, String color, Casillero casillero) {
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
		this.setEnumColor();
	}

	/**
	 * Informa si el jugador puede construir en la propiedad. Verifica que la
	 * propiedad sea una calle, que el jugador dueño de esta propiedad posea
	 * todas las propiedades del mismo color y que la propiedad no esté
	 * hipotecada.
	 * 
	 * @return {@code true} si se puede construir sobre la propiedad
	 */
	public boolean isContruible() {
		if (this.isHipotecada())
			return false;

		if (!this.jugadorTieneMonopolio())
			return false;

		if (this.casasParaCompletar() == 0)
			return false;

		return true;
	}

	/**
	 * Informa si se pueden vender construcciones de la calle o de alguna calle
	 * del mismo color.
	 * 
	 * @return {@code true} si se puede vender construcciones del color
	 */
	public boolean isDescontruible() {
		List<TarjetaCalle> lista = this.getCallesColor();

		for (TarjetaCalle tarjetaCalle : lista) {
			CasilleroCalle casillero = (CasilleroCalle) tarjetaCalle
					.getCasillero();
			if (casillero.getNroCasas() > 0)
				return true;
		}
		return false;
	}

	/**
	 * Informa si una calle se puede vender. Para que una calle se pueda vender,
	 * ninguna de las calles del mismo color deben tener edificios construidos.
	 * 
	 * @return Si la calle se puede vender.
	 */
	public boolean isVendible() {
		return !this.isDescontruible();
	}

	/**
	 * Verifica si el dueño de la propiedad tiene todas las calles del
	 * monopolio, o sea, todas las calles del mismo color que la propiedad
	 * 
	 * @return {@code true} si el jugador posee todas las calles del mismo color
	 */
	private boolean jugadorTieneMonopolio() {
		if (!this.isPropiedadCalle())
			return false;

		int contPropColor = 0;

		TarjetaCalle calle = (TarjetaCalle) this;
		final int cantColor = calle.getEnumColor().getCantMonopoly();

		for (TarjetaPropiedad propiedad : this.getJugador()
				.getTarjPropiedadList()) {
			if (propiedad.isPropiedadCalle())
				if (((TarjetaCalle) propiedad).getColor().equals(
						calle.getColor()))
					if (propiedad.isHipotecada())
						return false;
					else
						contPropColor++;
		}

		if (contPropColor == cantColor)
			return true;
		else
			return false;

	}

	/**
	 * Devuelve la cantidad de edificios construídos:
	 * <ul>
	 * <li>0 - No tiene nada construído</li>
	 * <li>1 a 4 - La cantdidad de casas</li>
	 * <li>5 - Un hotel</li>
	 * </ul>
	 * 
	 * @return La cantidad de casas u hoteles construídos
	 */
	public int getNroCasas() {
		return ((CasilleroCalle) this.getCasillero()).getNroCasas();
	}

	/**
	 * Caclula el precio del alquiler de acuerdo a la cantidad de casas que
	 * tenga edificadas en la calle.
	 * 
	 * @return El monto del alquiler a pagar
	 */
	public int calcularAlquiler() {
		CasilleroCalle casilleroCalle = (CasilleroCalle) this.getCasillero();
		return this.calcularAlquiler(casilleroCalle.getNroCasas());
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
		return (getPrecioCadaCasa() / 2);
	}

	/**
	 * El precio que se paga por vender un hotel del casillero.
	 * 
	 * @return El precio de venta del hotel
	 */
	public int getPrecioVentaHotel() {
		return (getPrecioCadaHotel() / 2);
	}

	/**
	 * @return the colorTarjeta
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param colorTarjeta
	 *            the colorTarjeta to set
	 */
	public void setColor(String color) {
		this.color = color;
		this.setEnumColor();
	}

	/**
	 * Devuelve la cantidad de casas que faltan para completar el total de casas
	 * del color.
	 * 
	 * @return La cantidad de casas permitidas en el color, menos las que ya
	 *         están construidas (total_casas_color - total_casas_construidas).
	 */
	public int casasParaCompletar() {

		List<TarjetaCalle> monopolio = getCallesColor();
		int contCasas = 0;

		for (TarjetaCalle tarjetaCalle : monopolio) {
			contCasas += ((CasilleroCalle) tarjetaCalle.getCasillero())
					.getNroCasas();
		}

		return (getEnumColor().getCantMonopoly() * 5) - contCasas;
	}

	/**
	 * Devuelve una lista con todas las calles del mismo color que posee el
	 * propietario de la propiedad. <strong>NO</strong> controla si el monopolio
	 * está completo.
	 * 
	 * @return Las calles del mismo color
	 */
	protected List<TarjetaCalle> getCallesColor() {
		List<TarjetaCalle> monopolio = new ArrayList<TarjetaCalle>();

		TarjetaCalle calle = (TarjetaCalle) this;

		for (TarjetaPropiedad propiedad : this.getJugador()
				.getTarjPropiedadList()) {
			if (propiedad.isPropiedadCalle()) {
				TarjetaCalle propiedadCalle = (TarjetaCalle) propiedad;
				if (propiedadCalle.getColor().equals(calle.getColor()))
					monopolio.add(propiedadCalle);
			}
		}
		return monopolio;
	}

	// /**
	// *
	// * @param colorTarjeta
	// */
	// public void setColor(Color colorTarjeta) {
	// this.color = colorTarjeta;
	// // this.colorTarjeta = this.color.getColor();
	// }
	//
	// /**
	// * @return the color
	// */
	// public Color getColor() {
	// return color;
	// }

	/**
	 * Calcula el monto del alquiler
	 * 
	 * @param nroCasas
	 *            La cantidad de casas contruidas en la calle
	 * @return El monto del alquiere correspondiente
	 */
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
