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

	private TarjetaController gestorTarjetas;

	private FichasController gestorFichas;

	private TreeMap<Integer, JugadorHumano> networkPlayers;

	private List<Jugador> jugadoresList;

	private CircularList<Jugador> turnosList;

	private MonopolyGameStatus status;

	public JuegoController(Juego juego) {
		this.juego = juego;
		this.estadoJuego = new Estado();
		this.networkPlayers = new TreeMap<Integer, JugadorHumano>();
		this.turnosList = new CircularList<Jugador>();
		this.jugadoresList = new ArrayList<Jugador>();
		gestorTarjetas = new TarjetaController(this.juego);
		gestorFichas = new FichasController();
	}

	/**
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}

	/**
	 * @param juego
	 *            the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	/**
	 * @return the gestorTarjetas
	 */
	public TarjetaController getGestorTarjetas() {
		return gestorTarjetas;
	}

	/**
	 * @param gestorTarjetas
	 *            the gestorTarjetas to set
	 */
	public void setGestorTarjetas(TarjetaController gestorTarjetas) {
		this.gestorTarjetas = gestorTarjetas;
	}

	/**
	 * @return the gestorFichas
	 */
	public FichasController getGestorFichas() {
		return gestorFichas;
	}

	/**
	 * @param gestorFichas
	 *            the gestorFichas to set
	 */
	public void setGestorFichas(FichasController gestorFichas) {
		this.gestorFichas = gestorFichas;
	}

	/**
	 * 
	 */
	public void addPlayer(Jugador jugador) {
		JugadorHumano jh;

		if (jugador instanceof JugadorHumano) {
			jh = (JugadorHumano) jugador;
			if (!networkPlayers.containsKey(jh.getSenderID())) {
				networkPlayers.put(jh.getSenderID(), (JugadorHumano)jugador);
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
		JugadorHumano jugador = (JugadorHumano) networkPlayers
				.get(key);
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
		
		status = new MonopolyGameStatus(turnosList.toList(),
				MonopolyGameStatus.EMPEZAR, turnosList.toList().get(0));
		
		for(Map.Entry<Integer, JugadorHumano> entry : networkPlayers.entrySet()) {
			  int key = entry.getKey();

			  PartidasController.getInstance().getMonopolyGame()
				.sendToOne(key, status);
			}
	}

}
