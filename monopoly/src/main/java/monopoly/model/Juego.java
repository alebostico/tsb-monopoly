/**
 * 
 */
package monopoly.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import monopoly.controller.FichasController;
import monopoly.model.tablero.Tablero;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
@Entity
@Table(name = "juego_guardado", catalog = "monopoly_db")
public class Juego implements Serializable {

	private static final long serialVersionUID = 634594719477426095L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "juegoGuardadoID")
	private int juegoGuardadoID;

	/**
	 * Nombre unico que identifica al juego
	 */
	@Column(name = "nombre_juego")
	private String nombreJuego;

	/**
	 * Un ID de juego único que lo identifica en el conjunto de juegos. Está
	 * formado por varios campos de la clase. Se genera solo en base a los
	 * datos.
	 */
	@Column(name = "juegoID")
	private String uniqueID;

	/**
	 * El "dueño" del Juego. Es el jugador que creó el juego. No se puede
	 * cambiar.
	 */
	@ManyToOne
	@JoinColumn(name = "usuarioID")
	private Usuario owner;

	@Column(name = "fecha_creacion")
	private Date fechaCreacion;

	@Column(name = "fecha_guardado")
	private Date fechaGuardado;

	@Column(name = "fecha_restaurado")
	private Date fechaRestaurado;

	@Transient
	private List<Jugador> jugadoresList; // cambiar por lista circular

	@Transient
	private Dado dado;

	@Column(name = "cant_jugadores")
	private int cantJugadores;

	@Column(name = "nombre_archivo")
	private String nombreArchivo;

	@Transient
	private List<Ficha> fichasPlayerList;

	@Transient
	private Tablero tablero;

	/**
	 * Constructor por defecto
	 * 
	 */
	public Juego() {
		super();
	}

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
		this.cantJugadores = 0;
		this.owner = creador;
		this.fechaCreacion = new Date();
		this.fechaGuardado = null;
		this.fechaRestaurado = null;
		this.jugadoresList = new ArrayList<Jugador>();
		this.fichasPlayerList = FichasController.getFichas();
		// this.tarjetasPropiedadList = TarjetaController
		// .getTarjetasPropiedadesTreeMap();
		this.generateUniqueID();
		this.initJuego();
	}

	public Juego(Usuario creador) {
		this(creador, "");
	}

	public void iniciarJuego() {
		this.initJuego();
	}

	/**
	 * Inicia el juego
	 */
	private void initJuego() {

		// this.banco = new Banco(); // Cargar el Banco
		// this.tablero = new Tablero(banco); // Cargar el tablero
		this.dado = new Dado();

		GestorLogs.registrarLog("Iniciado juego '" + this.getUniqueID() + "'");
	}

	/**
	 * Genera y retorna el identificador único del juego
	 * 
	 * @return el identificador único del juego
	 */
	private String generateUniqueID() {

		StringBuilder sb = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");

		sb.append(owner.getUserName());
		sb.append("_");
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

		boolean returnValue = this.jugadoresList.add(jugador);

		GestorLogs.registrarLog("Agregado jugador '"
				+ jugador.getFicha().getNombre() + "' al juego '"
				+ this.getUniqueID());

		return returnValue;
	}

	/**
	 * Devuelve la cantidad de jugadores humanos que están jugando
	 * 
	 * @return La cantidad de Jugadores humanos del juego
	 */
	public int cantJugadoresHumanos() {
		int contador = 0;
		for (Jugador jugador : jugadoresList) {
			if (jugador.isHumano())
				contador++;
		}
		return contador;
	}

	/**
	 * Devuelve la cantidad de jugadores virtuales que están jugando
	 * 
	 * @return La cantidad de Jugadores virtuales del juego
	 */
	public int cantJugadoresVirtuales() {
		return jugadoresList.size() - cantJugadoresHumanos();
	}

	/**
	 * @return the jugadores
	 */
	public List<Jugador> getJugadoresList() {
		return jugadoresList;
	}

	/**
	 * @return the cantJugadores
	 */
	public int getCantJugadores() {
		return cantJugadores;
	}

	/**
	 * @param cantJugadores
	 *            the cantJugadores to set
	 */
	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

	/**
	 * @return the nombreJuego
	 */
	public String getNombreJuego() {
		return nombreJuego;
	}

	/**
	 * @param nombreJuego
	 *            the nombreJuego to set
	 */
	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
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
	 * @return the fichasPlayerList
	 */
	public List<Ficha> getFichasPlayerList() {
		return fichasPlayerList;
	}

	/**
	 * @param fichasPlayerList
	 *            the fichasPlayerList to set
	 */
	public void setFichasPlayerList(List<Ficha> fichasPlayerList) {
		this.fichasPlayerList = fichasPlayerList;
	}

	/**
	 * Tira los dados y devuelve la suma de los dos.
	 * 
	 * @return La suma de los valores que salieron en los dados.
	 */
	public int tirarDados() {
		return this.getDado().getSuma();
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
	 * @return the tablero
	 */
	public Tablero getTablero() {
		return tablero;
	}

	/**
	 * @param tablero
	 *            the tablero to set
	 */
	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
	}

	public int getJuegoGuardadoID() {
		return juegoGuardadoID;
	}

	public void setJuegoGuardadoID(int juegoGuardadoID) {
		this.juegoGuardadoID = juegoGuardadoID;
	}

	public Date getFechaGuardado() {
		return fechaGuardado;
	}

	public void setFechaGuardado(Date fechaGuardado) {
		this.fechaGuardado = fechaGuardado;
	}

	public Date getFechaRestaurado() {
		return fechaRestaurado;
	}

	public void setFechaRestaurado(Date fechaRestaurado) {
		this.fechaRestaurado = fechaRestaurado;
	}

	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}

	public void setOwner(Usuario owner) {
		this.owner = owner;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
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
				+ this.cantJugadores + "] }";

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
		sb.append(", cantJugadores=" + this.cantJugadores);
		sb.append(", jugadores="
				+ ((this.jugadoresList != null) ? this.jugadoresList.toString()
						: "<SIN JUGADORES>"));
		sb.append(" }");

		return sb.toString();
	}

	public int compareTo(Juego j) {
		return this.getUniqueID().compareTo(j.getUniqueID());
	}
}
