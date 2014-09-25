/**
 * 
 */
package monopoly.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import monopoly.controller.FichasController;
import monopoly.controller.TarjetaController;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Juego {

	/**
	 * Nombre unico que identifica al juego
	 */
	private String nombreJuego;

	/**
	 * Un ID de juego único que lo identifica en el conjunto de juegos. Está
	 * formado por varios campos de la clase. Se genera solo en base a los
	 * datos.
	 */
	private String uniqueID;

	/**
	 * El "dueño" del Juego. Es el jugador que creó el juego. No se puede
	 * cambiar.
	 */
	private Usuario owner;

	private Date fechaCreacion;

	private Banco banco;

	private List<Jugador> jugadoresList; // cambiar por lista circular

	private TarjetaController gestorTarjetas;

	private FichasController gestorFichas;

	private Tablero tablero;

	private Dado dado;

	private List<TarjetaPropiedad> tarjetasPropiedadList;

	/**
	 * Constructor con parametros
	 * 
	 * @param nombreJuego
	 *            El nombre del juego
	 * @param creador
	 *            El creador y dueño del juego
	 */
	public Juego(Usuario creador, String nombreJuego) {
		this.nombreJuego = nombreJuego;
		this.owner = creador;
		this.fechaCreacion = new Date();
		this.jugadoresList = new ArrayList<Jugador>();
		this.generateUniqueID();
		GestorLogs.registrarLog("Creado nuevo juego '" + this.getUniqueID()
				+ "'");
		this.initJuego();
		// GestorLogs.registrarDebug(this.toString());
	}
	
	public Juego(Usuario creador) {
		this.owner = creador;
		this.fechaCreacion = new Date();
		this.jugadoresList = new ArrayList<Jugador>();
		this.generateUniqueID();
		GestorLogs.registrarLog("Creado nuevo juego '" + this.getUniqueID()
				+ "'");
		this.initJuego();
		// GestorLogs.registrarDebug(this.toString());
	}

	/**
	 * Inicia el juego
	 */
	private void initJuego() {

		
		this.banco = new Banco(); // Cargar el Banco
		this.tablero = new Tablero(banco); // Cargar el tablero
		this.dado = new Dado();
		this.gestorTarjetas = new TarjetaController(this);
		this.gestorFichas = new FichasController();

		GestorLogs.registrarLog("Iniciado juego '" + this.getUniqueID() + "'");
	}

	/**
	 * Genera y retorna el identificador único del juego
	 * 
	 * @return el identificador único del juego
	 */
	private String generateUniqueID() {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

		sb.append(owner.getUserName());
		sb.append("--");
		sb.append(formatter.format(this.getFechaCreacion()));

		this.uniqueID = sb.toString();

		return this.uniqueID;
	}

	/**
	 * Agrega un jugador al juego.
	 * 
	 * @param jugador
	 *            El jugador que se va a agregar
	 * @return true si se agrego, false en caso contrario
	 */
	public boolean addJugador(Jugador jugador) {

		for (Jugador j : this.jugadoresList) {
			if (j.equals(jugador))
				return false;
		}
		// ubica al jugador en el casillero 1
		this.getTablero().moverACasillero(jugador, 1, false);
		// y lo agrega a la lista de jugadores.
		boolean returnValue = this.jugadoresList.add(jugador);

		GestorLogs.registrarLog("Agregado jugador '"
				+ jugador.getFicha().getNombre() + "' al juego '"
				+ this.getUniqueID() + "' en el casillero #"
				+ jugador.getCasilleroActual().getNumeroCasillero());

		return returnValue;
	}

	/**
	 * Agrega un nuevo jugador. El nombre debe ser alguna de las constantes
	 * estáticas de la clase Ficha:
	 * <ul>
	 * <li>Ficha.TIPO_CARRETILLA</li>
	 * <li>Ficha.TIPO_AUTO</li>
	 * <li>Ficha.TIPO_SOMBRERO</li>
	 * <li>Ficha.TIPO_BOTA</li>
	 * <li>Ficha.TIPO_PLANCHA</li>
	 * <li>Ficha.TIPO_CARRETILLA</li>
	 * <li>Ficha.TIPO_DEDAL</li>
	 * <li>Ficha.TIPO_BARCO</li>
	 * <li>Ficha.TIPO_PERRO</li>
	 * <li>Ficha.TIPO_BOLSA_DINERO</li>
	 * <li>Ficha.TIPO_CABALLO</li>
	 * <li>Ficha.TIPO_CANON</li>
	 * </ul>
	 * 
	 * @param nombreFicha
	 *            El nombre de la ficha
	 * @param usuario
	 *            El usuario que esta por jugar
	 * @return El jugador que se crea.
	 */
	// public Jugador addJugador(String nombreFicha, Usuario usuario) {
	// Ficha ficha = this.gestorFichas.getFicha(nombreFicha);
	// if (ficha == null)
	// return null;
	// Jugador jugador = new Jugador(ficha, this);
	// this.addJugador(jugador);
	// return jugador;
	// }

	/**
	 * @return the banco
	 */
	public Banco getBanco() {
		return banco;
	}

	/**
	 * @return the jugadores
	 */
	public List<Jugador> getJugadoresList() {
		return jugadoresList;
	}

	/**
	 * @return the tablero
	 */
	public Tablero getTablero() {
		return tablero;
	}

	/**
	 * @return the tarjetasPropiedadList
	 */
	public List<TarjetaPropiedad> getTarjetasPropiedadList() {
		return tarjetasPropiedadList;
	}

	public int cantJugadores() {
		return ((this.jugadoresList != null) ? this.jugadoresList.size() : 0);
	}

	/**
	 * @return the nombreJuego
	 */
	public String getNombreJuego() {
		return nombreJuego;
	}

	/**
	 * Devuelve la fecha de creación como Date
	 * 
	 * @return La fecha de ceación en formato de fecha
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * Devuelve la fecha de creación en forma de String con el siguiente
	 * formato: <code>dd/MM/yyyy HH:mm:ss</code>
	 * 
	 * @return la fecha de creación como cadena de texto.
	 */
	public String getFechaCreacionString() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return formatter.format(this.getFechaCreacion());
	}

	/**
	 * Devuelve el creador y dueño del juego.
	 * 
	 * @return the owner
	 */
	public Usuario getOwner() {
		return owner;
	}

	/**
	 * Retorna un identificador unico del juego
	 * 
	 * @return the uniqueID
	 */
	public String getUniqueID() {
		return uniqueID;
	}

	/**
	 * Devuelve la ficha que tiene el nombre pasado por parámetro. El nombre
	 * debe ser alguna de las constantes estáticas de la clase GestorFichas:
	 * <ul>
	 * <li>GestorFichas.F_CARRETILLA</li>
	 * <li>GestorFichas.F_AUTO</li>
	 * <li>GestorFichas.F_SOMBRERO</li>
	 * <li>GestorFichas.F_BOTA</li>
	 * <li>GestorFichas.F_PLANCHA</li>
	 * <li>GestorFichas.F_CARRETILLA</li>
	 * <li>GestorFichas.F_DEDAL</li>
	 * <li>GestorFichas.F_BARCO</li>
	 * <li>GestorFichas.F_PERRO</li>
	 * <li>GestorFichas.F_BOLSA_DINERO</li>
	 * <li>GestorFichas.F_CABALLO</li>
	 * <li>GestorFichas.F_CANON</li>
	 * </ul>
	 * 
	 * @param nombreFicha
	 *            El nombre de la ficha
	 * @return La instancia de la clase Ficha o null si no se encuentra una
	 *         ficha con ese nombre.
	 */
	public Ficha getFicha(String nombreFicha) {
		return this.gestorFichas.getFicha(nombreFicha);
	}

	/**
	 * Tira los dados y devuelve la suma de los dos.
	 * 
	 * @return La suma de los valores que salieron en los dados.
	 */
	public int tirarDados() {
		return this.getDado().tirarDados();
	}

	/**
	 * Devuelve el dado
	 * 
	 * @return the dado
	 */
	public Dado getDado() {
		return dado;
	}

	/**
	 * Un toSrting resumido con los atributos de la instancia.
	 */
	@Override
	public String toString() {
		return "{ Juego [uniqueID: " + this.getUniqueID() + ", nombre juego: "
				+ this.getNombreJuego() + ", fecha creación: "
				+ this.getFechaCreacionString() + ", owner: "
				+ this.getOwner().getUserName() + ", cant. jugadores:"
				+ this.cantJugadores() + "] }";

	}

	/**
	 * Un toString detallado con todos los datos de la instancia. Llama a los
	 * toString de Tablero, Banco, Jugadores, Tarjetas, etc.
	 */
	public String toStringAll() {
		StringBuilder sb = new StringBuilder();
		sb.append("JuegoCompleto { nombreJuego=" + this.getNombreJuego());
		sb.append(", fechaCreacion=" + this.getFechaCreacionString());
		sb.append(", uniqueID=" + this.getUniqueID());
		sb.append(", owner=" + this.getOwner().getUserName());
		sb.append(", cantJugadores=" + this.cantJugadores());
		sb.append(", tablero=" + this.getTablero().toString());
		sb.append(", jugadores="
				+ ((this.jugadoresList != null) ? this.jugadoresList.toString()
						: "<SIN JUGADORES>"));
		sb.append(", tarjetas=" + this.gestorTarjetas.toString());
		sb.append(", banco=" + this.banco.toString());
		sb.append(" }");

		return sb.toString();
	}

	public int compareTo(Juego j) {
		return this.getUniqueID().compareTo(j.getUniqueID());
	}
}
