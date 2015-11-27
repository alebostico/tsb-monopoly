package monopoly.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import monopoly.model.Dado;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.JugadorVirtual;
import monopoly.util.TurnoComparator;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.exception.IlegalJugadorException;
import monopoly.util.list.CircularList;
import monopoly.util.list.Node;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class JugadorController implements Serializable {

	private static final long serialVersionUID = -3807835839032676507L;

	private TreeMap<Integer, JugadorHumano> networkPlayers;

	private List<Jugador> jugadoresList;

	private CircularList<Integer> indexList;

	private Node<Integer> currentPlayer;

	public JugadorController() {
		super();
		this.networkPlayers = new TreeMap<Integer, JugadorHumano>();
		this.indexList = new CircularList<Integer>();
		this.jugadoresList = new ArrayList<Jugador>();
	}

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
		}

	}

	public void establecerTurnos() {
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

	public void ordenarTurnos() {
		Collections.sort(jugadoresList,
				Collections.reverseOrder(new TurnoComparator()));
		for (Jugador jugador : jugadoresList) {
			indexList.push_back(jugadoresList.indexOf(jugador));
		}
		currentPlayer = indexList.getHeader();
	}

	public Jugador siguienteTurno() {
		currentPlayer = currentPlayer.getNext();
		return jugadoresList.get(currentPlayer.getKey());
	}

	public int cantJugadoresConectados() {
		return this.jugadoresList.size();
	}

	public JugadorHumano getJugadorHumano(int key) {
		return networkPlayers.get(key);
	}

	public List<JugadorHumano> getListaJugadoresHumanos() {
		List<JugadorHumano> list = new ArrayList<JugadorHumano>();
		for (JugadorHumano jugadorHumano : networkPlayers.values()) {
			list.add(jugadorHumano);
		}
		return list;
	}

	public List<Jugador> getTurnoslist() {
		List<Jugador> turnos = new ArrayList<Jugador>();

		Node<Integer> aux = currentPlayer;

		do{
			turnos.add(jugadoresList.get(aux.getKey()));
			aux = aux.getNext();
		}
		while (currentPlayer != aux);

//		int index = 0;
//		int jugadorActual = jugadoresList.get(currentPlayer.getKey());
//
//		while (index < jugadoresList.size()) {
//			if (jugadorActual >= jugadoresList.size())
//				jugadorActual = 0;
//			turnos.add(jugadoresList.get(jugadorActual));
//			jugadorActual++;
//			index++;
//		}

		return turnos;
	}

	public Jugador getCurrentPlayer() {
		return jugadoresList.get(currentPlayer.getKey());
	}

	public int[] getIdConnectionClient() {

		int[] vIdConnection = new int[networkPlayers.size()];

		int index = 0;

		for (Map.Entry<Integer, JugadorHumano> entry : networkPlayers
				.entrySet()) {
			vIdConnection[index] = entry.getKey();

			index++;
		}
		return vIdConnection;
	}

	public boolean isJugadorVirtual(Jugador jugador) {
		return (jugador instanceof JugadorVirtual);
	}

	public boolean isJugadorVirtualElJugadorActual() {
		return isJugadorVirtual(getCurrentPlayer());
	}

	public int getSenderIdPlayer(Jugador jugador) throws IlegalJugadorException {
		if (!(jugador instanceof JugadorHumano))
			throw new IlegalJugadorException(
					String.format(
							"El Jugador %s no es un jugador controlado por una persona. ",
							jugador.getNombre()));
		return ((JugadorHumano) jugador).getSenderID();
	}

}
