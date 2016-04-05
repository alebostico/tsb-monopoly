package monopoly.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import monopoly.dao.IJuegoDao;
import monopoly.model.AccionEnCasillero;
import monopoly.model.AccionEnTarjeta;
import monopoly.model.Dado;
import monopoly.model.Estado;
import monopoly.model.Estado.EstadoJuego;
import monopoly.model.History;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.JugadorVirtual;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.SubastaStatus;
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.constantes.EnumAction;
import monopoly.util.constantes.EnumEstadoSubasta;
import monopoly.util.constantes.EnumSalidaCarcel;
import monopoly.util.constantes.EnumsTipoImpuesto;
import monopoly.util.exception.CondicionInvalidaException;
import monopoly.util.exception.SinDineroException;
import monopoly.util.exception.SinEdificiosException;
import monopoly.util.message.ExceptionMessage;
import monopoly.util.message.game.BidForPropertyMessage;
import monopoly.util.message.game.BidResultMessage;
import monopoly.util.message.game.ChatGameMessage;
import monopoly.util.message.game.CompleteTurnMessage;
import monopoly.util.message.game.HistoryGameMessage;
import monopoly.util.message.game.ReloadSavedGameMessage;
import monopoly.util.message.game.actions.AuctionDecideMessage;
import monopoly.util.message.game.actions.AuctionFinishMessage;
import monopoly.util.message.game.actions.AuctionNotifyMessage;
import monopoly.util.message.game.actions.AuctionPropertyMessage;
import monopoly.util.message.game.actions.PayToPlayerMessage;

