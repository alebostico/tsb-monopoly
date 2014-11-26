/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import monopoly.connection.GameServer;
import monopoly.model.Dado;
import monopoly.model.Estado.EstadoJuego;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.message.game.StartGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class PartidasController {

	private TreeMap<String, JuegoController> juegosControllerList;

	private static PartidasController instance;

	private GameServer monopolyGame;

	private PartidasController() {
		juegosControllerList = new TreeMap<String, JuegoController>();
		GestorLogs.registrarLog("Creando el gestor de juegos");
	}

	public static PartidasController getInstance() {
		if (instance == null)
			instance = new PartidasController();

		return instance;
	}

	/**
	 * 
	 * Método que toma los datos del juego establecido por el creador del juego.
	 * Cómo ser cantidad de jugadores, jugadores virtuales, entre otros. Luego
	 * crea los jugadores.
	 * 
	 * @param senderID
	 *            . id de conexión del jugador creador
	 * @param juego
	 *            objecto juego, que posee los datos del juego
	 */
	public void loadGame(int senderID, Juego juego) {
		// TODO Auto-generated method stub
		JuegoController controller = juegosControllerList.get(juego
				.getUniqueID());
		int cantJugadores = juego.getCantJugadores();

		controller.setJuego(juego);
		controller.getEstadoJuego().actualizarEstadoJuego();
		controller.setCantJugadores(cantJugadores);

		for (Jugador jugador : juego.getJugadoresList()) {
			controller.addPlayer(jugador);
		}
	}

	public void establecerTurnoJugador(int senderId, Object message) {
		// TODO Auto-generated method stub
		Object[] vDatos = (Object[]) ((StartGameMessage) message).message;
		String idJuego = vDatos[0].toString();
		Dado dados = (Dado) vDatos[1];

		JuegoController controller = juegosControllerList.get(idJuego);
		controller.establecerTurnoJugador(senderId, dados);
	}

	/**
	 * Método que agrega un jugador Humano al juego.
	 * 
	 * @param jugador
	 */
	public void joinPlayerGame(Jugador jugador) {
		JuegoController juegoController = buscarControladorJuego(jugador
				.getJuego());
		GestorLogs.registrarLog(String.format(
				"Agregando el jugador %s al juego %s..", jugador.getNombre(), juegoController
						.getJuego().getUniqueID()));
		juegoController.addPlayer(jugador);
	}

	/**
	 * Agrega un juego a la lista de juegos
	 * 
	 * @param juego
	 *            El juego a agregar
	 * @return true si se agrega el juego. fasle en caso contrario
	 */
	private boolean addJuego(JuegoController jc) {
		// TODO: verificar que el juego no exista en la bd
		GestorLogs.registrarLog(String.format(
				"Agregando el juego %s a la juegosControllerList..", jc
						.getJuego().getUniqueID()));
		if (!this.existeJuego(jc.getJuego())) {
			if (juegosControllerList.put(jc.getJuego().getUniqueID(), jc) == null)
				return true;
		}
		return false;
	}

	/**
	 * Crea un juego nuevo, lo agrega a la lista de juegos y lo retorna.
	 * 
	 * @param creador
	 *            El usuario creador (due&ntilde;o) del juego
	 * @param nombre
	 *            El nombre del juego
	 * 
	 * @return El juego creado
	 */
	public Juego crearJuego(Usuario creador, String nombre) {
		// verificar que el nombre del juego no exista
		// tanto en las instancias actuales como en la BD.
		JuegoController jc = new JuegoController(creador, nombre);
		if (!this.addJuego(jc))
			return null;
		GestorLogs.registrarLog("Creando un nuevo juego...");
		return jc.getJuego();
	}

	/**
	 * Verifica si un juego existe en la lista de juegos
	 * 
	 * @param juego
	 *            El juego que cuya existencia se quiere verificar
	 * @return true si el juego existe. false si no existe.
	 */
	public boolean existeJuego(Juego juego) {
		for (JuegoController jc : this.juegosControllerList.values()) {
			if (jc.getJuego().compareTo(juego) == 0)
				return true;
		}
		return false;
	}

	/**
	 * Busca un juego a partir del usuario que lo creo y el nombre del juego
	 * 
	 * @param nombreUsuarioCreador
	 * @param nombreJuego
	 * @return
	 */
	public Juego buscarJuego(String nombreUsuarioCreador, String nombreJuego) {
		// TODO: implementar metodo
		for (JuegoController jc : juegosControllerList.values()) {
			if (jc.getJuego().getNombreJuego().equals(nombreJuego)
					&& jc.getJuego().getOwner().getUserName()
							.equals(nombreUsuarioCreador))
				return jc.getJuego();
		}
		return null;
	}

	/**
	 * Busca el controlador del juego a partir del key del juego
	 * 
	 * @param key
	 *            UniqueID del juego
	 * @return
	 */
	public JuegoController buscarControladorJuego(String key) {
		return this.juegosControllerList.get(key);
	}

	/**
	 * Busca el controlador del juego a partir del juego
	 * 
	 * @param juego
	 * @return
	 */
	public JuegoController buscarControladorJuego(Juego juego) {
		return buscarControladorJuego(juego.getUniqueID());
	}

	/**
	 * Retorna todos los juegos cuyos nombres coinciden con un criterio de
	 * búsqueda
	 * 
	 * @param likeNombre
	 * @return
	 */
	public List<Juego> buscarJuegos(String likeNombre) {
		// TODO: Implementar método
		List<Juego> juegos = new ArrayList<Juego>();
		for (JuegoController jc : juegosControllerList.values()) {
			if (jc.getJuego().getNombreJuego().contains(likeNombre))
				juegos.add(jc.getJuego());
		}
		return juegos;
	}

	/**
	 * Devuelve un juego a partir del UniqueID del juego
	 * 
	 * @param juegoUniqueId
	 *            el UniqueID del juego
	 * @return El Juego
	 */
	public Juego buscarJuego(String juegoUniqueId) {
		return juegosControllerList.get(juegoUniqueId).getJuego();
	}

	/**
	 * Busca todos los juegos de un usuario creados en el período de fechas
	 * pasados como parámetro.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public List<Juego> buscarJuegos(String nombreUsuarioCreador,
			Date fechaDesde, Date fechaHasta) {
		// TODO: implementar metodo
		return null;

	}

	/**
	 * Retorna todos los juegos cuyos nombres coinciden con un criterio de
	 * búsqueda
	 * 
	 * @param likeNombre
	 * @return
	 */
	public List<Juego> buscarJuegos(EstadoJuego estado) {
		// TODO: Implementar método
		List<Juego> juegos = new ArrayList<Juego>();
		for (JuegoController jc : juegosControllerList.values()) {
			if (jc.getEstadoJuego().getEstado().equals(estado))
				juegos.add(jc.getJuego());
		}
		return juegos;
	}

	/**
	 * Devuelve un juego a partir de su uniqueID
	 * 
	 * @param uniqueID
	 * @return
	 */
	public Juego getJuego(String uniqueID) {
		return buscarJuego(uniqueID);
	}

	public GameServer getMonopolyGame() {
		return monopolyGame;
	}

	public void setMonopolyGame(GameServer monopolyGame) {
		this.monopolyGame = monopolyGame;
	}

}
