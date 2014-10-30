/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class JugadorVirtual extends Jugador implements Serializable{

	private static final long serialVersionUID = -8121213780558221444L;
	
	public static final String TIPO_COMPRADOR_PRIMERIZO = "comprador primerizo";
	public static final String TIPO_EMPRESARIO = "empresario";
	public static final String TIPO_MAGNATE = "magnate";

	public enum TipoJugador {
		TJ_COMPRADOR_PRIMERIZO("comprador primerizo"), TJ_EMPRESARIO(
				"empresario"), TJ_MAGNATE("magnate");

		private final String nombreTipo;

		TipoJugador(String nombre) {
			this.nombreTipo = nombre;
		}

		public String getNombreTipo() {
			return this.nombreTipo;
		}
	}

	private TipoJugador tipoJugador;


	public JugadorVirtual(String nombre, Ficha ficha, Juego juego,
			TipoJugador tipoJugador) {
		super(nombre, ficha, juego);
		this.tipoJugador = tipoJugador;
		GestorLogs.registrarLog("Nuevo jugador Virtual '" + nombre + "'");
		GestorLogs.registrarDebug(this.toString());
	}

	/**
	 * @return the tipoJugador
	 */
	public TipoJugador getTipoJugador() {
		return tipoJugador;
	}

	/**
	 * @param tipoJugador
	 *            the tipoJugador to set
	 */
	public void setTipoJugador(TipoJugador tipoJugador) {
		this.tipoJugador = tipoJugador;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Jugador Virtual [");
		sb.append((this.tipoJugador != null) ? ", tipo jugador: " + this.tipoJugador.getNombreTipo(): "<SIN TIPO JUGADOR> ");
		sb.append("] }");
		return sb.toString();
	}

}
