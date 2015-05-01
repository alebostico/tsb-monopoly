/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.List;

import monopoly.model.Dado;
import monopoly.model.Estado;
import monopoly.model.Estado.EstadoJuego;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;
import monopoly.util.StringUtils;
import monopoly.util.message.game.HistoryGameMessage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class JuegoController {

	private Juego juego;

	private Estado estadoJuego;

	private int cantJugadores;

	private BancoController gestorBanco;

	private TableroController gestorTablero;

	private JugadorController gestorJugadores;

	private MonopolyGameStatus status;
	
	public JuegoController(Usuario creador, String nombre) {
		this.gestorBanco = new BancoController();
		this.gestorTablero = new TableroController();
		this.juego = new Juego(creador, nombre);
		this.juego.setTablero(gestorTablero.getTablero());
		this.estadoJuego = new Estado(EstadoJuego.CREADO);
		this.gestorJugadores = new JugadorController();
	}

	/**
	 * 
	 * Método que agrega un jugador al juego, informa al resto de los jugadores
	 * de qué jugador se unió al juego, valida de que se haya completado la
	 * cantidad de jugadores especificados por el creador y establece los
	 * turnos.
	 * 
	 * @param jugador
	 */
	public void addPlayer(Jugador jugador) {
		this.gestorJugadores.addPlayer(jugador);

		History history = new History(StringUtils.getFechaActual(),
				jugador.getNombre(), "Se unió al juego.");
		HistoryGameMessage msg = new HistoryGameMessage(history);
		if (jugador instanceof JugadorHumano)
			sendToOther(msg, ((JugadorHumano) jugador).getSenderID());

		if (this.gestorJugadores.cantJugadoresConectados() == cantJugadores) {
			estadoJuego.actualizarEstadoJuego();
			gestorJugadores.establecerTurnos();
		}
	}

	/**
	 * 
	 * Método que establece la suma de dados obtenidos en la tirada inicial para
	 * establecer el turno de inicio del juego. Luego valida de que todos los
	 * participantes hayan arrojado sus dados para establecer el orden de
	 * turnos.
	 * 
	 * @param key
	 *            id de conexión del jugador humano conectado al juego.
	 * @param dados
	 *            objecto dado con los números obtenidos.
	 */
	public void establecerTurnoJugador(int key, Dado dados) {
		// TODO Auto-generated method stub
		JugadorHumano jugador = gestorJugadores.getJugadorHumano(key);
		jugador.setTiradaInicial(dados);
		boolean tiraronTodosDados = true;
		for (Jugador j : gestorJugadores.getListaJugadoresHumanos()) {
			if (j.getTiradaInicial() == null) {
				tiraronTodosDados = false;
				break;
			}
		}
		if (tiraronTodosDados) {
			estadoJuego.actualizarEstadoJuego();
			ordenarTurnos();

			List<History> historyList = new ArrayList<History>();

			History history = new History(StringUtils.getFechaActual(),
					gestorJugadores.getCurrentPlayer().getNombre(),
					"Turno para tirar los dados.");
			historyList.add(history);

			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.TIRAR_DADO, null,gestorJugadores.getCurrentPlayer(),
					historyList);
			sendToAll(status);

			// tirarDadosJugador();
		}
	}

	/**
	 * 
	 * Método para ordenar la lista cicular de jugadores para establecer el
	 * turno de los mismos en base a los resultados de los dados arrojados. Una
	 * vez ordenados informa a los jugadores el orden establecido.
	 */
	private void ordenarTurnos() {
		this.gestorJugadores.ordenarTurnos();

		for (Jugador jug : gestorJugadores.getTurnoslist()) {
			for (Casillero casillero : gestorTablero.getTablero()
					.getCasillerosList()) {

				if (jug.getCasilleroActual().equals(casillero)) {
					casillero.addJugador(jug);
					break;
				}
			}
		}
	}
	
	public void avanzarDeCasillero(int key, Dado dados) throws Exception {
		// TODO Auto-generated method stub
		JugadorHumano jugador;
		Casillero casillero;
		
		jugador = gestorJugadores.getJugadorHumano(key);
		casillero = gestorTablero.moverAdelante(jugador, dados.getSuma(), true);
		
		//Debemos determinar que accion realiza sobre el casillero.
	}

//	private void sendToOne(int key, Object message) {
//		PartidasController.getInstance().getMonopolyGame()
//				.sendToOne(key, message);
//	}

	/**
	 * 
	 * Método que envía un determinado mensaje a todos los jugadores humanos del
	 * juego.
	 * 
	 * @param message
	 *            Objeto mensaje que recibirán los jugadores humanos.
	 */
	private void sendToAll(Object message) {
		for (int key : gestorJugadores.getIdConnectionClient()) {
			PartidasController.getInstance().getMonopolyGame()
					.sendToOne(key, message);
		}
	}

	/**
	 * 
	 * Método que envía un determinado mensaje a todos los jugadores humanos del
	 * juego excepto al que desencadena el mensaje.
	 * 
	 * @param message
	 *            Objeto mensaje que recibirán los jugadores humanos.
	 * @param senderId
	 *            Jugador que envía mensaje al resto de los participantes.
	 */
	private void sendToOther(Object message, int senderId) {
		for (int key : gestorJugadores.getIdConnectionClient()) {
			if (key != senderId)
				PartidasController.getInstance().getMonopolyGame()
						.sendToOne(key, message);
		}
	}

	
	
	// ============================= Getter & Setter
	// ==========================//

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public BancoController getGestorBanco() {
		return gestorBanco;
	}

	public TableroController getGestorTablero() {
		return gestorTablero;
	}

	public JugadorController getGestorJugadores() {
		return gestorJugadores;
	}

	public int getCantJugadores() {
		return cantJugadores;
	}

	public void setCantJugadores(int cantJugadores) {
		this.cantJugadores = cantJugadores;
	}

	public Estado getEstadoJuego() {
		return estadoJuego;
	}

	public void setEstadoJuego(Estado estadoJuego) {
		this.estadoJuego = estadoJuego;
	}

}
