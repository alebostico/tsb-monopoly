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

import monopoly.model.tablero.Casillero;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaPropiedad;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@Entity
@Table(name = "jugador", catalog = "monopoly_db")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Jugador implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "jugadorID")
	private Integer idJugador;
	
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

	/**
	 * Constructor por defecto. inicializa el arraylist tarjetaCarcelList, que
	 * será utilizado para almacenar las tarjetas de comunidad y suerte que
	 * permiten salir de la cárcel.
	 */
	public Jugador(String nombre, Ficha ficha, Juego juego) {
		this.nombre = nombre;
		this.ficha = ficha;
		this.juego = juego;
		this.dinero = 1500;
		this.nroCasas = 0;
		this.nroHoteles = 0;
		tarjetaCarcelList = new ArrayList<>();
		tarjPropiedadList = new ArrayList<>();
		casilleroActual = juego.getTablero().getCasillerosList()[0];
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
	 * @param nombre the nombre to set
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
	public void pagar(int monto) {
		this.setDinero(this.getDinero() - monto);
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

	public int getNroCasas() {
		return nroCasas;
	}


	public int getNroHoteles() {
		return nroHoteles;
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
	 * @param tiradaInicial the tiradaInicial to set
	 */
	public void setTiradaInicial(Dado tiradaInicial) {
		this.tiradaInicial = tiradaInicial;
	}

	/**
	 * Devuelve true si el jugador pasada por parametro es igual al que ejecuta
	 * el metodo compara en funcion del nombre (string) de la ficha
	 * 
	 * @param c
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		Jugador j = (Jugador) o;
		return this.getFicha().equals(j.getFicha());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Jugador [nombre: " + nombre);
		sb.append((this.juego != null) ? ", " + this.juego.toString() : "<SIN JUEGO>");
		sb.append((this.ficha != null) ? ", " + this.ficha.toString() : "<SIN FICHA>");
		sb.append("] }");
		
		return sb.toString();
	}
}
