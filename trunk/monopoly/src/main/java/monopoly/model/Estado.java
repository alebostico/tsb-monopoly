package monopoly.model;

import java.io.Serializable;

public class Estado implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Estados del Juego
	 */
	public static final String ESTADO_CREADO = "creado";
	public static final String ESTADO_ESPERANDO_JUGADOR = "esperando jugador";
	public static final String ESTADO_INICIADO = "iniciado";
	public static final String ESTADO_FINALIZADO = "finalizado";

	public enum EstadoJuego {
		CREADO("creado"), ESPERANDO_JUGADOR("esperando jugador"), INICIADO(
				"iniciado"),FINALIZADO("finalizado");

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

	private String nombre;

	private EstadoJuego estado;

	private static Estado instance;

	/**
	 * @param nombre
	 */
	public Estado(String nombre, EstadoJuego estado) {
		super();
		this.estado = estado;
		this.nombre = nombre;
	}

	/**
	 * 
	 */
	public Estado(EstadoJuego estado) {
		this(estado.getNombreEstadoJuego(), estado);
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
	 * @return the estado
	 */
	public EstadoJuego getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(EstadoJuego estado) {
		this.estado = estado;
	}

	public void actualizarEstadoJuego() {
		switch (estado) {
		case CREADO:
			estado = EstadoJuego.ESPERANDO_JUGADOR;
			break;
		case ESPERANDO_JUGADOR:
			estado = EstadoJuego.INICIADO;
			break;

		case INICIADO:
			estado = EstadoJuego.FINALIZADO;
			break;

		default:
			break;

		}
	}

	public static Estado getInstance() {
		if (instance == null)
			instance = new Estado(EstadoJuego.CREADO);

		return instance;
	}
}