import org.apache.commons.lang.mutable.MutableBoolean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class JuegoController implements Serializable {

	private static final long serialVersionUID = 7433262560591847582L;

	static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"spring/config/BeanLocations.xml");

	private Juego juego;

	private Estado estadoJuego;

	private int cantJugadores;

	private BancoController gestorBanco;

	private TableroController gestorTablero;

	private JugadorController gestorJugadores;

	private JugadorVirtualController gestorJugadoresVirtuales;

	private MonopolyGameStatus status;

	private SubastaController gestorSubasta;

	private int contadorPagos;

	public JuegoController(Usuario creador, String nombre) {
		this.gestorTablero = new TableroController();
		this.gestorBanco = new BancoController(gestorTablero.getTablero()
				.getCasillerosList());
		this.juego = new Juego(creador, nombre);
		this.juego.setTablero(gestorTablero.getTablero());
		this.estadoJuego = new Estado(EstadoJuego.CREADO);
		this.gestorJugadores = new JugadorController();
		this.gestorJugadoresVirtuales = new JugadorVirtualController();
		this.contadorPagos = 0;
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
	public void addPlayer(Jugador jugador) throws Exception {
		this.gestorJugadores.addPlayer(jugador);
		this.juego.addJugador(jugador);
		jugador.setCasilleroActual(gestorTablero.getCasillero(1));

		History history = new History(StringUtils.getFechaActual(),
				jugador.getNombre(), "Se unió al juego.");
		HistoryGameMessage msg = new HistoryGameMessage(history);
		if (jugador.isHumano())
			sendToOther(((JugadorHumano) jugador).getSenderID(), msg);

		if (this.gestorJugadores.cantJugadoresConectados() == cantJugadores) {
			estadoJuego.actualizarEstadoJuego();
			gestorJugadores.establecerTurnos();
		}
	}

	/**
	 * Restaura un juego serializado.
	 * 
	 * @throws IOException
	 *             Si no se encuentra el juego.
	 */
	public void reloadGame(int senderID) throws Exception {

		for (JugadorHumano jugador : this.gestorJugadores
				.getListaJugadoresHumanos()) {
			jugador.setSenderID(senderID);
			this.gestorJugadores.cleanNetworkPlayers();
			this.gestorJugadores.addNetworkPlayer(jugador);
		}

		List<History> historia = new ArrayList<History>();
		historia.add(new History(StringUtils.getFechaActual(), gestorJugadores
				.getCurrentPlayer().getNombre(), "Restauró el juego."));
		MonopolyGameStatus status = new MonopolyGameStatus(
				gestorJugadores.getTurnoslist(), gestorBanco.getBanco(),
				gestorTablero.getTablero(), EstadoJuego.TIRAR_DADO, null,
				gestorJugadores.getCurrentPlayer(), historia, null);
		sendToOne(senderID,
				new ReloadSavedGameMessage(senderID, this.getJuego(), status));
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
		JugadorHumano jugador = gestorJugadores.getJugadorHumano(key);
		jugador.setTiradaInicial(dados);
		boolean tiraronTodosDados = true;
		History history;

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
			for (int i = 0; i < gestorJugadores.getTurnoslist().size(); i++) {
				history = new History(StringUtils.getFechaActual(),
						gestorJugadores.getTurnoslist().get(i).getNombre(),
						(i + 1)
								+ "°"
								+ " Orden en la ronda. Suma de dados: "
								+ gestorJugadores.getTurnoslist().get(i)
										.getTiradaInicial().getSuma());
				historyList.add(history);
			}

			history = new History(StringUtils.getFechaActual(), gestorJugadores
					.getCurrentPlayer().getNombre(),
					"Turno para tirar los dados.");
			historyList.add(history);

			if (gestorJugadores.getCurrentPlayer().isVirtual()) {
				status = new MonopolyGameStatus(
						gestorJugadores.getTurnoslist(),
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						EstadoJuego.ESPERANDO_TURNO, null,
						gestorJugadores.getCurrentPlayer(), historyList, null);
				sendToAll(status);
				avanzarDeCasilleroJV();
			} else {
				JugadorHumano jh = (JugadorHumano) gestorJugadores
						.getCurrentPlayer();
				status = new MonopolyGameStatus(
						gestorJugadores.getTurnoslist(),
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						EstadoJuego.TIRAR_DADO, null,
						gestorJugadores.getCurrentPlayer(), historyList, null);
				sendToOne(jh.getSenderID(), status);

				status = new MonopolyGameStatus(
						gestorJugadores.getTurnoslist(),
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						EstadoJuego.ESPERANDO_TURNO, null,
						gestorJugadores.getCurrentPlayer(), historyList, null);
				sendToOther(jh.getSenderID(), status);
			}

		}
	}

	/**
	 * 
	 * Método para ordenar la lista cicular de jugadores para establecer el
	 * turno de los mismos en base a los resultados de los dados arrojados. Una
	 * vez ordenados informa a los jugadores el orden establecido.
	 */
	private void ordenarTurnos() throws Exception {
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

	/**
	 * Método para avanzar de casillero en base al suma de datos que arrojó el
	 * jugador.
	 * 
	 * @param senderId
	 *            Id de conexión de un jugador humano.
	 * @param dados
	 *            dados arrojados en la tirada.
	 * 
	 * @throws CondicionInvalidaException
	 * @throws Exception
	 */
	public void avanzarDeCasillero(int senderId, Dado dados)
			throws CondicionInvalidaException, Exception {
		Jugador jugador;
		Casillero casillero;
		MutableBoolean cobraSalida = new MutableBoolean(true);
		AccionEnCasillero accion;

		jugador = gestorJugadores.getJugadorHumano(senderId);
		jugador.setUltimoResultado(dados);
		casillero = gestorTablero.moverAdelante(jugador, dados.getSuma(),
				cobraSalida);

		if (cobraSalida.booleanValue())
			gestorBanco.pagarPasoSalida(jugador);

		accion = gestorTablero.getAccionEnCasillero(jugador, casillero);

		jugarAccionCasillero(accion, jugador, casillero, senderId);
	}

	/**
	 * Método utilizado para realizar la acción correspondiente al casillero en
	 * que un jugador humano cayó.
	 * 
	 * @param accion
	 *            Acción a realizar según el casillero que cayó.
	 * @param jugador
	 *            a realizar la acción.
	 * @param casillero
	 *            al que se avanzó.
	 * @param senderId
	 *            Id de conexión de un jugador humano.
	 * 
	 * @throws CondicionInvalidaException
	 * @throws SinDineroException
	 * @throws Exception
	 */
	private void jugarAccionCasillero(AccionEnCasillero accion,
			Jugador jugador, Casillero casillero, int senderId)
			throws CondicionInvalidaException, SinDineroException, Exception {

		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.JUGANDO;
		EstadoJuego estadoJuegoRestoJugadores = EstadoJuego.ESPERANDO_TURNO;
		MonopolyGameStatus status;
		Tarjeta tarjetaSelected = null;
		String mensaje;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();

		switch (accion) {
		case TARJETA_SUERTE:
			tarjetaSelected = gestorTablero.getTarjetaSuerte();
			accion.setMonto(((TarjetaSuerte) tarjetaSelected).getIdTarjeta());
			break;
		case TARJETA_COMUNIDAD:
			tarjetaSelected = gestorTablero.getTarjetaComunidad();
			accion.setMonto(((TarjetaComunidad) tarjetaSelected).getIdTarjeta());
			break;
		case DISPONIBLE_PARA_VENDER:
		case PAGAR_ALQUILER:
		case IMPUESTO_DE_LUJO:
		case IMPUESTO_SOBRE_CAPITAL:
		case IR_A_LA_CARCEL:
		case DESCANSO:
		case HIPOTECADA:
		case MI_PROPIEDAD:
			break;

		default:
			throw new CondicionInvalidaException(String.format(
					"La acción %s es inválida.", accion.toString()));
		}

		mensaje = String.format("Avanzó al casillero %s, %s",
				casillero.getNombreCasillero(), accion.getMensaje());

		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));

		turnosList = gestorJugadores.getTurnoslist();
		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoJugadorActual, accion,
				gestorJugadores.getCurrentPlayer(), historyList,
				tarjetaSelected);
		status.setMensajeAux(accion.getMensaje());
		status.setMonto(accion.getMonto());
		sendToOne(senderId, status);

		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoRestoJugadores, accion,
				gestorJugadores.getCurrentPlayer(), historyList,
				tarjetaSelected);
		sendToOther(senderId, status);
	}

	/**
	 * Método utilizado por un jugador virtual para avanzar de casillero.
	 * 
	 * @throws Exception
	 */
	public void avanzarDeCasilleroJV() throws Exception {
		AccionEnCasillero accion;
		MonopolyGameStatus status;
		Tarjeta tarjeta;
		Dado dados = null;
		Casillero casillero;
		History history;
		List<History> historyList;
		JugadorVirtual jugadorActual = (JugadorVirtual) this.gestorJugadores
				.getCurrentPlayer();
		String mensaje = "";
		MutableBoolean cobraSalida = new MutableBoolean(true);

		mensaje = gestorJugadoresVirtuales.deshipotecarAleatorio(jugadorActual);

		if (!StringUtils.IsNullOrEmpty(mensaje)) {
			sendToAll(new HistoryGameMessage(new History(
					StringUtils.getFechaActual(), gestorJugadores
							.getCurrentPlayer().getNombre(), mensaje)));
		}

		mensaje = "";

		try {
			mensaje = gestorJugadoresVirtuales
					.construirAleatorio(jugadorActual);

		} catch (SinEdificiosException e) {
			mensaje = "No pudo comprar edificios porque no tiene disponibilidad en el banco";
		} catch (SinDineroException e) {
			mensaje = "No pudo comprar edificios porque no tiene dinero suficiente";
		}

		if (!StringUtils.IsNullOrEmpty(mensaje)) {
			sendToAll(new HistoryGameMessage(new History(
					StringUtils.getFechaActual(), gestorJugadores
							.getCurrentPlayer().getNombre(), mensaje)));
		}

		try {
			// ~~~> pregunto si el jugador está preso.
			if (jugadorActual.estaPreso()) {
				// ~~~> Si el jugador está preso pregunto si decide salir con
				// una tarjeta.
				if (jugadorActual.getTarjetaCarcelList().size() > 0
						&& gestorJugadoresVirtuales
								.decidirSalirTarjeta(jugadorActual)) {
					tarjeta = jugadorActual.getTarjetaCarcelList().get(0);
					jugadorActual.getTarjetaCarcelList().remove(tarjeta);
					gestorTablero.getGestorTarjetas()
							.agregarTarjetaLibreDeCarcel(tarjeta);
					mensaje = String
							.format("Usó una tarjeta de la %s para salir de la cárcel.",
									tarjeta.isTarjetaSuerte() ? "Suerte"
											: "Caja de la Comunidad");
				} else {
					// ~~~> Si el jugador no decide usar la tarjeta por no
					// poseer una o no le conviene.
					// ~~~> Pregunto si quiere salir de la cárcel pagando.
					if (gestorJugadoresVirtuales
							.decidirSalirPagando(jugadorActual)) {
						jugadorActual.pagar(50);
						jugadorActual.setPreso(false);
						mensaje = String.format(
								"Pagó %s para salir de la cárcel.",
								StringUtils.formatearAMoneda(50));
					} else {
						dados = new Dado();
						tirarDadosDoblesSalirCarcel(jugadorActual, dados);
						return;
					}
				}
			}

			if (!StringUtils.IsNullOrEmpty(mensaje)) {
				history = new History(StringUtils.getFechaActual(),
						jugadorActual.getNombre(), mensaje);
				sendToAll(new HistoryGameMessage(history));
			}

			dados = new Dado();
			jugadorActual.setUltimoResultado(dados);

			casillero = gestorTablero.moverAdelante(jugadorActual,
					dados.getSuma(), cobraSalida);

			if (cobraSalida.booleanValue())
				gestorBanco.pagarPasoSalida(jugadorActual);

			mensaje = String.format(
					"Avanzó %s casilleros. Se encuentra en el casillero %s.",
					dados.getSuma(), casillero.getNombreCasillero());

			history = new History(StringUtils.getFechaActual(),
					jugadorActual.getNombre(), mensaje);
			historyList = new ArrayList<History>();
			historyList.add(history);

			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.ESPERANDO_TURNO, null, jugadorActual,
					historyList, null);
			sendToAll(status);

			accion = gestorTablero.getAccionEnCasillero(jugadorActual,
					casillero);

			sendToAll(new HistoryGameMessage(new History(
					StringUtils.getFechaActual(), jugadorActual.getNombre(),
					accion.getMensaje())));

			jugarAccionEnCasilleroJV(accion, jugadorActual, casillero);
			siguienteTurno(true);

		} catch (CondicionInvalidaException | SinDineroException e) {
			/*
			 * "SinDineroException" no debería generarse nunca para un
			 * JugadorVirtual (como sería en este caso), pero como el método
			 * "gestorTablero.comprarPropiedad()" es genérico para Jugador,
			 * entonces tenemos que "catchar" la excepción.
			 */
			GestorLogs.registrarError(e);
			e.printStackTrace();
		} catch (Exception e) {
			GestorLogs.registrarError(e);
			e.printStackTrace();
		}
	}

	/**
	 * Método utilizado para realizar la acción correspondiente al casillero en
	 * que un jugador cayó.
	 * 
	 * @param accion
	 *            accion Acción a realizar según el casillero que cayó.
	 * @param jugador
	 *            jugador jugador a realizar la acción.
	 * @param casillero
	 *            al que se avanzó.
	 * 
	 * @throws CondicionInvalidaException
	 * @throws SinDineroException
	 * @throws Exception
	 */
	private void jugarAccionEnCasilleroJV(AccionEnCasillero accion,
			JugadorVirtual jugador, Casillero casillero)
			throws CondicionInvalidaException, SinDineroException, Exception {

		Tarjeta tarjetaSelected = null;
		TarjetaPropiedad tarjetaPropiedad = null;
		CasilleroCalle casilleroCalle;
		CasilleroEstacion casilleroEstacion;
		CasilleroCompania casilleroCompania;
		Jugador duenio;
		String mensaje;
		int montoAPagar;

		switch (accion) {
		case TARJETA_SUERTE:
			tarjetaSelected = gestorTablero.getTarjetaSuerte();
			realizarObjetivoTarjeta(jugador, tarjetaSelected);
			break;
		case TARJETA_COMUNIDAD:
			tarjetaSelected = gestorTablero.getTarjetaComunidad();
			realizarObjetivoTarjeta(jugador, tarjetaSelected);
			break;
		case DISPONIBLE_PARA_VENDER:
			if (gestorJugadoresVirtuales.decidirComprar(casillero, jugador)) {
				switch (casillero.getTipoCasillero()) {
				case C_CALLE:
					tarjetaPropiedad = ((CasilleroCalle) casillero)
							.getTarjetaCalle();
					break;
				case C_ESTACION:
					tarjetaPropiedad = ((CasilleroEstacion) casillero)
							.getTarjetaEstacion();
					break;
				case C_COMPANIA:
					tarjetaPropiedad = ((CasilleroCompania) casillero)
							.getTarjetaCompania();
					break;
				default:
					break;
				}

				comprarPropiedad(jugador, tarjetaPropiedad);
			} else {
				mensaje = String.format("El Jugador %s decidió no comprar %s.",
						jugador.getNombre(), casillero.getNombreCasillero());
				sendToAll(new HistoryGameMessage(new History(
						StringUtils.getFechaActual(), jugador.getNombre(),
						mensaje)));
			}
			break;
		case IMPUESTO_DE_LUJO:
			jugador.pagar(100);
			break;
		case IMPUESTO_SOBRE_CAPITAL:
			jugador.pagar(gestorJugadoresVirtuales
					.decidirImpuestoEspecial(jugador));
			break;
		case IR_A_LA_CARCEL:
			gestorTablero.irACarcel(jugador);
			break;
		case PAGAR_ALQUILER:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				casilleroCalle = (CasilleroCalle) casillero;
				tarjetaPropiedad = casilleroCalle.getTarjetaCalle();
				montoAPagar = casilleroCalle.getTarjetaCalle()
						.calcularAlquiler();
				break;
			case C_ESTACION:
				casilleroEstacion = (CasilleroEstacion) casillero;
				tarjetaPropiedad = casilleroEstacion.getTarjetaEstacion();
				montoAPagar = casilleroEstacion.getTarjetaEstacion()
						.calcularAlquiler();
				break;
			case C_COMPANIA:
				casilleroCompania = (CasilleroCompania) casillero;
				tarjetaPropiedad = casilleroCompania.getTarjetaCompania();
				montoAPagar = casilleroCompania.getTarjetaCompania()
						.calcularAlquiler(jugador.getUltimoResultado());
				break;
			default:
				montoAPagar = 0;
				break;
			}
			duenio = tarjetaPropiedad.getJugador();

			gestorJugadoresVirtuales
					.pagarAJugador(jugador, duenio, montoAPagar);

			break;
		case DESCANSO:
		case HIPOTECADA:
		case MI_PROPIEDAD:
			break;
		default:
			throw new CondicionInvalidaException(String.format(
					"La acción %s es inválida.", accion.toString()));
		}
	}

	/**
	 * Método para determinar el siguiente turno de un jugador.
	 * 
	 * @param validaDadosDobles
	 * @throws Exception
	 */
	public void siguienteTurno(boolean validaDadosDobles) throws Exception {
		History history;
		Jugador jugadorActual;
		Jugador jugadorSiguiente;
		MonopolyGameStatus gameStatus;
		int senderId;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();
		EstadoJuego estadoJuego;

		jugadorActual = gestorJugadores.getCurrentPlayer();

		// ~~> Válida q haya sacado dobles y si está habilitado
		// para tirar de nuevo en caso q sean dobles.
		if (validaDadosDobles && jugadorActual.tiroDobles()) {

			// ~~~> Sacó dobles, incremento el contador.
			jugadorActual.incrementarCantidadDadosDobles();

			// ~~~> Si es la tercera vez q saca dobles, va a la cárcel.
			if (jugadorActual.getCatidadDadosDobles() < 3) {
				history = new History(StringUtils.getFechaActual(),
						jugadorActual.getNombre(), String.format(
								"Juega otro turno por sacar dados dobles %s",
								jugadorActual.getParDados()));
				historyList.add(history);

				// ~~~> Envío los mensajes a todos los clientes
				if (jugadorActual.isVirtual()) {
					gameStatus = new MonopolyGameStatus(
							gestorJugadores.getTurnoslist(),
							gestorBanco.getBanco(), gestorTablero.getTablero(),
							EstadoJuego.ESPERANDO_TURNO, null, jugadorActual,
							historyList, null);
					sendToAll(gameStatus);
					avanzarDeCasilleroJV();
				} else {
					senderId = ((JugadorHumano) jugadorActual).getSenderID();
					gameStatus = new MonopolyGameStatus(
							gestorJugadores.getTurnoslist(),
							gestorBanco.getBanco(), gestorTablero.getTablero(),
							EstadoJuego.DADOS_DOBLES, null, jugadorActual,
							historyList, null);
					sendToOne(senderId, gameStatus);

					gameStatus = new MonopolyGameStatus(
							gestorJugadores.getTurnoslist(),
							gestorBanco.getBanco(), gestorTablero.getTablero(),
							EstadoJuego.ESPERANDO_TURNO, null, jugadorActual,
							historyList, null);
					sendToOther(senderId, gameStatus);
				}
			} else {
				jugadorActual.resetCatidadDadosDobles();
				if (jugadorActual.isHumano()) {
					senderId = ((JugadorHumano) jugadorActual).getSenderID();
					irALaCarcel(senderId);
				} else {
					irALaCarcel(jugadorActual);
				}
			}
		} else {
			jugadorActual.resetCatidadDadosDobles();
			jugadorSiguiente = gestorJugadores.siguienteTurno();

			history = new History(StringUtils.getFechaActual(),
					jugadorActual.getNombre(), "Finalizó su turno.");
			historyList.add(history);

			// ~~> Historia del siguiente turno
			history = new History(StringUtils.getFechaActual(),
					jugadorSiguiente.getNombre(), String.format(
							"Turno del jugador %s.",
							jugadorSiguiente.getNombre()));
			historyList.add(history);

			turnosList = gestorJugadores.getTurnoslist();

			// ~~~> Pregunto si es virtual. Si lo es envío un sendToAll
			// a todos los jugadores humanos. Si no, un mensaje al jugador
			// que le tocó el turno y otro mensaje al resto.
			if (jugadorSiguiente.isVirtual()) {
				status = new MonopolyGameStatus(turnosList,
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						EstadoJuego.ESPERANDO_TURNO, null,
						gestorJugadores.getCurrentPlayer(), historyList, null);
				sendToAll(status);

				avanzarDeCasilleroJV();
			} else {
				estadoJuego = EstadoJuego.TIRAR_DADO;
				// ~~~> Pregunto si está preso. Si lo está le cambio el estado
				// a preso.
				if (gestorJugadores.getCurrentPlayer().estaPreso())
					estadoJuego = EstadoJuego.PRESO;

				status = new MonopolyGameStatus(turnosList,
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						estadoJuego, null, gestorJugadores.getCurrentPlayer(),
						historyList, null);
				sendToOne(((JugadorHumano) jugadorSiguiente).getSenderID(),
						status);

				status = new MonopolyGameStatus(turnosList,
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						EstadoJuego.ESPERANDO_TURNO, null,
						gestorJugadores.getCurrentPlayer(), historyList, null);
				sendToOther(((JugadorHumano) jugadorSiguiente).getSenderID(),
						status);
			}
		}
	}

	/**
	 * Recibe una oferta de un jugador para comprar una propiedad de otro
	 * jugador. Si el Jugador que recibe la oferta es un JugadorVirtual,
	 * verifica si este la acepta y transfiere la propiedad si corresponde. Si
	 * es un JugadorHumano, le envía un mensaje con la oferta.
	 * 
	 * @param senderId
	 *            El jugador que hizo la oferta
	 * @param propiedad
	 *            La propiedad que quiere comprar
	 * @param oferta
	 *            El monto ofrecido por la propiedad
	 * @throws Exception
	 */
	public void ofrecerPorPropiedad(JugadorHumano comprador,
			TarjetaPropiedad propiedad, int oferta) throws Exception {

		Jugador dueno = propiedad.getJugador();
		boolean compra;

		if (dueno.isVirtual()) {
			JugadorVirtual jugadorVirtual = (JugadorVirtual) dueno;
			compra = gestorJugadoresVirtuales.decidirVenderPropiedad(propiedad,
					oferta, comprador, jugadorVirtual);

			this.transferirPropiedad(propiedad, comprador, oferta, compra);

		} else {
			BidForPropertyMessage bidMessage = new BidForPropertyMessage(
					comprador, juego.getUniqueID(), propiedad, oferta);
			sendToOne(((JugadorHumano) dueno).getSenderID(), bidMessage);
		}

	}

	/**
	 * Determina si un JugadorHumano acepta una oferta por una propiedad. En
	 * caso de que haya aceptado, realiza la transferencia de la propiedad.
	 * 
	 * @param comprador
	 *            El jugador que compra la propiedad
	 * @param propiedad
	 *            La propiedad que se transfiere
	 * @param oferta
	 *            El monto de la oferta
	 * @param resultado
	 *            {@code true} si el jugador aceptó la oferta
	 * @return {@code true} si el jugador aceptó la oferta
	 * @throws Exception
	 */
	public boolean terminarOfertaPorPropiedad(JugadorHumano comprador,
			TarjetaPropiedad propiedad, int oferta, boolean resultado)
			throws Exception {

		this.transferirPropiedad(propiedad, comprador, oferta, resultado);

		return resultado;
	}

	/**
	 * Realiza la transferencia de una propiedad de un jugador a otro
	 * 
	 * @param propiedad
	 *            La propiedad que se va a transferir
	 * @param comprador
	 *            El jugador que compra la propiedad
	 * @param monto
	 *            El monto por el cual se compra la propiedad
	 * @param compra
	 *            {@code true} si el jugador aceptó la oferta
	 * @throws Exception
	 */
	private void transferirPropiedad(TarjetaPropiedad propiedad,
			JugadorHumano comprador, int monto, boolean compra)
			throws Exception {

		String mensaje;
		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.ACTUALIZANDO_ESTADO;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();
		Jugador oldDueno = propiedad.getJugador();
		JugadorHumano newDueno = comprador;

		if (compra) {

			for (Jugador jugador : this.gestorJugadores.getTurnoslist()) {
				if (jugador.getNombre().equals(comprador.getNombre()))
					newDueno = (JugadorHumano) jugador;
				else if (jugador.getNombre().equals(
						propiedad.getJugador().getNombre()))
					oldDueno = jugador;
			}

			// Transferimos la propiedad y el dinero....
			gestorTablero.transferirPropiedad(
					gestorTablero.getTarjetaPropiedad(propiedad), newDueno,
					oldDueno, monto);

			// Enviar mensajes informado
			mensaje = String.format(
					"Le compró la propiedad %s al jugador %s por %s",
					propiedad.getNombre(), oldDueno.getNombre(),
					StringUtils.formatearAMoneda(monto));

		} else {
			mensaje = String.format(
					"No le compró la propiedad %s al jugador %s "
							+ "porque no aceptó la oferta de %s",
					propiedad.getNombre(), oldDueno.getNombre(),
					StringUtils.formatearAMoneda(monto));
		}

		historyList.add(new History(StringUtils.getFechaActual(), comprador
				.getNombre(), mensaje));

		turnosList = gestorJugadores.getTurnoslist();
		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoJugadorActual, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);
		sendToAll(status);

		// le informamos al comprador el resultado de la operacion...
		BidResultMessage bidMsg = new BidResultMessage(comprador,
				juego.getUniqueID(), propiedad, monto, compra);

		sendToOne(comprador.getSenderID(), bidMsg);
	}

	/**
	 * Método solicitado por un jugador humano para comprar una propiedad.
	 * 
	 * @param senderId
	 *            id de conexión de un jugador humano.
	 * @param nombrePropiedad
	 *            que se comprará.
	 * 
	 * @throws SinDineroException
	 * @throws Exception
	 */
	public void comprarPropiedad(int senderId, String nombrePropiedad)
			throws SinDineroException, Exception {
		TarjetaPropiedad tarjeta = gestorBanco.getBanco().getTarjetaPropiedad(
				nombrePropiedad);
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		comprarPropiedad(jugador, tarjeta);
	}

	/**
	 * Método para comprar una propiedad.
	 * 
	 * @param jugador
	 *            que realizará la compra.
	 * @param tarjeta
	 *            propiedad que se comprará.
	 * 
	 * @throws SinDineroException
	 * @throws Exception
	 */
	private void comprarPropiedad(Jugador jugador, TarjetaPropiedad tarjeta)
			throws SinDineroException, Exception {
		History history;
		int senderId = 0;
		gestorTablero.comprarPropiedad(jugador, tarjeta);

		if (jugador.isHumano()) {
			senderId = ((JugadorHumano) jugador).getSenderID();
			status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.JUGANDO, null,
					gestorJugadores.getCurrentPlayer(),
					new ArrayList<History>(), null);
			sendToOne(
					senderId,
					new CompleteTurnMessage(String.format(
							"Adquirió la propiedad %s.", tarjeta.getNombre()),
							EnumAction.BUY_PROPERTY, status));
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), String.format(
							"Adquirió la propiedad %s.", tarjeta.getNombre()));
			sendToOther(senderId, new HistoryGameMessage(history));
		} else {
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), String.format(
							"Adquirió la propiedad %s.", tarjeta.getNombre()));
			sendToAll(new HistoryGameMessage(history));
		}

		if (jugador.isHumano())
			siguienteTurno(true);
	}

	/**
	 * Método para adquirir una propiedad a un determinado monto.
	 * 
	 * @param jugadorComprador
	 *            , jugador que va a adquirir la propiedad.
	 * @param jugadorVendedor
	 *            , jugadpr que vende la propiedad. <code>null</code> si el
	 *            vendedor es el banco.
	 * @param tarjeta
	 *            , propiedad a adquirir.
	 * @param monto
	 *            , monto de la propiedad.
	 */
	private void adquirirPropiedad(Jugador jugadorComprador,
			Jugador jugadorVendedor, TarjetaPropiedad tarjeta, int monto)
			throws SinDineroException {
		if (jugadorVendedor == null) {
			tarjeta = gestorBanco.getBanco().getTarjetaPropiedad(tarjeta);
			gestorBanco.adquirirPropiedad(jugadorComprador, tarjeta, monto);
		} else {
			tarjeta = jugadorVendedor.getPropiedad(tarjeta);
			jugadorVendedor.venderPropiedad(tarjeta, jugadorComprador, monto);
		}
	}

	/**
	 * Hipoteca una propiedad y le paga el monto de la hipteca a su dueño.
	 * 
	 * @param propiedad
	 *            La propiedad que se va a hipotecar
	 * @return La {@code propiedad} hipotecada si se hipotecó. {@code null} si
	 *         no se pudo hipotecar. Ver
	 *         {@link TableroController#hipotecarPropiedad(TarjetaPropiedad, Jugador)}
	 *         .
	 * @throws Exception
	 *             Si no se puede enviar el mensaje al cliente.
	 */
	public TarjetaPropiedad hipotecarPropiedad(TarjetaPropiedad propiedad)
			throws Exception {

		Jugador jugador = gestorJugadores.getCurrentPlayer();
		TarjetaPropiedad propiedadToReturn = gestorTablero.hipotecarPropiedad(
				propiedad, jugador);

		String mensaje;
		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.ACTUALIZANDO_ESTADO;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();

		if (propiedadToReturn != null)
			mensaje = String.format("Hipotecó la propiedad %s y cobró %s.",
					propiedad.getNombre(), StringUtils
							.formatearAMoneda(propiedad.getValorHipotecario()));
		else
			mensaje = String.format("No pudo hipotecar la propiedad %s",
					propiedad.getNombre());

		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));

		turnosList = gestorJugadores.getTurnoslist();
		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoJugadorActual, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);
		sendToAll(status);

		return propiedadToReturn;

	}

	/**
	 * Deshipoteca una propiedad y le resta el monto de la deshipteca a su
	 * dueño.
	 * 
	 * @param propiedad
	 *            La propiedad que se va a deshipotecar
	 * @return La {@code propiedad} deshipotecada si se deshipotecó.
	 *         {@code null} si no se pudo deshipotecar. Ver
	 *         {@link TableroController#hipotecarPropiedad(TarjetaPropiedad, Jugador)}
	 *         .
	 * @throws Exception
	 *             Si no se puede enviar el mensaje al cliente.
	 */
	public TarjetaPropiedad deshipotecarPropiedad(TarjetaPropiedad propiedad)
			throws Exception {

		Jugador jugador = gestorJugadores.getCurrentPlayer();
		TarjetaPropiedad propiedadToReturn = gestorTablero
				.deshipotecarPropiedad(propiedad, jugador);

		String mensaje;
		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.ACTUALIZANDO_ESTADO;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();

		if (propiedadToReturn != null)
			mensaje = String.format("Deshipotecó la propiedad %s por %s.",
					propiedad.getNombre(), StringUtils
							.formatearAMoneda(propiedad
									.getValorDeshipotecario()));
		else
			mensaje = String.format("No pudo deshipotecar la propiedad %s",
					propiedad.getNombre());

		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));

		turnosList = gestorJugadores.getTurnoslist();
		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoJugadorActual, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);
		sendToAll(status);

		return propiedadToReturn;

	}

	/**
	 * Construye {@code cantidad} de edificios en el color de la {@code calle} y
	 * le cobra al dueño de la calle.
	 * 
	 * @param calle
	 *            La calle del color donde se va a construir
	 * @param cantidad
	 *            La cantidad de casas que se van a construir
	 * @return El dinero invertido en construir o {@code -1} si no se pudo
	 *         construir.
	 * @throws Exception
	 *             Si no se puede enviar el mensaje al cliente.
	 */
	public int construirEdificios(TarjetaCalle calle, int cantidad)
			throws Exception {

		Jugador jugador = gestorJugadores.getCurrentPlayer();
		int toReturn = gestorTablero.comprarEdificio(cantidad,
				(CasilleroCalle) calle.getCasillero());

		String mensaje;
		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.ACTUALIZANDO_ESTADO;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();

		if (toReturn != -1)
			mensaje = String.format(
					"Contruyó %s edificios sobre la calle %s por %s.",
					cantidad, calle.getNombre(),
					StringUtils.formatearAMoneda(toReturn));
		else
			mensaje = String.format("No pudo construir sobre la calle %s",
					calle.getNombre());

		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));

		turnosList = gestorJugadores.getTurnoslist();
		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoJugadorActual, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);
		sendToAll(status);

		return toReturn;

	}

	/**
	 * Vende {@code cantidad} de edificios en el color de la {@code calle} y le
	 * paga al dueño de la calle.
	 * 
	 * @param calle
	 *            La calle del color donde se quiere vender.
	 * @param cantidad
	 *            La cantidad de casas que se van a vender
	 * @return El dinero ganado por vender o {@code -1} si no se pudo vender.
	 * @throws Exception
	 *             Si no se puede enviar el mensaje al cliente.
	 */
	public int venderEdificios(TarjetaCalle calle, int cantidad)
			throws Exception {
		Jugador jugador = gestorJugadores.getCurrentPlayer();
		int cantVendida = gestorTablero.venderEdificio(cantidad,
				(CasilleroCalle) calle.getCasillero());

		int money = (calle.getPrecioVentaCadaCasa()) * cantVendida;

		String mensaje;
		EstadoJuego estadoJuegoJugadorActual = EstadoJuego.ACTUALIZANDO_ESTADO;
		List<Jugador> turnosList;
		List<History> historyList = new ArrayList<History>();

		if (cantVendida != -1)
			mensaje = String.format(
					"Vendió %s edificios de la calle %s por %s.", cantVendida,
					calle.getNombre(), StringUtils.formatearAMoneda(money));
		else
			mensaje = String.format("No pudo vender de la calle %s",
					calle.getNombre());

		historyList.add(new History(StringUtils.getFechaActual(), jugador
				.getNombre(), mensaje));

		turnosList = gestorJugadores.getTurnoslist();
		status = new MonopolyGameStatus(turnosList, gestorBanco.getBanco(),
				gestorTablero.getTablero(), estadoJuegoJugadorActual, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);
		sendToAll(status);

		return money;
	}

	/**
	 * Implementa el objetivo de la tarjeta Comunidad o Suerte Cuando un jugador
	 * humano saca una de las tarjetas.
	 * 
	 * @param senderId
	 *            id de jugador que envió el mensaje.
	 * @param tarjeta
	 *            Tarjeta obtenida en el casillero.
	 * 
	 */
	public void realizarObjetivoTarjeta(int senderId, Tarjeta tarjeta)
			throws Exception {
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		realizarObjetivoTarjeta(jugador, tarjeta);
	}

	/**
	 * Implementa el objetivo de la tarjeta Comunidad o Suerte Cuando un jugador
	 * saca una de las tarjetas.
	 * 
	 * @param jugador
	 * @param tarjeta
	 * @throws Exception
	 */
	private boolean realizarObjetivoTarjeta(Jugador jugador, Tarjeta tarjeta)
			throws Exception {
		AccionEnTarjeta accion = null;
		if (tarjeta.isTarjetaComunidad()) {
			accion = gestorTablero.getGestorTarjetas().jugarTarjetaComunidad(
					jugador, (TarjetaComunidad) tarjeta);
		} else {
			accion = gestorTablero.getGestorTarjetas().jugarTarjetaSuerte(
					jugador, (TarjetaSuerte) tarjeta);
		}
		return jugarAccionTarjeta(jugador, accion);
	}

	/**
	 * Busca el objetivo de la tarjeta sacada del mazo a partir del Enum y
	 * ejecuta el objetivo.
	 * 
	 * <ol>
	 * <li>AccionEnTarjeta.PAGAR</li>
	 * <li>AccionEnTarjeta.COBRAR</li>
	 * <li>AccionEnTarjeta.COBRAR_TODOS</li>
	 * <li>AccionEnTarjeta.MOVER_A</li>
	 * <li>AccionEnTarjeta.IR_A_CARCEL</li>
	 * <li>AccionEnTarjeta.LIBRE_DE_CARCEL</li>
	 * </ol>
	 * </p>
	 * <p>
	 * Acciones tarjeta Suerte:
	 * <ol>
	 * <li>AccionEnTarjeta.MOVER_A</li>
	 * <li>AccionEnTarjeta.COBRAR</li>
	 * <li>AccionEnTarjeta.IR_A_CARCEL</li>
	 * <li>AccionEnTarjeta.MOVER</li>
	 * <li>AccionEnTarjeta.PAGAR_POR_CASA_HOTEL</li>
	 * <li>AccionEnTarjeta.LIBRE_DE_CARCEL</li>
	 * <li>AccionEnTarjeta.PAGAR</li>
	 * </ol>
	 * </p>
	 * 
	 * @param jugador
	 * @param tarjeta
	 * @throws Exception
	 */
	public boolean jugarAccionTarjeta(Jugador jugador,
			AccionEnTarjeta accionEnTarjeta) throws Exception {
		String mensaje;
		Casillero casillero = null;
		AccionEnCasillero accionEnCasillero;
		MutableBoolean cobraSalida;

		int senderId = (jugador.isHumano() ? ((JugadorHumano) jugador)
				.getSenderID() : -1);

		switch (accionEnTarjeta) {

		// ~~~> El jugador cobra, el banco paga
		case COBRAR:
			gestorBanco.pagar(jugador, accionEnTarjeta.getMonto());
			break;

		// ~~~> El jugador paga, el banco cobra
		case PAGAR:
			try {
				gestorBanco.cobrar(jugador, accionEnTarjeta.getMonto());
			} catch (SinDineroException e) {
				ExceptionMessage msg = new ExceptionMessage(e);
				sendToOne(senderId, msg);
				return false;
			}
			break;

		// ~~~> Cobra a todos los jugadores de la partida.
		case COBRAR_TODOS:
			this.contadorPagos = 0;
			String msgString = String.format("Debe pagar %s al jugador %s",
					StringUtils.formatearAMoneda(accionEnTarjeta.getMonto()),
					jugador.getNombre());
			PayToPlayerMessage msg = new PayToPlayerMessage(msgString, jugador,
					accionEnTarjeta.getMonto(), null);
			if (senderId == -1)
				sendToAll(msg);
			else
				sendToOther(senderId, msg);
			break;

		// ~~~> Debe pagar por cada casa u hotel
		case PAGAR_POR_CASA_HOTEL:
			try {
				gestorBanco.cobrarPorCasaYHotel(jugador,
						accionEnTarjeta.getPrecioPorCasa(),
						accionEnTarjeta.getPrecioPorHotel());
			} catch (SinDineroException e) {
				ExceptionMessage msgSinDinero = new ExceptionMessage(e);
				sendToOne(senderId, msgSinDinero);
				return false;
			}
			break;

		// ~~~> Se mueve a un determinado casillero.
		case MOVER:
			cobraSalida = new MutableBoolean(accionEnTarjeta.isCobraSalida());
			casillero = gestorTablero.moverAdelante(jugador,
					accionEnTarjeta.getNroCasilleros(), cobraSalida);
			if (cobraSalida.booleanValue())
				gestorBanco.pagarPasoSalida(jugador);
			break;

		// ~~~> Retrocede casilleros.
		case MOVER_A:
			cobraSalida = new MutableBoolean(accionEnTarjeta.isCobraSalida());
			casillero = gestorTablero.moverACasillero(jugador,
					accionEnTarjeta.getNroCasilleros(), cobraSalida);
			if (cobraSalida.booleanValue())
				gestorBanco.pagarPasoSalida(jugador);
			break;

		// ~~~> Tarjeta que deja libre de la cárcel.
		case LIBRE_DE_CARCEL:
			jugador.getTarjetaCarcelList().add(
					accionEnTarjeta.getTarjetaCarcel());
			gestorTablero.getGestorTarjetas().quitarTarjetaLibreDeCarcel(
					accionEnTarjeta.getTarjetaCarcel());

			break;

		// ~~~> Ir a la cárcel.
		case IR_A_CARCEL:
			gestorTablero.irACarcel(jugador);
			break;

		default:
			break;
		}

		mensaje = accionEnTarjeta.getMensaje();

		// ~~~> Se manda las historias a los demás participantes.
		sendToAll(new HistoryGameMessage(new History(
				StringUtils.getFechaActual(), jugador.getNombre(), mensaje)));

		switch (accionEnTarjeta) {
		case MOVER:
		case MOVER_A:
			accionEnCasillero = gestorTablero.getAccionEnCasillero(jugador,
					casillero);
			if (jugador.isHumano()) {
				jugarAccionCasillero(accionEnCasillero, jugador, casillero,
						senderId);
			} else {
				jugarAccionEnCasilleroJV(accionEnCasillero,
						(JugadorVirtual) jugador, casillero);
			}
			break;
		case IR_A_CARCEL:
			if (jugador.isHumano())
				this.siguienteTurno(false);
			break;

		default:
			if (jugador.isHumano())
				this.siguienteTurno(true);
			break;
		}

		return true;
	}

	/**
	 * Método para llevar preso a un jugador.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 */
	public void irALaCarcel(int senderId) throws Exception {
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		irALaCarcel(jugador);
	}

	/**
	 * Método para llevar preso a un jugador.
	 * 
	 * @param jugador
	 *            que irá preso.
	 * @throws Exception
	 */
	private void irALaCarcel(Jugador jugador) throws Exception {
		gestorTablero.irACarcel(jugador);

		sendToAll(new HistoryGameMessage(new History(
				StringUtils.getFechaActual(), jugador.getNombre(),
				"Fue a la cárcel")));

		if (jugador.isHumano())
			siguienteTurno(false);
	}

	/**
	 * Método para cobrar el impuesto sobre el cápital.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 * @param tipoImpuesto
	 */
	public void impuestoAlCapital(int senderId, EnumsTipoImpuesto tipoImpuesto)
			throws Exception {
		int monto = 0;
		History history;
		HistoryGameMessage msgHistory;
		Jugador jugador;

		jugador = gestorJugadores.getJugadorHumano(senderId);
		if (tipoImpuesto == EnumsTipoImpuesto.TIPO_IMPUESTO_MONTO)
			monto = 200;
		else
			monto = (int) (jugador.getCapital() * 0.1);

		if (jugador.getDinero() >= monto) {
			gestorBanco.cobrar(jugador, monto);
			history = new History();
			history.setFecha(StringUtils.getFechaActual());
			history.setUsuario(jugador.getNombre());
			history.setMensaje(String.format("Pagó %s de impuesto al capital.",
					monto));
			msgHistory = new HistoryGameMessage(history);
			sendToAll(msgHistory);
		} else {
			// TODO completar acción cuando el jugador quiere pagar por
			SinDineroException sde = new SinDineroException(
					String.format(
							"No posees suficiente dinero para pagar el impuesto. Debes pagar %s.",
							StringUtils.formatearAMoneda(monto)));
			sendToOne(senderId, sde);
		}
		if (jugador.isHumano())
			siguienteTurno(true);
	}

	/**
	 * Método para que el jugador pague al banco un determinado {@code monto}.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano.
	 * @param monto
	 *            cantidad de dinero que el jugador le pagará al banco.
	 */
	public void pagarAlBanco(int senderId, int monto, String mensaje)
			throws Exception, SinDineroException {
		Jugador jugador;
		History history;
		HistoryGameMessage msgHistory;

		jugador = gestorJugadores.getJugadorHumano(senderId);
		gestorBanco.cobrar(jugador, monto);
		if (StringUtils.IsNullOrEmpty(mensaje)) {
			history = new History();
			history.setFecha(StringUtils.getFechaActual());
			history.setUsuario(jugador.getNombre());
			history.setMensaje(mensaje);
			msgHistory = new HistoryGameMessage(history);
			sendToAll(msgHistory);
		}
		if (jugador.isHumano())
			siguienteTurno(true);
	}

	/**
	 * Método ejecutado por un jugador humano para determinar si tiró dados
	 * dobles para salir de la cárcel.
	 * 
	 * @param senderId
	 *            Id de conexión de un jugador humano.
	 * @param dados
	 *            resultado de la tirada del jugador.
	 * @throws Exception
	 */
	public void tirarDadosDoblesSalirCarcel(int senderId, Dado dados)
			throws Exception {
		Jugador jugador;
		jugador = gestorJugadores.getJugadorHumano(senderId);
		tirarDadosDoblesSalirCarcel(jugador, dados);
	}

	/**
	 * Método para determinar si un jugador tiró dados dobles para salir de la
	 * cárcel.
	 * 
	 * @param jugador
	 *            que tiró los dados.
	 * @param dados
	 *            resultado obtenido de la tirada.
	 * @throws Exception
	 */
	private void tirarDadosDoblesSalirCarcel(Jugador jugador, Dado dados)
			throws Exception {
		MutableBoolean cobraSalida = new MutableBoolean(true);

		History history;
		String mensaje = "";
		Casillero casillero;
		AccionEnCasillero accion;
		HistoryGameMessage msgHistory;

		jugador.setUltimoResultado(dados);

		if (dados.EsDoble() || jugador.getCantidadTurnosCarcel() >= 3) {
			// ~~~> Sacó dobles, sale de la carcel.
			jugador.resetCantidadTurnosCarcel();
			jugador.setPreso(false);
			mensaje = dados.EsDoble() ? String.format(
					"Sacó dobles (%s - %s). Sale de la cárcel.",
					dados.getValorDado(1), dados.getValorDado(2))
					: "Tercer turno en la cárcel. Queda libre.";
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), mensaje);
			msgHistory = new HistoryGameMessage(history);
			sendToAll(msgHistory);

			// ~~> Sigue jugando
			casillero = gestorTablero.moverAdelante(jugador, dados.getSuma(),
					cobraSalida);
			// TODO corregir paso de
			if (cobraSalida.booleanValue())
				gestorBanco.pagarPasoSalida(jugador);

			accion = gestorTablero.getAccionEnCasillero(jugador, casillero);

			if (jugador.isHumano())
				jugarAccionCasillero(accion, jugador, casillero,
						((JugadorHumano) jugador).getSenderID());
			else
				jugarAccionEnCasilleroJV(accion, (JugadorVirtual) jugador,
						casillero);
		} else {
			jugador.incrementarCantidadTurnosCarcel();
			mensaje = String.format(
					"No sacó dobles (%s - %s). Queda en la cárcel.",
					dados.getValorDado(1), dados.getValorDado(2));
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), mensaje);
			msgHistory = new HistoryGameMessage(history);
			sendToAll(msgHistory);
			siguienteTurno(false);
		}
	}

	/**
	 * Método para salir de la cárcel a través de un pago de dinero.
	 * 
	 * @param senderId
	 *            Identificador del jugador que esta abonando.
	 * @throws Exception
	 */
	public void pagarSalidaDeCarcel(int senderId, EnumSalidaCarcel tipoSalida)
			throws Exception, SinDineroException {
		Jugador jugador;
		Tarjeta tarjeta;
		History history;
		List<History> historyList;
		String mensaje = "";
		MonopolyGameStatus status;

		jugador = gestorJugadores.getJugadorHumano(senderId);
		historyList = new ArrayList<History>();
		if (tipoSalida == EnumSalidaCarcel.PAGAR) {
			if (jugador.getDinero() >= 50) {
				jugador.pagar(50);
				mensaje = String.format("Pagó %s para salir de la cárcel.",
						StringUtils.formatearAMoneda(50));
			} else {
				throw new SinDineroException(
						String.format(
								"No cuentas con suficiente dinero para pagar %s para quedar libre. Vende hoteles, casas o hipoteca propiedades para continuar con el juego.",
								StringUtils.formatearAMoneda(50)), 50);
			}
		} else {
			if (jugador.getTarjetaCarcelList().size() > 0) {
				tarjeta = jugador.getTarjetaCarcelList().get(0);
				jugador.getTarjetaCarcelList().remove(tarjeta);
				gestorTablero.getGestorTarjetas().agregarTarjetaLibreDeCarcel(
						tarjeta);
				mensaje = String.format(
						"Usó una tarjeta de la %s para salir de la cárcel.",
						tarjeta.isTarjetaSuerte() ? "Suerte"
								: "Caja de la Comunidad");
			}
		}

		jugador.setPreso(false);

		if (!StringUtils.IsNullOrEmpty(mensaje)) {
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), mensaje);
			historyList.add(history);
		}

		status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				EstadoJuego.ESPERANDO_TURNO, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);

		sendToOther(senderId, status);

		status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				EstadoJuego.LIBRE, null, gestorJugadores.getCurrentPlayer(),
				historyList, null);

		sendToOne(senderId, status);

	}

	/**
	 * Método para pagar el alquiler por haber caído en el casillero del jugador
	 * propietario.
	 * 
	 * @param senderId
	 *            Id de conexión de un jugador humano.
	 * @param tarjetaPropiedad
	 * @throws Exception
	 */
	public void pagarAlquiler(int senderId, int propiedadId)
			throws SinDineroException, Exception {
		Jugador jugador;
		Jugador jugadorPropietario;
		Casillero casillero;
		TarjetaPropiedad tarjeta;
		int monto;

		jugador = gestorJugadores.getJugadorHumano(senderId);
		casillero = gestorTablero.getCasillero(propiedadId);
		tarjeta = gestorBanco.getBanco().getTarjetaPropiedadByCasillero(
				casillero);

		jugadorPropietario = tarjeta.getJugador();

		if (tarjeta.isPropiedadCalle()) {
			monto = ((TarjetaCalle) tarjeta).calcularAlquiler();
		} else if (tarjeta.isPropiedadCompania()) {
			monto = ((TarjetaCompania) tarjeta).calcularAlquiler(jugador
					.getUltimoResultado());
		} else {
			monto = ((TarjetaEstacion) tarjeta).calcularAlquiler();
		}

		jugador.pagarAJugador(jugadorPropietario, monto);

		sendToAll(new HistoryGameMessage(
				new History(
						StringUtils.getFechaActual(),
						jugador.getNombre(),
						String.format(
								"Pagó %s al jugador %s en concepto de alquiler de la propiedad.",
								StringUtils.formatearAMoneda(monto),
								jugadorPropietario.getNombre(),
								tarjeta.getNombre()))));

		siguienteTurno(true);

	}

	/**
	 * Método para llevar a cabo la subasta de una propiedad.
	 * 
	 * @param senderId
	 *            Id de conexión de un jugador humano.
	 * @param subastaStatus
	 *            Estado de la subasta.
	 * 
	 * @throws Exception
	 */
	public void subastar(int senderId, SubastaStatus subastaStatus)
			throws Exception {
		Jugador jugadorActual = gestorJugadores.getJugadorHumano(senderId);
		subastar(jugadorActual, subastaStatus);
	}

	private void subastar(Jugador jugadorActual, SubastaStatus subastaStatus)
			throws SinDineroException, Exception {
		AuctionFinishMessage msgFinalizarSubasta;
		AuctionPropertyMessage msgActualizarSubasta;
		AuctionNotifyMessage msgHistorySubasta;
		AuctionDecideMessage msgDecidirSubasta;
		HistoryGameMessage msgHistoryGame;
		TarjetaPropiedad tarjeta;
		Jugador jugadorTurno;

		String mensaje;
		History history;
		List<History> historyList;
		int montoSubasta = 0;
		int montoSubastaVirtual = 0;
		int montoMinimoSubasta = 0;
		int senderId = 0;
		boolean esVirtual = jugadorActual.isVirtual();
		boolean decidirAceptarSubasta = false;

		tarjeta = gestorBanco.getBanco().getTarjetaPropiedad(
				subastaStatus.propiedadSubastada.getNombrePropiedad());
		montoSubasta = subastaStatus.montoSubasta;

		// Si está creada, recién se genera, se agregan los jugadores.
		if (subastaStatus.estado == EnumEstadoSubasta.CREADA) {
			// Validar que todos los jugadores tengan suficiente dinero.
			gestorSubasta = new SubastaController(
					subastaStatus.propiedadSubastada);
			for (Jugador jugador : gestorJugadores.getTurnoslist()) {
				if (subastaStatus.jugadorActual == null
						&& jugador.equals(jugadorActual))
					continue;

				if (jugador.getDinero() > subastaStatus.montoSubasta)
					gestorSubasta.agregarJugadorASubasta(jugador);
			}

			switch (gestorSubasta.cantidadJugadores()) {
			case 0:
				/**
				 * Si no hay jugadores apostadores la propiedad queda
				 * disponible.
				 */
				mensaje = String
						.format("Ningún jugador posee dinero para subastar. La Propiedad %s queda disponible",
								tarjeta.getNombre());

				msgHistoryGame = new HistoryGameMessage(new History(
						StringUtils.getFechaActual(),
						jugadorActual.getNombre(), mensaje));
				sendToAll(msgHistoryGame);

				Thread.sleep(1500);

				// Si es virtual el jugador que inició la subasta
				// continúa el siguiente turno. Si no informo al jugador
				// humando para que finalice su turno.
				if (esVirtual) {
					siguienteTurno(true);
				} else {
					senderId = ((JugadorHumano) jugadorActual).getSenderID();
					msgFinalizarSubasta = new AuctionFinishMessage(null,
							mensaje);
					sendToOne(senderId, msgFinalizarSubasta);
				}
				break;

			case 1:
				/**
				 * Notificar quien ganó la subasta.
				 */
				jugadorTurno = gestorSubasta.getJugadoresList().get(0);

				/**
				 * Si el jugador adjudicado es distinto al que inició la
				 * subasta.
				 */
				if (!jugadorActual.equals(jugadorTurno)) {

					/**
					 * Si es virtual, debo preguntar si el jugador acepta la
					 * subasta.
					 */
					if (jugadorTurno.isVirtual()) {
						decidirAceptarSubasta = gestorJugadoresVirtuales
								.decidirAceptarSubasta(tarjeta, montoSubasta,
										(JugadorVirtual) jugadorTurno);
					}
					/**
					 * Si es humano envío mensaje preguntando si acepta la
					 * subasta de la propiedad ganada.
					 */
					else {
						mensaje = String
								.format("Haz ganado la subasta de la propiedad %s. Deseas pagar %s para hacerte propietario.",
										tarjeta.getNombre(), montoSubasta);
						msgDecidirSubasta = new AuctionDecideMessage(mensaje,
								montoSubasta, tarjeta, jugadorActual);
						sendToOne(((JugadorHumano) jugadorTurno).getSenderID(),
								msgDecidirSubasta);
						break;
					}
				} else {
					// Ganador, único apostor
					decidirAceptarSubasta = true;
				}

				/**
				 * El banco traspasa la propiedad al ganador de la subasta.
				 */
				if (decidirAceptarSubasta) {

					adquirirPropiedad(jugadorTurno, null, tarjeta, montoSubasta);

					mensaje = String.format(
							"Ganó la subasta de la propiedad %s con %s.",
							tarjeta.getNombre(),
							StringUtils.formatearAMoneda(montoSubasta));
					history = new History(StringUtils.getFechaActual(),
							jugadorTurno.getNombre(), mensaje);

					// Notifico mediante un GameStatus que el jugador se
					// adjudicó la propiedad.
					sendToAll(new MonopolyGameStatus(
							gestorJugadores.getTurnoslist(),
							gestorBanco.getBanco(), gestorTablero.getTablero(),
							EstadoJuego.ACTUALIZANDO_ESTADO, null,
							gestorJugadores.getCurrentPlayer(),
							new ArrayList<History>(Arrays.asList(history)),
							null));

					Thread.sleep(1500);

					/**
					 * Si el jugador que inició la subasta ganó, entonces mando
					 * un determinado mensaje. en caso contrario ganó un jugador
					 * virtual e informa quién la ganó.
					 */
					if (jugadorActual.equals(jugadorTurno))
						mensaje = String.format(
								"Ganaste la subasta de la propiedad %s",
								tarjeta.getNombre());
					else {
						mensaje = String
								.format("%s ganó la subasta de la propiedad %s. Finalizó tu turno.",
										jugadorTurno.getNombre(),
										tarjeta.getNombre());
					}

					msgFinalizarSubasta = new AuctionFinishMessage(null,
							mensaje);
					sendToOne(senderId, msgFinalizarSubasta);

				}
				/**
				 * Si no decide aceptar la subasta. Notifico.
				 */
				else {
					mensaje = String
							.format("Ningún jugador posee dinero para subastar. La Propiedad %s queda disponible",
									tarjeta.getNombre());
					history = new History(StringUtils.getFechaActual(),
							jugadorTurno.getNombre(), mensaje);
					sendToAll(new HistoryGameMessage(history));

					Thread.sleep(1500);

					if (jugadorActual.isVirtual()) {
						siguienteTurno(true);
					} else {
						senderId = ((JugadorHumano) jugadorActual)
								.getSenderID();
						msgFinalizarSubasta = new AuctionFinishMessage(null,
								mensaje);
						sendToOne(senderId, msgFinalizarSubasta);
					}
				}
				break;

			default:
				/**
				 * Existen varios jugadores para apostar por la propiedad.
				 */
				gestorSubasta.inicializarVariables();
				jugadorTurno = gestorSubasta.siguienteTurno();

				// Genero los histories de la subasta.
				historyList = new ArrayList<History>();

				mensaje = "Inició la subasta con "
						+ StringUtils.formatearAMoneda(montoSubasta);
				history = new History(StringUtils.getFechaActual(),
						jugadorActual.getNombre(), mensaje);
				historyList.add(history);

				mensaje = "Turno para subastar.";
				history = new History(StringUtils.getFechaActual(),
						jugadorTurno.getNombre(), mensaje);
				historyList.add(history);

				msgHistorySubasta = new AuctionNotifyMessage(historyList);
				sendToAll(msgHistorySubasta);

				Thread.sleep(1500);

				subastaStatus = new SubastaStatus(EnumEstadoSubasta.INICIADA,
						new ArrayList<History>(), jugadorTurno, tarjeta,
						montoSubasta);

				msgActualizarSubasta = new AuctionPropertyMessage(
						juego.getUniqueID(), subastaStatus);

				// Si el jugador actual es humano. Mando un mensaje al resto
				// para que inicien sus respectivas pantallas.
				if (jugadorActual.isHumano())
					sendToOther(((JugadorHumano) jugadorActual).getSenderID(),
							msgActualizarSubasta);
				else
					// Si es Virtual envío a todos el mensaje.
					sendToAll(msgActualizarSubasta);

				montoMinimoSubasta = (int) (tarjeta.getValorPropiedad() * 0.1);

				/**
				 * Hago un ciclo para recorrer los turnos de los posibles
				 * jugadores virtuales. Si el siguiente jugador es humano, envía
				 * el msj al jugador correspondiente para que haga su
				 * ofrecimiento.
				 */
				while (true) {
					if (jugadorTurno.isVirtual()) {
						// Incremento el monto minímo de la subasta para el
						// jugador virtual.
						montoSubastaVirtual = gestorJugadoresVirtuales.pujar(
								tarjeta, montoSubasta + montoMinimoSubasta,
								jugadorActual, (JugadorVirtual) jugadorTurno);

						Thread.sleep(2000);

						// Si es 0, se retira de la subasta.
						if (montoSubastaVirtual <= 0) {

							// Notifico a los jugadores que el jugador virtual
							// abandonó la subasta.
							mensaje = "Abandonó la Subasta.";
							history = new History(StringUtils.getFechaActual(),
									jugadorTurno.getNombre(), mensaje);
							msgHistorySubasta = new AuctionNotifyMessage(
									new ArrayList<History>(
											Arrays.asList(history)));
							sendToAll(msgHistorySubasta);

							Thread.sleep(1500);

							gestorSubasta.quitarJugadorDeSubasta(jugadorTurno);

							/**
							 * Si es igual a 1 queda el jugador ganador.
							 */
							if (gestorSubasta.cantidadJugadores() <= 1) {

								mensaje = String
										.format("Ganó la subasta de la propiedad %s con %s.",
												tarjeta.getNombre(),
												StringUtils
														.formatearAMoneda(montoSubasta));
								history = new History(
										StringUtils.getFechaActual(),
										jugadorActual.getNombre(), mensaje);

								sendToAll(new MonopolyGameStatus(
										gestorJugadores.getTurnoslist(),
										gestorBanco.getBanco(),
										gestorTablero.getTablero(),
										EstadoJuego.SUBASTA_FINALIZADA, null,
										gestorJugadores.getCurrentPlayer(),
										new ArrayList<History>(Arrays
												.asList(history)), null));
								break;
							} else {
								jugadorTurno = gestorSubasta.jugadorActual();
								continue;
							}

						} else {
							// Notifico a los jugadores que el jugador virtual
							// levantó el monto de la subasta.
							historyList = new ArrayList<History>();

							mensaje = "Subastó con "
									+ StringUtils
											.formatearAMoneda(montoSubastaVirtual);
							history = new History(StringUtils.getFechaActual(),
									jugadorTurno.getNombre(), mensaje);
							historyList.add(history);

							montoSubasta = montoSubastaVirtual;
							jugadorActual = jugadorTurno;
							jugadorTurno = gestorSubasta.siguienteTurno();

							if (jugadorTurno.isVirtual()) {
								mensaje = "Turno de subastar";
								history = new History(
										StringUtils.getFechaActual(),
										jugadorTurno.getNombre(), mensaje);
								historyList.add(history);
							}

							msgHistorySubasta = new AuctionNotifyMessage(
									historyList);
							sendToAll(msgHistorySubasta);
							continue;
						}

					} else {
						mensaje = "Turno para subastar.";
						history = new History(StringUtils.getFechaActual(),
								jugadorTurno.getNombre(), mensaje);
						subastaStatus = new SubastaStatus(
								EnumEstadoSubasta.JUGANDO,
								new ArrayList<History>(Arrays.asList(history)),
								jugadorTurno, tarjeta, montoSubasta);
						msgActualizarSubasta = new AuctionPropertyMessage(
								juego.getUniqueID(), subastaStatus);
						sendToAll(msgActualizarSubasta);
						break;
					}
				}
				break;
			}
		}
		/**
		 * Si la subasta ya se encuentra iniciada
		 */
		else {
			if (subastaStatus.estado == EnumEstadoSubasta.JUGANDO) {

				// Genero los histories de la subasta.
				historyList = new ArrayList<History>();

				mensaje = "Subastó con ."
						+ StringUtils.formatearAMoneda(montoSubasta);
				history = new History(StringUtils.getFechaActual(),
						jugadorActual.getNombre(), mensaje);
				historyList.add(history);

				jugadorTurno = gestorSubasta.siguienteTurno();

				mensaje = "Turno para subastar.";
				history = new History(StringUtils.getFechaActual(),
						jugadorTurno.getNombre(), mensaje);
				historyList.add(history);

				msgHistorySubasta = new AuctionNotifyMessage(historyList);
				sendToAll(msgHistorySubasta);

				Thread.sleep(1500);

				/**
				 * Hago un ciclo para recorrer los turnos de los posibles
				 * jugadores virtuales. Si el siguiente jugador es humano, envía
				 * el msj al jugador correspondiente para que haga su
				 * ofrecimiento.
				 */
				while (true) {
					if (jugadorTurno.isVirtual()) {
						// Incremento el monto minímo de la subasta para el
						// jugador virtual.
						montoSubastaVirtual = gestorJugadoresVirtuales.pujar(
								tarjeta, montoSubasta + montoMinimoSubasta,
								jugadorActual, (JugadorVirtual) jugadorTurno);

						Thread.sleep(2000);

						// Si es 0, se retira de la subasta.
						if (montoSubastaVirtual <= 0) {

							// Notifico a los jugadores que el jugador virtual
							// abandonó la subasta.
							mensaje = "Abandonó la Subasta.";
							history = new History(StringUtils.getFechaActual(),
									jugadorTurno.getNombre(), mensaje);
							msgHistorySubasta = new AuctionNotifyMessage(
									new ArrayList<History>(
											Arrays.asList(history)));
							sendToAll(msgHistorySubasta);

							Thread.sleep(1500);

							gestorSubasta.quitarJugadorDeSubasta(jugadorTurno);

							/**
							 * Si es igual a 1 queda el jugador ganador.
							 */
							if (gestorSubasta.cantidadJugadores() <= 1) {

								mensaje = String
										.format("Ganó la subasta de la propiedad %s con %s.",
												tarjeta.getNombre(),
												StringUtils
														.formatearAMoneda(montoSubasta));
								history = new History(
										StringUtils.getFechaActual(),
										jugadorActual.getNombre(), mensaje);

								sendToAll(new MonopolyGameStatus(
										gestorJugadores.getTurnoslist(),
										gestorBanco.getBanco(),
										gestorTablero.getTablero(),
										EstadoJuego.SUBASTA_FINALIZADA, null,
										gestorJugadores.getCurrentPlayer(),
										new ArrayList<History>(Arrays
												.asList(history)), null));
								break;
							} else {
								jugadorTurno = gestorSubasta.jugadorActual();
								continue;
							}

						} else {
							// Notifico a los jugadores que el jugador virtual
							// levantó el monto de la subasta.
							historyList = new ArrayList<History>();

							mensaje = "Subastó con ."
									+ StringUtils
											.formatearAMoneda(montoSubastaVirtual);
							history = new History(StringUtils.getFechaActual(),
									jugadorTurno.getNombre(), mensaje);
							historyList.add(history);

							montoSubasta = montoSubastaVirtual;
							jugadorActual = jugadorTurno;
							jugadorTurno = gestorSubasta.siguienteTurno();

							if (jugadorTurno.isVirtual()) {
								mensaje = "Turno de subastar";
								history = new History(
										StringUtils.getFechaActual(),
										jugadorTurno.getNombre(), mensaje);
								historyList.add(history);
							}

							msgHistorySubasta = new AuctionNotifyMessage(
									historyList);
							sendToAll(msgHistorySubasta);
							continue;
						}

					} else {
						mensaje = "Turno para subastar.";
						history = new History(StringUtils.getFechaActual(),
								jugadorTurno.getNombre(), mensaje);
						subastaStatus = new SubastaStatus(
								EnumEstadoSubasta.JUGANDO,
								new ArrayList<History>(Arrays.asList(history)),
								jugadorTurno, tarjeta, montoSubasta);
						msgActualizarSubasta = new AuctionPropertyMessage(
								juego.getUniqueID(), subastaStatus);
						sendToAll(msgActualizarSubasta);
						break;
					}
				}
			} else {
				GestorLogs.registrarError("No se debería dar el caso.");
			}
		}
	}

	public void resultadoDecisionSubasta(Jugador jugador, int monto,
			TarjetaPropiedad propiedad, boolean respuesta,
			Jugador jugadorSubastador) throws Exception {

		AuctionFinishMessage msgFinalizarSubasta;
		int senderId = 0;
		String mensaje = "";
		History history;

		if (respuesta) {
			adquirirPropiedad(jugador, null, propiedad, monto);

			mensaje = String.format(
					"Ganó la subasta de la propiedad %s con %s.",
					propiedad.getNombre(), StringUtils.formatearAMoneda(monto));
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), mensaje);

			sendToAll(new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
					gestorBanco.getBanco(), gestorTablero.getTablero(),
					EstadoJuego.ACTUALIZANDO_ESTADO, null,
					gestorJugadores.getCurrentPlayer(), new ArrayList<History>(
							Arrays.asList(history)), null));

			Thread.sleep(1500);

			if (jugadorSubastador.isHumano()) {
				senderId = ((JugadorHumano) jugadorSubastador).getSenderID();
				msgFinalizarSubasta = new AuctionFinishMessage(null, mensaje);
				sendToOne(senderId, msgFinalizarSubasta);
			} else {
				siguienteTurno(true);
			}
		} else {

			mensaje = String
					.format("Ningún jugador posee dinero para subastar. La Propiedad %s queda disponible",
							propiedad.getNombre());

			if (jugadorSubastador.isHumano()) {
				senderId = ((JugadorHumano) jugadorSubastador).getSenderID();
				msgFinalizarSubasta = new AuctionFinishMessage(null, mensaje);
				sendToOne(senderId, msgFinalizarSubasta);
			} else {
				siguienteTurno(true);
			}
		}
	}

	public void finalizarSubasta(int senderId) {

	}

	/**
	 * Método para enviar un mensaje por el chat.
	 * 
	 * @param history
	 *            mensaje del chat.
	 * 
	 * @throws Exception
	 */
	public void sendChatMessage(History history) throws Exception {
		ChatGameMessage msgChatGameMessage = new ChatGameMessage(null, history);
		sendToAll(msgChatGameMessage);
	}

	/**
	 * Método para mostrar la historia del juego.
	 * 
	 * @param history
	 * @throws Exception
	 */
	public void sendHistoryGame(History history) throws Exception {
		sendToAll(new HistoryGameMessage(history));
	}

	// =====================================================================//
	// ================= Métodos para determinar el juego. =================//
	// =====================================================================//

	/**
	 * Actualiza los datos de un juego guardado
	 * 
	 * @param juego
	 *            El juego a actualizar
	 * @return El juego actualizado
	 */
	public static Juego updateJuego(Juego juego) {
		IJuegoDao juegoDao = (IJuegoDao) appContext.getBean("juegoDao");
		juegoDao.update(juego);
		return juego;
	}

	/**
	 * Busca un juego en la base de datos
	 * 
	 * @param juego
	 *            El {@code UniqueID} del juego que se quiere buscar
	 * @return El juego o {@code null} si no se encontró.
	 */
	public static Juego findJuegoByUniqueId(String uniqueID) {
		IJuegoDao juegoDao = (IJuegoDao) appContext.getBean("juegoDao");
		return juegoDao.findJuegoByUniqueId(uniqueID);
	}

	/**
	 * Guarda un juego en la base de datos
	 * 
	 * @param juego
	 *            El juego que se quiere guardar
	 * @return El juego guardado
	 */
	public static Juego saveJuego(Juego juego) {
		IJuegoDao juegoDao = (IJuegoDao) appContext.getBean("juegoDao");
		juegoDao.save(juego);
		return juego;
	}

	/**
	 * Borra un juego de la base de datos
	 * 
	 * @param juego
	 *            El juego que se quiere borrar
	 * @return El juego que se borro
	 */
	public static Juego deleteJuego(Juego juego) {
		IJuegoDao juegoDao = (IJuegoDao) appContext.getBean("juegoDao");
		juegoDao.delete(juego);
		return juego;
	}

	/**
	 * Busca en la base de datos todos los juegos guardados de un usuario.
	 * 
	 * @param usuario
	 *            El usuario creador de los juegos que se quiere buscar.
	 * @return Los Juegos creados por el {@code usuario}
	 */
	public static List<Juego> buscarJuegosGuardados(Usuario usuario) {
		IJuegoDao juegoDao = (IJuegoDao) appContext.getBean("juegoDao");
		return juegoDao.getJuegoGuardados(usuario);
	}

	/**
	 * Busca un juego guardado en la base de datos
	 * 
	 * @param UniqueID
	 *            El nombre del juego guardado
	 * @return El juego
	 */
	public static Juego buscarJuegoGuardado(String UniqueID) {
		IJuegoDao juegoDao = (IJuegoDao) appContext.getBean("juegoDao");
		return juegoDao.findJuegoByUniqueId(UniqueID);
	}

	// =====================================================================//
	// ============= Métodos para envío de mensaje al cliente. =============//
	// =====================================================================//

	/**
	 * Método para enviar un mensaje a un jugador en particular.
	 * 
	 * @param recipientID
	 *            Id de conexión de un jugador humano.
	 * @param message
	 *            Objecto mensaje.
	 * 
	 * @throws Exception
	 */
	private void sendToOne(int recipientID, Object message) throws Exception {
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
	private void sendToAll(Object message) throws Exception {
		for (int key : gestorJugadores.getIdConnectionClient()) {
			PartidasController.getInstance().getMonopolyGame()
					.sendToOne(key, message);
		}
		Thread.sleep(1000);
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
	private void sendToOther(int senderId, Object message) throws Exception {
		for (int key : gestorJugadores.getIdConnectionClient()) {
			if (key != senderId)
				PartidasController.getInstance().getMonopolyGame()
						.sendToOne(key, message);
		}
		Thread.sleep(1000);
	}

	// =====================================================================//
	// ========================== Getter & Setter ==========================//
	// =====================================================================//

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

	public JugadorVirtualController getGestorJugadoresVirtuales() {
		return gestorJugadoresVirtuales;
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

	public void addContadorPagos() {
		this.contadorPagos++;
	}

	public boolean checkPagaronTodos() {
		return contadorPagos == this.getCantJugadores();
	}

}
