/**
 * 
 */
package monopoly.model;

import java.io.Serializable;

import monopoly.model.tablero.Casillero;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class JugadorVirtual extends Jugador implements Serializable {

	private static final long serialVersionUID = -8121213780558221444L;

	public static final String TIPO_COMPRADOR_PRIMERIZO = "comprador primerizo"; // Todo
																					// random
	public static final String TIPO_EMPRESARIO = "empresario"; // basado en
																// estadísticas
	public static final String TIPO_MAGNATE = "magnate"; // basado en reglas

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

	public JugadorVirtual(String nombre, Ficha ficha, Juego juego, Casillero casillero,
			TipoJugador tipoJugador) {
		super(nombre, ficha, juego, casillero);
		this.tipoJugador = tipoJugador;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.model.Jugador#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ Jugador Virtual [");
		sb.append((this.tipoJugador != null) ? ", tipo jugador: "
				+ this.tipoJugador.getNombreTipo() : "<SIN TIPO JUGADOR> ");
		sb.append("] }");
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.model.Jugador#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;

		if (object == null || getClass() != object.getClass())
			return false;

		return this.getNombre().toLowerCase()
				.equals(((JugadorVirtual) object).getNombre().toLowerCase());
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

}
