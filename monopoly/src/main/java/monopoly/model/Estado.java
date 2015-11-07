package monopoly.model;

import java.io.Serializable;

public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Estados del Juego
	 */
	public static final String ESTADO_CREADO = "Creado";
	public static final String ESTADO_ESPERANDO_JUGADOR = "Esperando jugador";
	public static final String ESTADO_ESTABLECIENDO_TURNOS = "Estableciendo turnos";
	public static final String ESTADO_INICIADO = "Iniciado";
	public static final String ESTADO_TIRAR_DADO = "Tirando dado para avanzar de Casillero";
	public static final String ESTADO_FINALIZADO = "Finalizado";

	public enum EstadoJuego {
		CREADO("creado"), ESPERANDO_JUGADOR("esperando jugador"), ESTABLECIENDO_TURNOS(
				"Estableciendo turnos"), INICIADO("iniciado"), TIRAR_DADO(
				"Tirando dado para avanzar de Casillero"), JUGANDO(
				"Jugador Jugando"), ESPERANDO_TURNO(
				"Esperando que el jugador termine su turno"), PRESO("Jugador en la cárcel"),
				LIBRE("Queda libre de la cárcel"), DADOS_DOBLES("Jugando otro turno por dados dobles"),
				FINALIZADO(
				"finalizado");

		private final String nombreEstadoJuego;

		EstadoJuego(String nombre) {
			nombreEstadoJuego = nombre;
		}

		/**
		 * @return the nombreEstadoJuego
		 */
		public String getNombreEstadoJuego() {
			return nombreEstadoJuego;
		}

	}

	public enum EstadoJugador {
		EJ_JUGANDO("jugando"), EJ_CARCEL("en la carcel"), EJ_BANCARROTA(
				"en bancarrota");

		private final String nombreEstado;

		private EstadoJugador(String nombreEstado) {
			this.nombreEstado = nombreEstado;
		}

		public String getNombreEstado() {
			return nombreEstado;
		}
	}

	private String nombre;

	// private int idEstado;

	private EstadoJuego estadoJuego;

	private EstadoJugador estadoJugador;

	/**
	 * @param nombre
	 */
	public Estado(String nombre, EstadoJuego estado) {

	}

	public Estado(EstadoJuego estado) {
		super();
		this.estadoJuego = estado;
		this.nombre = estado.getNombreEstadoJuego();
	}

	public Estado(EstadoJugador estado) {
		super();
		this.estadoJugador = estado;
		this.nombre = estado.getNombreEstado();
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @return the estado
	 */
	public EstadoJuego getEstadoJuego() {
		return estadoJuego;
	}

	/**
	 * 
	 * @return
	 */
	public EstadoJugador getEstadoJugador() {
		return estadoJugador;
	}

	public void actualizarEstadoJuego() {
		switch (estadoJuego) {
		case CREADO:
			estadoJuego = EstadoJuego.ESPERANDO_JUGADOR;
			break;
		case ESPERANDO_JUGADOR:
			estadoJuego = EstadoJuego.ESTABLECIENDO_TURNOS;
			break;
		case ESTABLECIENDO_TURNOS:
			estadoJuego = EstadoJuego.INICIADO;
			break;
		case INICIADO:
			estadoJuego = EstadoJuego.FINALIZADO;
			break;

		default:
			break;

		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o.getClass() != this.getClass())
			return false;
		Estado f = (Estado) o;
		return this.getNombre().equals(f.getNombre());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.getNombre().hashCode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String tipo = new String();
		if (this.estadoJuego != null)
			tipo = "EstadoJuego";
		else if (this.estadoJugador != null)
			tipo = "EstadoJugador";

		StringBuilder sb = new StringBuilder();
		sb.append("{ Estado [tipo: " + tipo + ", nombre: " + this.getNombre()
				+ "] }");
		return sb.toString();
	}

}
