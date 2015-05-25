/**
 * 
 */
package monopoly.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.model.Estado.EstadoJugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.exception.SinDineroException;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
@Entity
@Table(name = "jugador", catalog = "monopoly_db")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Jugador implements Serializable {

	private static final long serialVersionUID = -3652571253408007334L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "jugadorID")
	private int idJugador;

	@Transient
	private String nombre;

	@Transient
	private Ficha ficha;

	@Transient
	private Casillero casilleroActual;

	@Transient
	private List<TarjetaPropiedad> tarjPropiedadList;

	@Transient
	private Juego juego;

	@Transient
	private int dinero;

	@Transient
	private Dado tiradaInicial;

	@Transient
	private List<Tarjeta> tarjetaCarcelList;

	@Transient
	private int nroCasas;

	@Transient
	private int nroHoteles;

	@Transient
	private int contTurnosCarcel;

	@Transient
	private EstadoJugador estadoJugador;

	/**
	 * Constructor por defecto. inicializa el arraylist tarjetaCarcelList, que
	 * será utilizado para almacenar las tarjetas de comunidad y suerte que
	 * permiten salir de la cárcel.
	 */
	public Jugador(String nombre, Ficha ficha, Juego juego, Casillero casilleroActual) {
		this.nombre = nombre;
		this.ficha = ficha;
		this.juego = juego;
		this.dinero = 1500;
		this.nroCasas = 0;
		this.nroHoteles = 0;
		this.casilleroActual = casilleroActual;
		tarjetaCarcelList = new ArrayList<>();
		tarjPropiedadList = new ArrayList<>();
	}

	/**
	 * @return the ficha
	 */
	public Ficha getFicha() {
		return ficha;
	}

	/**
	 * @param ficha
	 *            the ficha to set
	 */
	public void setFicha(Ficha ficha) {
		this.ficha = ficha;
	}

	/**
	 * @return the casilleroActual
	 */
	public Casillero getCasilleroActual() {
		return casilleroActual;
	}

	/**
	 * @param casilleroActual
	 *            the casilleroActual to set
	 */
	public void setCasilleroActual(Casillero casilleroActual) {
		this.casilleroActual = casilleroActual;
	}

	/**
	 * @return the tarjPropiedadList
	 */
	public List<TarjetaPropiedad> getTarjPropiedadList() {
		return tarjPropiedadList;
	}

	/**
	 * @param tarjPropiedadList
	 *            the tarjPropiedadList to set
	 */
	public void setTarjPropiedadList(List<TarjetaPropiedad> tarjPropiedadList) {
		this.tarjPropiedadList = tarjPropiedadList;
	}

	/**
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}

	/**
	 * @param juego
	 *            the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public int getDinero() {
		return dinero;
	}

	public void setDinero(int dinero) {
		this.dinero = dinero;
	}

	/**
	 * @return the idJugador
	 */
	public Integer getIdJugador() {
		return idJugador;
	}

	/**
	 * @param idJugador
	 *            the idJugador to set
	 */
	public void setIdJugador(Integer idJugador) {
		this.idJugador = idJugador;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the tarjetaCarcelList
	 */
	public List<Tarjeta> getTarjetaCarcelList() {
		return tarjetaCarcelList;
	}

	/**
	 * @param tarjetaCarcelList
	 *            the tarjetaCarcelList to set
	 */
	public void setTarjetaCarcelList(List<Tarjeta> tarjetaCarcelList) {
		this.tarjetaCarcelList = tarjetaCarcelList;
	}

	/**
	 * @return the estadoJugador
	 */
	public EstadoJugador getEstadoJugador() {
		return estadoJugador;
	}

	/**
	 * @param estadoJugador
	 *            the estadoJugador to set
	 */
	public void setEstadoJugador(EstadoJugador estadoJugador) {
		this.estadoJugador = estadoJugador;
	}

	/**
	 * Devuelve la cantidad de turnos que el jugador estuvo en la carcel.
	 * 
	 * @return Devuelve la cantidad de turnos que el jugador estuvo en la
	 *         carcel.
	 */
	public int getContTurnosCarcel() {
		return contTurnosCarcel;
	}

	/**
	 * Suma uno al contador de turnos que el jugador está en la carcel.
	 * 
	 * @return Devuelve la cantidad de turnos que el jugador estuvo en la
	 *         carcel.
	 */
	public int addTurnoCarcel() {
		// TODO: verificar que no sea mas de tres y tirar una excepcion
		this.contTurnosCarcel++;
		return this.contTurnosCarcel;
	}

	/**
	 * Pone en cero el contador de turnos en la carcel del jugador.
	 * 
	 * @return Devuelve la cantidad de turnos que el jugador estuvo en la
	 *         carcel.
	 */
	public int resetTurnoCarcel() {
		int tmpTurnos = this.contTurnosCarcel;
		this.contTurnosCarcel = 0;
		return tmpTurnos;
	}

	/**
	 * Agrega una propiedad a la lista de propiedades del jugador y asigna el
	 * jugador a la propiedad
	 * 
	 * @param tarjeta
	 *            La propiedad que compra
	 * @return true si se agrego correctamente
	 */
	public boolean adquirirPropiedad(TarjetaPropiedad tarjeta) {
		tarjeta.setJugador(this);
		return this.tarjPropiedadList.add(tarjeta);
	}

	/**
	 * Le vende una propiedad a otro jugador.
	 * 
	 * @param tarjeta
	 *            La tarjeta de la propiedad que se vende
	 * @param jugador
	 *            El jugamontodor al cual se le vende la propiedad
	 * @return true si puede vender
	 * @throws SinDineroException
	 */
	public boolean venderPropiedad(TarjetaPropiedad tarjeta, Jugador jugador,
			int monto) throws SinDineroException {
		jugador.pagarAJugador(this, monto);
		this.getTarjPropiedadList().remove(tarjeta);
		return jugador.adquirirPropiedad(tarjeta);

	}

	/**
	 * devuelve true si el jugador puede pagar el monto indicado
	 * 
	 * @param monto
	 *            el monto que se quiere consultar
	 * @return true si el jugador puede pagar ese monto
	 */
	public boolean puedePagar(int monto) {
		return (this.getDinero() >= monto);
	}

	/**
	 * resta del dinero del jugador el monto indicado
	 * 
	 * @param monto
	 *            el monto a pagar por el jugador
	 */
	public boolean pagar(int monto) {
		if (puedePagar(monto)) {
			this.setDinero(this.getDinero() - monto);
		} else {
			return false;
		}
		return true;
	}

	/**
	 * suma al dinero del jugador el monto indicado
	 * 
	 * @param monto
	 *            el monto a cobrar por el jugador
	 */
	public void cobrar(int monto) {
		this.setDinero(this.getDinero() + monto);
	}

	/**
	 * Paga un monto de dinero a un jugador
	 * 
	 * @param jugador
	 * @param monto
	 * @throws SinDineroException
	 */
	public void pagarAJugador(Jugador jugador, int monto)
			throws SinDineroException {
		if (this.pagar(monto))
			jugador.cobrar(monto);
		else
			throw new SinDineroException(
					String.format(
							"El jugador %s no posee dinero suficiente para pagar %s € al jugador %s",
							this.getNombre(), monto, jugador.getNombre()));
	}

	public int getNroCasas() {
		return nroCasas;
	}

	public int getNroHoteles() {
		return nroHoteles;
	}
	
	public int getCantPropiedades(){
		return this.tarjPropiedadList.size();
	}

	public int incrementNroCasas(int cantidad) {
		nroCasas += cantidad;
		return nroCasas;
	}

	public int incrementNroHoteles(int cantidad) {
		nroHoteles += cantidad;
		return nroHoteles;
	}

	/**
	 * @return the tiradaInicial
	 */
	public Dado getTiradaInicial() {
		return tiradaInicial;
	}

	/**
	 * @param tiradaInicial
	 *            the tiradaInicial to set
	 */
	public void setTiradaInicial(Dado tiradaInicial) {
		this.tiradaInicial = tiradaInicial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNombre().toLowerCase().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		return this.getNombre().toLowerCase()
				.equals(((Jugador) object).getNombre().toLowerCase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Jugador [nombre: " + nombre);
		sb.append((this.juego != null) ? ", " + this.juego.toString()
				: "<SIN JUEGO>");
		sb.append((this.ficha != null) ? ", " + this.ficha.toString()
				: "<SIN FICHA>");
		sb.append("] }");

		return sb.toString();
	}
}
