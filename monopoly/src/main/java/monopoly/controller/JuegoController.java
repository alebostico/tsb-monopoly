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
import monopoly.model.JugadorVirtual;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.MonopolyGameStatus.AccionEnCasillero;
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.StringUtils;
import monopoly.util.constantes.EnumAction;
import monopoly.util.exception.CondicionInvalidaException;
import monopoly.util.exception.SinDineroException;
import monopoly.util.message.game.CompleteTurnMessage;
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
		this.gestorTablero = new TableroController();
		this.gestorBanco = new BancoController(gestorTablero.getTablero()
				.getCasillerosList());
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
		jugador.setCasilleroActual(gestorTablero.getCasillero(1));

		History history = new History(StringUtils.getFechaActual(),
				jugador.getNombre(), "Se unió al juego.");
		HistoryGameMessage msg = new HistoryGameMessage(history);
		if (jugador instanceof JugadorHumano)
			sendToOther(((JugadorHumano) jugador).getSenderID(), msg);

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
	public void establecerTurnoJugador(int key, Dado dados) throws Exception {
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
					EstadoJuego.TIRAR_DADO, null,
					gestorJugadores.getCurrentPlayer(), historyList, null);
			sendToAll(status);

			if (gestorJugadores.getCurrentPlayer() instanceof JugadorVirtual) {
				tirarDadosJugadorVirtual();
			}
		}
	}

	public void tirarDadosJugadorVirtual() {

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

	public void avanzarDeCasillero(int senderId, Dado dados)
			throws CondicionInvalidaException, Exception {
		// TODO Auto-generated method stub
		Jugador jugador;
		Casillero casillero;
		boolean cobraSalida = true;
		AccionEnCasillero accion;
		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.TIRAR_DADO;
		EstadoJuego estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.TIRAR_DADO;
		MonopolyGameStatus status;
		Tarjeta tarjetaSelected = null;
		String mensaje;
		List<History> historyList = new ArrayList<History>();

		jugador = gestorJugadores.getJugadorHumano(senderId);
		casillero = gestorTablero.moverAdelante(jugador, dados.getSuma(),
				cobraSalida);

		accion = gestorTablero.getAccionEnCasillero(jugador, casillero,
				dados.getSuma());

		switch (accion) {
		case TARJETA_SUERTE:
			tarjetaSelected = gestorTablero.getTarjetaSuerte();
			accion.getAcciones()[1] = String
					.valueOf(((TarjetaSuerte) tarjetaSelected).getIdTarjeta());
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.ESPERANDO_TURNO;
			break;
		case TARJETA_COMUNIDAD:
			tarjetaSelected = gestorTablero.getTarjetaComunidad();
			accion.getAcciones()[1] = String
					.valueOf(((TarjetaComunidad) tarjetaSelected)
							.getIdTarjeta());
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.ESPERANDO_TURNO;
			break;
		case DISPONIBLE_PARA_VENDER:
		case IMPUESTO_DE_LUJO:
		case IR_A_LA_CARCEL:
		case PAGAR_ALQUILER:
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.ESPERANDO_TURNO;
			break;
		case DESCANSO:
		case HIPOTECADA:
		case MI_PROPIEDAD:
			estadoJuegoJugadorActual = Estado.EstadoJuego.ESPERANDO_TURNO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.TIRAR_DADO;
			break;
		default:
			throw new CondicionInvalidaException(String.format(
					"La acción %s es inválida.", accion.toString()));
		}

		mensaje = String.format("Avanzaste al casillero %s, %s",
				casillero.getNombreCasillero(), accion.getAcciones()[0]);

		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));
		if (estadoJuegoJugadorActual != EstadoJuego.JUGANDO)
			gestorJugadores.siguienteTurno();

		status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				estadoJuegoJugadorActual, accion,
				gestorJugadores.getCurrentPlayer(), historyList,
				tarjetaSelected);

		sendToOne(senderId, status);

		mensaje = String.format("Avanzó al casillero {1}.",
				casillero.getNombreCasillero());

		historyList = new ArrayList<History>();
		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));

		status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				estadoJuegoRestoJugadoresEstadoJuego, accion,
				gestorJugadores.getCurrentPlayer(), historyList,
				tarjetaSelected);

		sendToOther(senderId, status);
	}

	public void comprarPropiedad(int senderId, TarjetaPropiedad tarjeta)
			throws SinDineroException, Exception {
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		comprarPropiedad(senderId, jugador, tarjeta);
	}

	private void comprarPropiedad(int senderId, Jugador jugador,
			TarjetaPropiedad tarjeta) throws SinDineroException, Exception {
		MonopolyGameStatus status;
		History history;
		gestorTablero.comprarPropiedad(jugador, tarjeta);

		if (jugador instanceof JugadorHumano) {
			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.JUGANDO, null,
					gestorJugadores.getCurrentPlayer(),
					new ArrayList<History>(), null);
			sendToOne(
					senderId,
					new CompleteTurnMessage(String.format(
							"Ha adquirido la propiedad %s.",
							tarjeta.getNombre()), EnumAction.BUY_PROPERTY,
							status));
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), String.format(
							"Ha adquirido la propiedad %s.",
							tarjeta.getNombre()));
			sendToOther(senderId, new HistoryGameMessage(history));
		} else {
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), String.format(
							"Ha adquirido la propiedad %s.",
							tarjeta.getNombre()));
			sendToAll(new HistoryGameMessage(history));
		}
	}

	public void siguienteTurno() throws Exception {
		// TODO Auto-generated method stub
		History history;
		Jugador jugadorActual;
		Jugador jugadorSiguiente;
		MonopolyGameStatus status;
		List<History> historyList = new ArrayList<History>();

		jugadorActual = gestorJugadores.getCurrentPlayer();
		jugadorSiguiente = gestorJugadores.siguienteTurno();

		history = new History(StringUtils.getFechaActual(),
				jugadorActual.getNombre(), "Ha finalizado su turno.");
		historyList.add(history);
		history = new History(StringUtils.getFechaActual(),
				jugadorSiguiente.getNombre(), String.format(
						"Turno del jugador %s.", jugadorSiguiente.getNombre()));
		historyList.add(history);

		if (jugadorSiguiente instanceof JugadorVirtual) {
			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.ESPERANDO_TURNO, null,
					gestorJugadores.getCurrentPlayer(), historyList, null);
			sendToAll(status);

			tirarDadosJugadorVirtual();
		} else {
			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.TIRAR_DADO, null,
					gestorJugadores.getCurrentPlayer(), historyList, null);
			sendToOne(((JugadorHumano) jugadorSiguiente).getSenderID(), status);

			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.ESPERANDO_TURNO, null,
					gestorJugadores.getCurrentPlayer(), historyList, null);
			sendToOther(((JugadorHumano) jugadorSiguiente).getSenderID(),
					status);
		}

	}

	public void tarjetaSuerte(int senderId, TarjetaSuerte tarjeta)
			throws Exception {
		// TODO Auto-generated method stub
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		tarjetaSuerte(senderId, jugador,tarjeta);
	}

	private void tarjetaSuerte(int senderId, Jugador jugador,
			TarjetaSuerte tarjeta) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void tarjetaComunidad(int senderId, TarjetaComunidad tarjeta)
			throws Exception {
		// TODO Auto-generated method stub
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		tarjetaComunidad(senderId, jugador, tarjeta);
	}

	private void tarjetaComunidad(int senderId, Jugador jugador,
			TarjetaComunidad tarjeta) throws Exception {
		// TODO Auto-generated method stub
	}

	private void sendToOne(int recipientID, Object message) {
		PartidasController.getInstance().getMonopolyGame()
				.sendToOne(recipientID, message);
	}

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
	private void sendToOther(int senderId, Object message) {
		for (int key : gestorJugadores.getIdConnectionClient()) {
			if (key != senderId)
				PartidasController.getInstance().getMonopolyGame()
						.sendToOne(key, message);
		}
	}

	// ======================= Getter & Setter =======================//

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
