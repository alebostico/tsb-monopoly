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
import monopoly.model.tarjetas.TarjetaSuerte;
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
					EstadoJuego.TIRAR_DADO, null,
					gestorJugadores.getCurrentPlayer(), historyList, null);
			sendToAll(status);

			if(gestorJugadores.getCurrentPlayer() instanceof JugadorVirtual)
			{
				tirarDadosJugadorVirtual();
			}
		}
	}
	
	public void tirarDadosJugadorVirtual(){
		
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

	public void avanzarDeCasillero(int senderId, Dado dados) throws Exception {
		// TODO Auto-generated method stub
		JugadorHumano jugador;
		Casillero casillero;
		boolean cobraSalida = true;
		AccionEnCasillero accion;
		EstadoJuego estadoJuegoJugadorActual= EstadoJuego.TIRAR_DADO;;
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
			accion.getAcciones()[1]= String.valueOf(((TarjetaSuerte)tarjetaSelected).getIdTarjeta());
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.TIRAR_DADO;
			break;
		case TARJETA_COMUNIDAD:
			tarjetaSelected = gestorTablero.getTarjetaComunidad();
			accion.getAcciones()[1]= String.valueOf(((TarjetaComunidad)tarjetaSelected).getIdTarjeta());
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.TIRAR_DADO;
			break;
		case DISPONIBLE_PARA_VENDER:
		case IMPUESTO:
		case IR_A_LA_CARCEL:
		case PAGAR_ALQUILER:
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.TIRAR_DADO;
			break;
		case DESCANSO:
		case HIPOTECADA:
		case MI_PROPIEDAD:
			estadoJuegoJugadorActual = Estado.EstadoJuego.TIRAR_DADO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.TIRAR_DADO;
		break;
		default:
			break;
		}

		mensaje = String.format("Avanzaste al casillero {0}, {1}",
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

		mensaje = String.format("Avanzó al casillero {1}.", casillero.getNombreCasillero());
		
		historyList = new ArrayList<History>();
		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));
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
	private void sendToOther(Object message, int senderId) {
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
