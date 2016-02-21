/**
 * 
 */
package monopoly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import monopoly.connection.GameServer;
import monopoly.model.Dado;
import monopoly.model.Estado.EstadoJuego;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.SubastaStatus;
import monopoly.model.Usuario;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.EnumSalidaCarcel;
import monopoly.util.constantes.EnumsTipoImpuesto;
import monopoly.util.exception.SinDineroException;
import monopoly.util.message.game.SaveGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class PartidasController {

	private TreeMap<String, JuegoController> juegosControllerList;

	private static PartidasController instance;

	private JuegoController juegoController;

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
	 *            id de conexión del jugador creador
	 * @param juego
	 *            objecto juego, que posee los datos del juego
	 */
	public void loadGame(int senderID, Juego juego) throws Exception {
		juegoController = juegosControllerList.get(juego.getUniqueID());
		int cantJugadores = juego.getCantJugadores();

		juegoController.setJuego(juego);
		juegoController.getEstadoJuego().actualizarEstadoJuego();
		juegoController.setCantJugadores(cantJugadores);

		for (Jugador jugador : juego.getJugadoresList()) {
			juegoController.addPlayer(jugador);
		}
	}

	/**
	 * Guarda el estado de un juego para continuar jugándolo más adelante.
	 * 
	 * @param uniqueIdJuego
	 *            El {@code uniqueID} del juego que se quiere guardar
	 * @throws IOException
	 *             Si no se pudo guardar el juego.
	 */
	public void saveGame(int senderID, String uniqueIdJuego) {
		juegoController = juegosControllerList.get(uniqueIdJuego);
		monopolyGame = PartidasController.getInstance().getMonopolyGame();
		SaveGameMessage saveGameMessage;

		try {
			SerializerController.saveGame(juegoController);
			saveGameMessage = new SaveGameMessage(uniqueIdJuego, null);
			GestorLogs.registrarLog("Se guardó el juego " + uniqueIdJuego);
		} catch (IOException e) {
			saveGameMessage = new SaveGameMessage(uniqueIdJuego, e);
			GestorLogs.registrarError("Error al guardar el juego "
					+ uniqueIdJuego);
			GestorLogs.registrarException(e);
		}

		monopolyGame.sendToOne(senderID, saveGameMessage);
	}

	/**
	 * Busca un juego en la base de datos y lo restaura
	 * 
	 * @param nombre
	 *            El nombre del juego
	 * @return El {@code JuegoController} del juego restaurado
	 */
	public JuegoController reloadGame(int senderID, String nombre)
			throws Exception {
		JuegoController juegoController = null;
		juegoController = SerializerController.loadGame(nombre);
		this.juegosControllerList.put(juegoController.getJuego().getUniqueID(),
				juegoController);
		juegoController.reloadGame(senderID);
		return juegoController;
	}

	public void establecerTurnoJugador(int senderId, String idJuego, Dado dados)
			throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.establecerTurnoJugador(senderId, dados);
	}

	/**
	 * Método para avanzar de casillero en base a los resultados de los dados.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano.
	 * @param idJuego
	 *            El juego en el que se avanza
	 * @param dados
	 *            La cantidad de casilleros que se debe avanzar
	 */
	public void avanzarDeCasillero(int senderId, String idJuego, Dado dados)
			throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.avanzarDeCasillero(senderId, dados);
	}

	/**
	 * Método que agrega un jugador Humano al juego.
	 * 
	 * @param jugador
	 */
	public void joinPlayerGame(Jugador jugador) throws Exception {
		juegoController = buscarControladorJuego(jugador.getJuego());
		GestorLogs.registrarLog(String.format(
				"Agregando el jugador %s al juego %s..", jugador.getNombre(),
				juegoController.getJuego().getUniqueID()));
		juegoController.addPlayer(jugador);
	}

	/**
	 * Método solicitado por un jugador para comprar una propiedad.
	 * 
	 * @param senderId
	 *            id de conexión del jugador que desea comprar la propiedad.
	 * @param nombrePropiedad
	 *            El nombre de la propiedad que desea comprar el jugador.
	 */
	public void comprarPropiedad(String idJuego, int senderId,
			String nombrePropiedad) throws SinDineroException, Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.comprarPropiedad(senderId, nombrePropiedad);
	}

	/**
	 * Método para hipotecar una propiedad
	 * 
	 * @param idJuego
	 *            El ID del juego en donde se va a hipotecar
	 * @param senderId
	 *            Id de conexión del jugador que desea hipotecar la propiedad.
	 * @param propiedad
	 *            La {@code TarjetaPropiedad} de la propiedad que se va a
	 *            hipotecar
	 * @return La {@code  TarjetaPropiedad} de la propiedad hipotecada o
	 *         {@code null} si no se pudo hipotecar.
	 * @throws Exception
	 */
	public TarjetaPropiedad hipotecarPropiedad(String idJuego, int senderId,
			TarjetaPropiedad propiedad) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		return juegoController.hipotecarPropiedad(propiedad);
	}

	/**
	 * Método para deshipotecar una propiedad
	 * 
	 * @param idJuego
	 *            El ID del juego en donde se va a deshipotecar
	 * @param senderId
	 *            Id de conexión del jugador que desea deshipotecar la
	 *            propiedad.
	 * @param propiedad
	 *            La {@code TarjetaPropiedad} de la propiedad que se va a
	 *            deshipotecar
	 * @return La {@code  TarjetaPropiedad} de la propiedad deshipotecada o
	 *         {@code null} si no se pudo deshipotecar.
	 * @throws Exception
	 */
	public TarjetaPropiedad deshipotecarPropiedad(String idJuego, int senderId,
			TarjetaPropiedad propiedad) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		return juegoController.deshipotecarPropiedad(propiedad);
	}

	/**
	 * Método para construir edificios en una calle
	 * 
	 * @param idJuego
	 *            El ID del juego en donde se va a construir
	 * @param senderId
	 *            Id de conexión del jugador que desea construir.
	 * @param propiedad
	 *            La {@code TarjetaCalle} de la calle donde se va a construir
	 * @return El dinero invertido en construir o {@code -1} si no se pudo
	 *         construir.
	 * @throws Exception
	 */
	public int construirEdificios(String idJuego, int senderId,
			TarjetaCalle calle, int cantidad) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		return juegoController.construirEdificios(calle, cantidad);
	}
	
	/**
	 * Método para vender edificios en una calle
	 * 
	 * @param idJuego
	 *            El ID del juego en donde se va a construir
	 * @param senderId
	 *            Id de conexión del jugador que desea construir.
	 * @param propiedad
	 *            La {@code TarjetaCalle} de la calle donde se va a construir
	 * @return El dinero ganado en vender o {@code -1} si no se pudo
	 *         vender.
	 * @throws Exception
	 */
	public int venderEdificios(String idJuego, int senderId,
			TarjetaCalle calle, int cantidad) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		return juegoController.venderEdificios(calle, cantidad);
	}

	/**
	 * Método ejecutado para realizar el objetivo de la tarjeta comunidad o
	 * suerte que salió.
	 * 
	 * @param idJuego
	 *            identificador del juego.
	 * @param senderId
	 *            id de conexión del jugador humano.
	 * @param tarjeta
	 *            Tarjeta obtenida del mazo.
	 * @throws Exception
	 */
	public void jugarTarjeta(String idJuego, int senderId, Tarjeta tarjeta)
			throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.realizarObjetivoTarjeta(senderId, tarjeta);
	}

	/**
	 * Método para avanzar de turno cuando el jugador confirma que finalizó la
	 * jugada.
	 * 
	 * @param idJuego
	 *            identificador del juego.
	 * @throws Exception
	 */
	public void siguienteTurno(String idJuego) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.siguienteTurno(true);
	}

	/**
	 * Método para llevar preso al jugador.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano.
	 * @param idJuego
	 *            identificador del juego.
	 * @throws Exception
	 */
	public void irALaCarcel(int senderId, String idJuego) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.irALaCarcel(senderId);
	}

	/**
	 * Método para pagar el alquiler de un jugador humano a otro jugador.
	 * 
	 * @param senderId
	 * @param idJuego
	 * @param tarjetaPropiedad
	 * @throws Exception
	 */
	public void pagarAlquiler(int senderId, String idJuego, int propiedadId)
			throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.pagarAlquiler(senderId, propiedadId);
	}

	public void addContadorPagos(int senderId, String idJuego) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.addContadorPagos();
		if (juegoController.checkPagaronTodos()) {
			juegoController.siguienteTurno(true);
		}
	}

	/**
	 * Método para cobrar el impuesto de lujo.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano.
	 * @param idJuego
	 *            identificador del juego.
	 * @param tipoImpuesto
	 * @throws Exception
	 */
	public void impuestoAlCapital(int senderId, String idJuego,
			EnumsTipoImpuesto tipoImpuesto) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.impuestoAlCapital(senderId, tipoImpuesto);
	}

	/**
	 * Método para que el jugador pague al banco un determinado {@code monto}.
	 * 
	 * @param idJuego
	 *            identificador del juego.
	 * @param senderId
	 *            id de conexión del jugador humano.
	 * @param monto
	 *            cantidad de dinero que el jugador le pagará al banco.
	 */
	public void pagarAlBanco(String idJuego, int senderId, int monto,
			String mensaje) throws Exception, SinDineroException {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.pagarAlBanco(senderId, monto, mensaje);
	}

	/**
	 * Método que verifica si el jugador ha sacado dados dobles. Si es así sale
	 * de la cárcel.
	 * 
	 * @param senderId
	 * @param idJuego
	 * @param dados
	 * @throws Exception
	 */
	public void tirarDadosDoblesSalirCarcel(int senderId, String idJuego,
			Dado dados) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.tirarDadosDoblesSalirCarcel(senderId, dados);
	}

	/**
	 * Método que paga al banco para quedar en libertad.
	 * 
	 * @param senderId
	 * @param idJuego
	 */
	public void pagarSalidaDeCarcel(int senderId, String idJuego,
			EnumSalidaCarcel tipoSalida) throws Exception, SinDineroException {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.pagarSalidaDeCarcel(senderId, tipoSalida);
	}

	/**
	 * Envía a todos los jugadores una historia de una acción realizada por un
	 * jugador en particular.
	 * 
	 * @param senderId
	 *            Id de conección del jugador.
	 * @param idJuego
	 *            Identificador del juego.
	 * @param history
	 *            Historia de la acción realizada por el jugador.
	 */
	public void sendHistoryGame(int senderId, String idJuego, History history)
			throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.sendHistoryGame(history);
	}

	public void subastar(String idJuego, int senderId,
			SubastaStatus subastaStatus) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.subastar(senderId, subastaStatus);
	}

	public void finalizarSubasta(String idJuego, int senderId) throws Exception {
		juegoController = juegosControllerList.get(idJuego);
		juegoController.finalizarSubasta(senderId);
	}

	// =====================================================================//
	// ========================= Gestión del juego =========================//
	// =====================================================================//

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
	 * @param nombreUsuarioCreador
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public List<Juego> buscarJuegos(String nombreUsuarioCreador,
			Date fechaDesde, Date fechaHasta) {
		return null;

	}

	/**
	 * Retorna todos los juegos cuyos nombres coinciden con un criterio de
	 * búsqueda
	 * 
	 * @param estado
	 * @return
	 */
	public List<Juego> buscarJuegos(EstadoJuego estado) {
		List<Juego> juegos = new ArrayList<Juego>();
		for (JuegoController jc : juegosControllerList.values()) {
			if (jc.getEstadoJuego().getEstadoJuego().equals(estado))
				juegos.add(jc.getJuego());
		}
		return juegos;
	}

	/**
	 * Busca todos los juegos guardados que se crearon por el usuario
	 * {@code creador}
	 * 
	 * @param creador
	 *            El creador de los juegos que se deben buscar
	 * @return Los juegos guardados del usuario {@code creador}
	 */
	public List<Juego> buscarJuegosGuardados(Usuario creador) {
		return JuegoController.buscarJuegosGuardados(creador);
	}

	/**
	 * Confirma que un juego se restauró correctamente. Actualiza la fecha de
	 * restauración del juego en la base de datos. Previene que un juego se
	 * pueda restaurar mas de una vez por cada vez que se guarda
	 * 
	 * @param juego
	 *            El juego que se quiere guardar
	 * @return El juego actualizado o {@code null} si no se pudo guardar.
	 */
	public Juego confirmarJuegoRestaurado(Juego juego) {
		return SerializerController.updateRestoreDate(juego);
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

	public void sendChatMessage(String uniqueID, History history)
			throws Exception {
		juegoController = juegosControllerList.get(uniqueID);
		juegoController.sendChatMessage(history);
	}

}
