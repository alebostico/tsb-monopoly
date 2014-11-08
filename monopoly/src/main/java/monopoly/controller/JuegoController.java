/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import monopoly.model.Dado;
import monopoly.model.Estado;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.JugadorVirtual;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.list.CircularList;
import monopoly.util.list.Node;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class JuegoController {

	private Juego juego;

	private Estado estadoJuego;

	private int cantJugadores;

	private BancoController gestorBanco;

	private TableroController gestorTablero;

	private TreeMap<Integer, JugadorHumano> networkPlayers;

	private List<Jugador> jugadoresList;

	private CircularList<Jugador> turnosList;

	private Node<Jugador> currentPlayer;

	private MonopolyGameStatus status;

	public JuegoController(Usuario creador, String nombre) {
		this.gestorBanco = new BancoController();
		this.gestorTablero = new TableroController();
		this.juego = new Juego(creador, nombre);
		this.juego.setTablero(gestorTablero.getTablero());
		this.estadoJuego = new Estado();
		this.networkPlayers = new TreeMap<Integer, JugadorHumano>();
		this.turnosList = new CircularList<Jugador>();
		this.jugadoresList = new ArrayList<Jugador>();
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	/**
	 * 
	 */
	public void addPlayer(Jugador jugador) {
		JugadorHumano jh;

		if (jugador instanceof JugadorHumano) {
			jh = (JugadorHumano) jugador;
			if (!networkPlayers.containsKey(jh.getSenderID())) {
				networkPlayers.put(jh.getSenderID(), (JugadorHumano) jugador);
			}
		}

		if (!jugadoresList.contains(jugador)) {
			jugadoresList.add(jugador);
			turnosList.push_back(jugador);
		}

		if (jugadoresList.size() == cantJugadores) {
			estadoJuego.actualizarEstadoJuego();
			establecerTurnos();
		}

	}

	private void establecerTurnos() {
		for (Jugador jugador : jugadoresList) {
			if (jugador instanceof JugadorVirtual)
				jugador.setTiradaInicial(new Dado());
			else {
				int recipientID = ((JugadorHumano) jugador).getSenderID();
				PartidasController
						.getInstance()
						.getMonopolyGame()
						.sendToOne(recipientID,
								ConstantesMensaje.THROW_DICE_TURNS_MESSAGE);
			}
		}
	}

	/**
	 * @return the gestorBanco
	 */
	public BancoController getGestorBanco() {
		return gestorBanco;
	}

	/**
	 * @return the gestorTablero
	 */
	public TableroController getGestorTablero() {
		return gestorTablero;
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
	 * @return the jugadores
	 */
	public TreeMap<Integer, JugadorHumano> getJugadores() {
		return networkPlayers;
	}

	/**
	 * @param jugadores
	 *            the jugadores to set
	 */
	public void setJugadores(TreeMap<Integer, JugadorHumano> jugadores) {
		this.networkPlayers = jugadores;
	}

	/**
	 * @return the turnosList
	 */
	public CircularList<Jugador> getTurnosList() {
		return turnosList;
	}

	/**
	 * @param turnosList
	 *            the turnosList to set
	 */
	public void setTurnosList(CircularList<Jugador> turnosList) {
		this.turnosList = turnosList;
	}

	/**
	 * @return the estadoJuego
	 */
	public Estado getEstadoJuego() {
		return estadoJuego;
	}

	/**
	 * @param estadoJuego
	 *            the estadoJuego to set
	 */
	public void setEstadoJuego(Estado estadoJuego) {
		this.estadoJuego = estadoJuego;
	}

	public void startGame(int key, Dado dados) {
		// TODO Auto-generated method stub
		JugadorHumano jugador = (JugadorHumano) networkPlayers.get(key);
		jugador.setTiradaInicial(dados);
		boolean tiraronTodosDados = true;
		for (Jugador j : networkPlayers.values()) {
			if (j.getTiradaInicial() == null) {
				tiraronTodosDados = false;
				break;
			}
		}
		if (tiraronTodosDados) {
			estadoJuego.actualizarEstadoJuego();
			ordenarTurnos();
			// quien es el primero?
		}
	}

	private void ordenarTurnos() {

		Node<Jugador> primero = (Node<Jugador>) turnosList.getHeader();
		Node<Jugador> tmp = null;
		Jugador aux;

		do {
			tmp = primero.getNext();
			while (tmp != (Node<Jugador>) turnosList.getHeader()) {
				if (primero.getKey().getTiradaInicial().getSuma() < tmp
						.getKey().getTiradaInicial().getSuma()) {
					aux = primero.getKey();
					primero.setKey(tmp.getKey());
					tmp.setKey(aux);

				}
				tmp = tmp.getNext();
			}
			primero = primero.getNext();

		} while (tmp != (Node<Jugador>) turnosList.getHeader());

		for (Jugador jug : turnosList.toList()) {
			for (Casillero casillero : gestorTablero.getTablero()
					.getCasillerosList()) {

				if (jug.getCasilleroActual().equals(casillero)) {
					casillero.addJugador(jug);
					break;
				}
			}
		}
		
		currentPlayer = (Node<Jugador>) turnosList.getHeader();

		status = new MonopolyGameStatus(turnosList.toList(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				MonopolyGameStatus.EMPEZAR, currentPlayer.getKey());

		for (Map.Entry<Integer, JugadorHumano> entry : networkPlayers
				.entrySet()) {
			int key = entry.getKey();

			PartidasController.getInstance().getMonopolyGame()
					.sendToOne(key, status);
		}
	}

}
