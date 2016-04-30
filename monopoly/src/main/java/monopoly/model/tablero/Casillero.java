/**
 * 
 */
package monopoly.model.tablero;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.Jugador;

/**
 * La clase casillero implementa un casillero del tablero genérico, sin
 * funcionalidad. Si un casillero debe tener una funcionalidad especial, se debe
 * implementar como una clase diferente y que herede de esta (como
 * CasilleroCalle que tiene el método comprarCalle). Los tipos de casilleros
 * pueden ser:
 * <ul>
 * <li>Casillero.CASILLERO_CALLE
 * <li>Casillero.CASILLERO_ESTACION
 * <li>Casillero.CASILLERO_COMPANIA
 * <li>Casillero.CASILLERO_SUERTE
 * <li>Casillero.CASILLERO_COMUNIDAD
 * <li>Casillero.CASILLERO_CARCEL
 * <li>Casillero.CASILLERO_IRACARCEL
 * <li>Casillero.CASILLERO_SALIDA
 * <li>Casillero.CASILLERO_DESCANSO
 * <li>Casillero.CASILLERO_IMPUESTO
 * </ul>
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 * 
 */
public class Casillero implements Serializable {

	private static final long serialVersionUID = 6028621918042358320L;

	private List<Jugador> jugadores;
	private int numeroCasillero;
	private TipoCasillero tipoCasillero;
	private String nombreCasillero;
	// cant casilleros
	public final static String CASILLERO_CALLE = "casillero calle"; // 22
	public final static String CASILLERO_ESTACION = "casillero estación"; // 4
	public final static String CASILLERO_COMPANIA = "casillero compañia"; // 2
	public final static String CASILLERO_SUERTE = "casillero suerte"; // 3
	public final static String CASILLERO_COMUNIDAD = "casillero comunidad"; // 3
	public final static String CASILLERO_CARCEL = "casillero carcel"; // 1
	public final static String CASILLERO_IRACARCEL = "casillero ir a cárcel"; // 1
	public final static String CASILLERO_SALIDA = "casillero salida"; // 1
	public final static String CASILLERO_DESCANSO = "casillero descanso"; // 1
	public final static String CASILLERO_IMPUESTO = "casillero impuesto"; // 2

	public enum TipoCasillero {
		C_CALLE("casillero calle"), C_ESTACION("casillero estación"), C_COMPANIA(
				"casillero compañia"), C_SUERTE("casillero suerte"), C_COMUNIDAD(
				"casillero comunidad"), C_CARCEL("casillero carcel"), C_IRACARCEL(
				"casillero ir a cárcel"), C_SALIDA("casillero salida"), C_DESCANSO(
				"casillero descanso"), C_IMPUESTO("casillero impuesto");

		private final String nombreTipoCasillero;

		TipoCasillero(String nombre) {
			this.nombreTipoCasillero = nombre;
		}

		public String getNombreTipoCasillero() {
			return this.nombreTipoCasillero;
		}

		public String toString() {
			return "{ Tipo Casillero [nombre: " + nombreTipoCasillero + "] }";
		}

	}

	/**
	 * Constructor con parámetros de un casillero.
	 * 
	 * @param numeroCasillero
	 *            El número de casillero en el tablero.
	 * @param nombre
	 *            El nombre del casillero
	 * @param tipoCasillero
	 *            El tipo de casillero.
	 */
	public Casillero(int numeroCasillero, String nombre,
			TipoCasillero tipoCasillero) {
		super();
		this.numeroCasillero = numeroCasillero;
		this.tipoCasillero = tipoCasillero;
		this.nombreCasillero = nombre;
		this.jugadores = new ArrayList<Jugador>();
	}

	/**
	 * 
	 * @return the jugadores
	 */
	public List<Jugador> getJugadores() {
		return jugadores;
	}

	/**
	 * Agrega un jugador a la lista de jugadores que están en ese casillero.
	 * 
	 * @param jugador
	 *            El jugador que se desea eliminar.
	 * @return true si se elimino - false si no se elimino.
	 */
	public boolean addJugador(Jugador jugador) {
		return this.jugadores.add(jugador);
	}

	/**
	 * Elimina el jugador pasado como parámetro de la lista de jugadores que
	 * están en ese casillero.
	 * 
	 * @param jugador
	 *            El jugador que se desea eliminar.
	 * @return true si se elimino - false si no se elimino.
	 */
	public boolean removeJugador(Jugador jugador) {
		return this.jugadores.remove(jugador);
	}

	/**
	 * Devuelve el número de casillero.
	 * 
	 * @return El número de casillero.
	 */
	public int getNumeroCasillero() {
		return numeroCasillero;
	}

	/**
	 * Setea el número de casilelro.
	 * 
	 * @param numeroCasillero
	 *            el número del casillero.
	 */
	public void setNumeroCasillero(int numeroCasillero) {
		this.numeroCasillero = numeroCasillero;
	}

	/**
	 * Devuelve el tipo de casillero.
	 * 
	 * @return El tipo de casillero.
	 */
	public TipoCasillero getTipoCasillero() {
		return tipoCasillero;
	}

	/**
	 * @param tipoCasillero
	 *            the tipoCasillero to set
	 */
	public void setTipoCasillero(TipoCasillero tipoCasillero) {
		this.tipoCasillero = tipoCasillero;
	}

	/**
	 * @param jugadores
	 *            the jugadores to set
	 */
	public void setJugadores(List<Jugador> jugadores) {
		this.jugadores = jugadores;
	}

	public String getNombreCasillero() {
		return nombreCasillero;
	}

	public void setNombreCasillero(String nombreCasillero) {
		this.nombreCasillero = nombreCasillero;
	}

	/**
	 * Pregunta si es un casillero calle.
	 * 
	 * @return <code>True</code> si es casillero calle. <code>False</code> caso
	 *         contrario.
	 */
	public boolean isCasilleroCalle() {
		return this instanceof CasilleroCalle;
	}

	/**
	 * Pregunta si es un casillero estación
	 * 
	 * @return <code>True</code> si es casillero estación. <code>False</code>
	 *         caso contrario.
	 */
	public boolean isCasilleroEstacion() {
		return this instanceof CasilleroEstacion;
	}

	/**
	 * Pregunta si es un casillero companía.
	 * 
	 * @return <code>True</code> si es casillero companía. <code>False</code>
	 *         caso contrario.
	 */
	public boolean isCasilleroCompania() {
		return this instanceof CasilleroCompania;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Casillero [número casillero:" + this.numeroCasillero);
		sb.append(", " + this.tipoCasillero != null ? this.tipoCasillero
				.toString() : "<SIN TIPO CASILLERO>");
		sb.append("] }");

		return sb.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		Casillero c = (Casillero) o;
		return this.numeroCasillero == c.getNumeroCasillero();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.numeroCasillero;
	}

}
