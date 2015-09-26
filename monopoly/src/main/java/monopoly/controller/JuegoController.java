/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.List;

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
import monopoly.model.Usuario;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;
import monopoly.util.constantes.EnumAction;
import monopoly.util.constantes.EnumsTipoImpuesto;
import monopoly.util.exception.CondicionInvalidaException;
import monopoly.util.exception.SinDineroException;
import monopoly.util.exception.SinEdificiosException;
import monopoly.util.message.ExceptionMessage;
import monopoly.util.message.game.ChatGameMessage;
import monopoly.util.message.game.CompleteTurnMessage;
import monopoly.util.message.game.HistoryGameMessage;
import monopoly.util.message.game.actions.PayToPlayerMessage;

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

	private JugadorVirtualController gestorJugadoresVirtuales;

	private MonopolyGameStatus status;

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
	public void addPlayer(Jugador jugador) {
		this.gestorJugadores.addPlayer(jugador);
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

			if (gestorJugadores.getCurrentPlayer().isVirtual()) {
				status = new MonopolyGameStatus(
						gestorJugadores.getTurnoslist(),
						gestorBanco.getBanco(), gestorTablero.getTablero(),
						EstadoJuego.ESPERANDO_TURNO, null,
						gestorJugadores.getCurrentPlayer(), historyList, null);
				sendToAll(status);
				tirarDadosJugadorVirtual();
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
			accion.setMonto(((TarjetaSuerte) tarjetaSelected).getIdTarjeta());
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.ESPERANDO_TURNO;
			break;
		case TARJETA_COMUNIDAD:
			tarjetaSelected = gestorTablero.getTarjetaComunidad();
			accion.setMonto(((TarjetaComunidad) tarjetaSelected).getIdTarjeta());
			estadoJuegoJugadorActual = Estado.EstadoJuego.JUGANDO;
			estadoJuegoRestoJugadoresEstadoJuego = EstadoJuego.ESPERANDO_TURNO;
			break;
		case DISPONIBLE_PARA_VENDER:
		case IMPUESTO_DE_LUJO:
		case IMPUESTO_SOBRE_CAPITAL:
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

		mensaje = String.format("Avanzastó al casillero %s, %s",
				casillero.getNombreCasillero(), accion.getMensaje());

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

		mensaje = String.format("Avanzó al casillero %s.",
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

	public void avanzarDeCasilleroJV(JugadorVirtual jugador, Dado dados)
			throws CondicionInvalidaException, SinDineroException, Exception {
		Casillero casillero;
		boolean cobraSalida = true;
		AccionEnCasillero accion;
		// EstadoJuego estadoJuegoJugadorActual = EstadoJuego.TIRAR_DADO;
		// EstadoJuego estadoJuegoRestoJugadoresEstadoJuego =
		// EstadoJuego.TIRAR_DADO;
		// MonopolyGameStatus status;
		Tarjeta tarjetaSelected = null;
		TarjetaPropiedad tarjetaPropiedad = null;
		String mensaje;
		int montoAPagar;

		CasilleroCalle casilleroCalle;
		CasilleroEstacion casilleroEstacion;
		CasilleroCompania casilleroCompania;
		Jugador duenio;

		// History history;

		casillero = gestorTablero.moverAdelante(jugador, dados.getSuma(),
				cobraSalida);

		accion = gestorTablero.getAccionEnCasillero(jugador, casillero,
				dados.getSuma());

		mensaje = String.format("El Jugador %s avanzó al casillero %s, %s",
				jugador.getNombre(), casillero.getNombreCasillero(),
				accion.getMensaje());

		sendToAll(new HistoryGameMessage(new History(
				StringUtils.getFechaActual(), jugador.getNombre(), mensaje)));

		// historiasList.add(new History(StringUtils.getFechaActual(), jugador
		// .getNombre(), mensaje));

		switch (accion) {
		case TARJETA_SUERTE:
			tarjetaSelected = gestorTablero.getTarjetaSuerte();
			tarjetaSuerte(jugador, (TarjetaSuerte) tarjetaSelected);
			break;
		case TARJETA_COMUNIDAD:
			tarjetaSelected = gestorTablero.getTarjetaComunidad();
			tarjetaComunidad(jugador, (TarjetaComunidad) tarjetaSelected);
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
			}else{
				mensaje = String.format("El Jugador %s decidió no comprar %s.",
						jugador.getNombre(), casillero.getNombreCasillero());
				sendToAll(new HistoryGameMessage(new History(
						StringUtils.getFechaActual(), jugador.getNombre(), mensaje)));
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
						.calcularAlquiler(dados.getSuma());
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

//		mensaje = accion.getMensaje();
		
//		sendToAll(new HistoryGameMessage(new History(
//				StringUtils.getFechaActual(), jugador.getNombre(), mensaje)));

	}

	public void tirarDadosJugadorVirtual() throws Exception {
		List<History> historyList = new ArrayList<History>();
		JugadorVirtual jugadorActual = (JugadorVirtual) this.gestorJugadores
				.getCurrentPlayer();
		String mensaje;
		try {
			mensaje = gestorJugadoresVirtuales
					.construirAleatorio(jugadorActual);

		} catch (SinEdificiosException e) {
			mensaje = String
					.format("El jugador %s no pudo comprar edificios porque no tiene disponibilidad en el banco",
							jugadorActual.getNombre());
		} catch (SinDineroException e) {
			mensaje = String
					.format("El jugador %s no pudo comprar edificios porque no tiene dinero suficiente",
							jugadorActual.getNombre());
		}

		// TODO: Agregar la verificacion de deshipoteca.

		if (mensaje != null) {
			sendToAll(new HistoryGameMessage(new History(
					StringUtils.getFechaActual(), gestorJugadores
					.getCurrentPlayer().getNombre(), mensaje)));
		}

		Dado dados = new Dado();

		try {
			this.avanzarDeCasilleroJV(jugadorActual, dados);
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

		status = new MonopolyGameStatus(gestorJugadores.getTurnoslist(),
				gestorBanco.getBanco(), gestorTablero.getTablero(),
				EstadoJuego.ESPERANDO_TURNO, null,
				gestorJugadores.getCurrentPlayer(), historyList, null);
		sendToAll(status);

		siguienteTurno();
	}

	public void siguienteTurno() throws Exception {
		History history;
		Jugador jugadorActual;
		Jugador jugadorSiguiente;
		MonopolyGameStatus status;
		List<History> historyList = new ArrayList<History>();

		jugadorActual = gestorJugadores.getCurrentPlayer();
		jugadorSiguiente = gestorJugadores.siguienteTurno();

		history = new History(StringUtils.getFechaActual(),
				jugadorActual.getNombre(), "Finalizó su turno.");
		historyList.add(history);
		history = new History(StringUtils.getFechaActual(),
				jugadorSiguiente.getNombre(), String.format(
						"Turno del jugador %s.", jugadorSiguiente.getNombre()));
		historyList.add(history);

		if (jugadorSiguiente.isVirtual()) {
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

	public void jugarAccionTarjeta(int senderId,
			TarjetaComunidad tarjetaComunidad) {
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		jugarAccionTarjeta(jugador, tarjetaComunidad);
	}

	private void jugarAccionTarjeta(Jugador jugador,
			TarjetaComunidad tarjetaComunidad) {
		AccionEnTarjeta accion = gestorTablero.getGestorTarjetas()
				.jugarTarjetaComunidad(jugador, tarjetaComunidad);
		jugarAccionTarjeta(jugador, accion);
	}

	public void jugarAccionTarjeta(int senderId, TarjetaSuerte tarjetaSuerte) {
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		jugarAccionTarjeta(jugador, tarjetaSuerte);
	}

	private void jugarAccionTarjeta(Jugador jugador, TarjetaSuerte tarjetaSuerte) {
		AccionEnTarjeta accion = gestorTablero.getGestorTarjetas()
				.jugarTarjetaSuerte(jugador, tarjetaSuerte);
		jugarAccionTarjeta(jugador, accion);
	}

	public boolean jugarAccionTarjeta(Jugador jugador, AccionEnTarjeta accion) {
		String mensaje;
		int senderId = (jugador.isHumano() ? ((JugadorHumano) jugador)
				.getIdJugador() : -1);

		switch (accion) {
		case COBRAR:
			// El jugador cobra, el banco paga
			gestorBanco.pagar(jugador, accion.getMonto());
			break;
		case PAGAR:
			// El jugador paga, el banco cobra
			try {
				gestorBanco.cobrar(jugador, accion.getMonto());
			} catch (SinDineroException e) {
				ExceptionMessage msg = new ExceptionMessage(e);
				sendToOne(senderId, msg);
				return false;
			}
			break;
		case COBRAR_TODOS:
			// gestorBanco.cobrarATodosPagarAUno(jugador, accion.getMonto());
			this.contadorPagos = 0;
			String msgString = String.format("Debe pagar %s al jugador %s",
					StringUtils.formatearAMoneda(accion.getMonto()),
					jugador.getNombre());
			PayToPlayerMessage msg = new PayToPlayerMessage(msgString, jugador,
					accion.getMonto(), null);
			if (senderId == -1)
				sendToAll(msg);
			else
				sendToOther(senderId, msg);
			break;
		case PAGAR_POR_CASA_HOTEL:
			try {
				gestorBanco.cobrarPorCasaYHotel(jugador,
						accion.getPrecioPorCasa(), accion.getPrecioPorHotel());
			} catch (SinDineroException e) {
				ExceptionMessage msgSinDinero = new ExceptionMessage(e);
				sendToOne(senderId, msgSinDinero);
				return false;
			}
			break;
		case MOVER:
			gestorTablero.moverAdelante(jugador, accion.getNroCasilleros(),
					accion.isCobraSalida());
			break;
		case MOVER_A:
			gestorTablero.moverACasillero(jugador, accion.getNroCasilleros(),
					accion.isCobraSalida());
			break;
		case LIBRE_DE_CARCEL:
			jugador.getTarjetaCarcelList().add(accion.getTarjetaCarcel());
			break;
		case IR_A_CARCEL:
			gestorTablero.irACarcel(jugador);
			break;
		default:
			break;
		}

		mensaje = accion.getMensaje();

		HistoryGameMessage historias = new HistoryGameMessage(new History(
				StringUtils.getFechaActual(), jugador.getNombre(), mensaje));

		sendToAll(historias);
		return true;

	}

	public void comprarPropiedad(int senderId, String nombrePropiedad)
			throws SinDineroException, Exception {
		TarjetaPropiedad tarjeta = gestorBanco.getBanco().getTarjetaPropiedad(
				nombrePropiedad);
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		comprarPropiedad(jugador, tarjeta);
	}

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
							"Adquirió la propiedad %s.",
							tarjeta.getNombre()), EnumAction.BUY_PROPERTY,
							status));
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), String.format(
							"Adquirió la propiedad %s.",
							tarjeta.getNombre()));
			sendToOther(senderId, new HistoryGameMessage(history));
		} else {
			history = new History(StringUtils.getFechaActual(),
					jugador.getNombre(), String.format(
							"Adquirió la propiedad %s.",
							tarjeta.getNombre()));
			sendToAll(new HistoryGameMessage(history));
		}

		if (jugador.isHumano())
			siguienteTurno();
	}

	/**
	 * Implementa la accion de TarjetaSuerte cuando viene desde el cliente
	 * (JugadorHumano)
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 * @param tarjeta
	 * @throws Exception
	 */
	public void tarjetaSuerte(int senderId, int idTarjeta) throws Exception {
		TarjetaSuerte tarjeta = gestorTablero.getGestorTarjetas()
				.getTarjetaSuerteById(idTarjeta);
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		tarjetaSuerte(jugador, tarjeta);
	}

	/**
	 * Implementa la accion de TarjetaSuerte cuando se ejecuta en el server
	 * (JugadorVirtual)
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 * @param jugador
	 * @param tarjeta
	 * @throws Exception
	 */
	private void tarjetaSuerte(Jugador jugador, TarjetaSuerte tarjeta)
			throws Exception {
		AccionEnTarjeta accion = gestorTablero.getGestorTarjetas()
				.jugarTarjetaSuerte(jugador, tarjeta);
		if (this.jugarAccionTarjeta(jugador, accion))
			if (jugador.isHumano())
				this.siguienteTurno();
	}

	/**
	 * Implementa la accion de TarjetaComunidad cuando viene desde el cliente
	 * (JugadorHumano)
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 * @param tarjeta
	 * @throws Exception
	 */
	public void tarjetaComunidad(int senderId, int idTarjeta) throws Exception {
		TarjetaComunidad tarjeta = gestorTablero.getGestorTarjetas()
				.getTarjetaComunidadById(idTarjeta);
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		tarjetaComunidad(jugador, tarjeta);
	}

	/**
	 * * Implementa la accion de TarjetaComunidad cuando se ejecuta en el server
	 * (JugadorVirtual)
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 * @param jugador
	 * @param tarjeta
	 * @throws Exception
	 */
	private void tarjetaComunidad(Jugador jugador, TarjetaComunidad tarjeta)
			throws Exception {
		AccionEnTarjeta accion = gestorTablero.getGestorTarjetas()
				.jugarTarjetaComunidad(jugador, tarjeta);
		if (this.jugarAccionTarjeta(jugador, accion))
			if (jugador.isHumano())
				this.siguienteTurno();
	}

	/**
	 * Método para llevar preso al jugador.
	 * 
	 * @param senderId
	 *            id de conexión del jugador humano
	 */
	public void irALaCarcel(int senderId) throws Exception {
		Jugador jugador = gestorJugadores.getJugadorHumano(senderId);
		gestorTablero.irACarcel(jugador);

		History history = new History(StringUtils.getFechaActual(),
				jugador.getNombre(), "Fue a la cárcel");
		sendToAll(new HistoryGameMessage(history));
		if (jugador.isHumano())
			siguienteTurno();
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
			history.setMensaje(String.format(
					"Pagó %s de impuesto al capital.", monto));
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
			siguienteTurno();
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
			siguienteTurno();
	}

	// =====================================================================//
	// ============= Métodos para envío de mensaje al cliente. =============//
	// =====================================================================//

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

	public void sendChatMessage(History history) {
		ChatGameMessage msgChatGameMessage = new ChatGameMessage(null, history);
		sendToAll(msgChatGameMessage);
	}

}
