/**
 * 
 */
package monopoly.model.tablero;

import java.util.LinkedList;
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
 * @author Oliva Pablo
 * 
 */
public class Casillero {

	private List<Jugador> jugadores;
	private int numeroCasillero;
	private int tipoCasillero;
	private Tablero tablero;

	public final static int CASILLERO_CALLE = 1; // 22 casilleros
	public final static int CASILLERO_ESTACION = 2; // 4 casilleros
	public final static int CASILLERO_COMPANIA = 3; // 2 casilleros
	public final static int CASILLERO_SUERTE = 4; // 3 casilleros
	public final static int CASILLERO_COMUNIDAD = 5; // 3 casilleros
	public final static int CASILLERO_CARCEL = 6; // 1 casilleros
	public final static int CASILLERO_IRACARCEL = 7; // 1 casilleros
	public final static int CASILLERO_SALIDA = 8; // 1 casilleros
	public final static int CASILLERO_DESCANSO = 9; // 1 casilleros
	public final static int CASILLERO_IMPUESTO = 10; // 2 casilleros

	/**
	 * Constructor con parámetros de un casillero.
	 * 
	 * @param numeroCasillero
	 *            El número de casillero en el tablero.
	 * @param tipoCasillero
	 *            El tipo de casillero.
	 */
	public Casillero(int numeroCasillero, int tipoCasillero, Tablero tablero) {
		super();
		this.numeroCasillero = numeroCasillero;
		this.tipoCasillero = tipoCasillero;
		this.jugadores = new LinkedList<Jugador>();
		this.tablero = tablero;
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
	public int getTipoCasillero() {
		return tipoCasillero;
	}

	/**
	 * Devuelve el tipo de casillero en formato String
	 * 
	 * @return El tipo de casillero en String
	 */
	public String getTipoCasilleroString() {
		return this.tipoCasilleroToString(this.tipoCasillero);
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public String toString() {
		return "Casillero [ numeroCasillero=" + this.numeroCasillero
				+ ", tipoCasillero="
				+ this.tipoCasilleroToString(this.tipoCasillero) + " ]";
	}

	private String tipoCasilleroToString(int tipoCasillero) {
		// CASILLERO_CALLE = 1; // 22 casilleros
		// CASILLERO_ESTACION = 2; // 4 casilleros
		// CASILLERO_COMPANIA = 3; // 2 casilleros
		// CASILLERO_SUERTE = 4; // 3 casilleros
		// CASILLERO_COMUNIDAD = 5; // 3 casilleros
		// CASILLERO_CARCEL = 6; // 1 casilleros
		// CASILLERO_IRACARCEL = 7; // 1 casilleros
		// CASILLERO_SALIDA = 8; // 1 casilleros
		// CASILLERO_DESCANSO = 9; // 1 casilleros
		// CASILLERO_IMPUESTO = 10; // 2 casilleros

		switch (tipoCasillero) {
		case 1:
			return "CASILLERO_CALLE";
		case 2:
			return "CASILLERO_ESTACION";
		case 3:
			return "CASILLERO_COMPANIA";
		case 4:
			return "CASILLERO_SUERTE";
		case 5:
			return "CASILLERO_COMUNIDAD";
		case 6:
			return "CASILLERO_CARCEL";
		case 7:
			return "CASILLERO_IRACARCEL";
		case 8:
			return "CASILLERO_SALIDA";
		case 9:
			return "CASILLERO_DESCANSO";
		case 10:
			return "CASILLERO_IMPUESTO";
		default:
			return "<_TIPO_CASILLERO_INCORRECTO_>";
		}
	}
}
