package monopoly.controller;

import java.util.Random;

import monopoly.model.Jugador;
import monopoly.model.JugadorVirtual;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

/**
 * Clase est&aacute;tica para el manejo de la inteligencia de los jugadores
 * Virtuales. Existen tres tipos de jugadores con diferente nivel de dificultad:
 * <ul>
 * <li><strong>Comprador primerizo (f&aacute;cil)</strong>: compra todo lo que
 * puede.</li>
 * <li><strong>Empresario (media)</strong>: basado en resultados random.</li>
 * <li><strong>Magnate (dificil)</strong>: basado en reglas.</li>
 * </ul>
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class JugadorVirtualController {

	/**
	 * porcentaje de puja mínima. La puja inicial será este porcentaje del valor
	 * de la propiedad. El valor está en PORCENTAJE (%).
	 */
	private static final int PORC_PUJA_MINIMA = 25;

	/**
	 * Cuando un TJ_EMPRESARIO o un TJ_COMPRADOR_PRIMERIZO pujan, cuanto más
	 * ofrecen. El valor está en PESOS ($).
	 */
	private static final int AUMENTO_PUJA = 10;

	/**
	 * Cuanto tiene que pagar el Jugador para salir de la carcel
	 */
	private static final int MULTA_CARCEL = 50;

	/**
	 * El jugador decidir&aacute; la nueva cantidad a pujar, si lo considera
	 * oportuno. El jugador basado en reglas lo hara en funci&oacute;n de su
	 * razonamiento, y el aleatorio por puro azar.
	 * 
	 * @param propiedad
	 * @param maxActual
	 * @param ganador
	 * @param jugadorActual
	 * @return
	 */
	public int pujar(TarjetaPropiedad propiedad, int maxActual,
			Jugador ganador, JugadorVirtual jugadorActual) {

		// Si no tiene dinero no ofrece nada sin importar el tipo de jugador que
		// sea
		if (maxActual >= jugadorActual.getDinero()) {
			GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
					+ " no tiene dinero para pujar por "
					+ propiedad.getNombre());
			return 0;
		}

		Random rnd = new Random();
		int puja = 0;
		int precioPropiedad = propiedad.getValorPropiedad();
		int dineroJugador = jugadorActual.getDinero();
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugadorActual.getJuego().getUniqueID());
		TableroController tableroController = juegoController
				.getGestorTablero();

		switch (jugadorActual.getTipoJugador()) {
		// Jugador basado en reglas
		case TJ_MAGNATE:

			// Si se comienza la puja y tengo dinero pujo
			if (maxActual == 0 && precioPropiedad < dineroJugador) {
				puja = ((precioPropiedad * PORC_PUJA_MINIMA) / 100);
			} else

			// Si es la ultima propiedad del monopolio y tengo dinero, pujo.
			if (tableroController.esUltimaPropiedadMonopolio(
					propiedad.getCasillero(), jugadorActual)
					&& maxActual * 2 < dineroJugador) {
				puja = maxActual + ((maxActual * 30) / 100);
			} else

			// Si es la última propiedad del monopolio de otro jugador, tengo
			// dinero, y dicho jugador es el ganador, pujo!
			if (tableroController.esUltimaPropiedadMonopolio(
					propiedad.getCasillero(), ganador)
					&& maxActual * 2 < dineroJugador) {
				puja = maxActual + ((maxActual * 10) / 100);
			} else

			// Si soy el unico poseedor del monopolio y tengo dinero, pujo
			if (tableroController.esUnicoPoseedorMonopolio(
					propiedad.getCasillero(), jugadorActual)
					&& maxActual * 2 < dineroJugador) {
				puja = maxActual + ((maxActual * 20) / 100);
			} else

			// Si tengo mucho dinero pujo
			if (maxActual < precioPropiedad && maxActual * 10 < dineroJugador) {
				puja = maxActual + ((maxActual * 30) / 100);
			} else

			// Si tengo mucho dinero pujo
			if (maxActual < precioPropiedad && maxActual * 3 < dineroJugador) {
				puja = maxActual + ((maxActual * 10) / 100);
			} else

			// Si tengo mucho dinero pujo
			if (maxActual > precioPropiedad && maxActual * 5 < dineroJugador) {
				puja = maxActual + ((maxActual * 5) / 100);
			} else

				break;

		case TJ_EMPRESARIO:
			// Si no hay puja

			if (maxActual == 0) {
				// Si tengo dinero, pujo por encima del precio

				int proxPuja = rnd.nextInt(precioPropiedad);

				if (dineroJugador > proxPuja) {
					puja = proxPuja;
				}
			}
			// Si ya hay puja
			else if (maxActual + AUMENTO_PUJA < dineroJugador) {
				// Decido según el azar si pujo o no, en funcion de la siguiente
				// probabilidad
				int probabilidad = (dineroJugador - maxActual) * 100
						/ dineroJugador;

				int resultado = rnd.nextInt() % 100;

				// Si el numero es menor que la probabilidad, pujo
				if (resultado < probabilidad) {
					puja = maxActual + AUMENTO_PUJA;
				}
			}

			break;
		case TJ_COMPRADOR_PRIMERIZO:

			if (maxActual == 0) {
				// Si tengo dinero, pujo por encima del precio

				int proxPuja = rnd.nextInt(precioPropiedad);

				if (dineroJugador > proxPuja) {
					puja = proxPuja;
				}
			}
			// Si ya hay puja
			else if (maxActual + AUMENTO_PUJA < dineroJugador) {
				// Decido según el azar si pujo o no, en funcion de la siguiente
				// probabilidad
				int probabilidad = 50;

				int resultado = rnd.nextInt() % 100;

				// Si el numero es menor que la probabilidad, pujo
				if (resultado < probabilidad) {
					puja = maxActual + AUMENTO_PUJA;
				}
			}

			break;

		}

		if (puja != 0) {
			GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
					+ " ofreció $" + puja + " por " + propiedad.getNombre());
		} else {
			GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
					+ " no ofreció nada por " + propiedad.getNombre());
		}
		return puja;
	}

	/**
	 * El jugador decidir&aacute; al caer sobre una propiedad libre si quiere
	 * comprarla o no. La decisi&oacute;n se tomar&aacute; en funci&oacute;n del
	 * tipo de agente de IA que se trate.
	 * 
	 * @param casillero
	 * @param jugadorActual
	 * @return
	 */
	public boolean decidirComprar(Casillero casillero,
			JugadorVirtual jugadorActual) {

		Random rnd = new Random();
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugadorActual.getJuego().getUniqueID());
		TableroController tableroController = juegoController
				.getGestorTablero();

		TarjetaPropiedad propiedad;
		int precio;

		switch (jugadorActual.getTipoJugador()) {
		// Jugador basado en reglas
		case TJ_MAGNATE:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;

				// No compra porque no tiene dinero
				if (casilleroCalle.getTarjetaCalle().getValorPropiedad() >= jugadorActual
						.getDinero()) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " no tiene dinero para comprar "
							+ casilleroCalle.getTarjetaCalle().getNombre());
					return false;
				}

				// Compra porque con ella completa un monopolio
				if (tableroController.esUltimaPropiedadMonopolio(
						casilleroCalle, jugadorActual)) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre() + " compra "
							+ casilleroCalle.getTarjetaCalle().getNombre()
							+ " porque completa el monopolio.");
					return true;
				}

				// Compra porque esta el monopolio completo libre. Inversión de
				// futuro
				if (tableroController.monopolioLibre(casilleroCalle)) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroCalle.getTarjetaCalle()
											.getNombre()
									+ " como inversión de futuro. Aun esta todo libre.");
					return true;
				}

				// Compra porque es el único poseedor de propiedades de ese
				// monopolio
				if (tableroController.esUnicoPoseedorMonopolio(casilleroCalle,
						jugadorActual)) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroCalle.getTarjetaCalle()
											.getNombre()
									+ " porque él es el único que posee propiedades en el monopolio.");
					return true;

				}

				// Compra porque tiene dinero suficiente aunque no pueda
				// completar el monopolio
				if (tableroController.poseeParteDelMonopolio(casilleroCalle,
						jugadorActual)
						&& ((casilleroCalle.getTarjetaCalle()
								.getValorPropiedad() * 3) < jugadorActual
								.getDinero())) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroCalle.getTarjetaCalle()
											.getNombre()
									+ " porque tiene dinero suficiente, aunque parte del monopolio no le pertence.");
					return true;
				}

				// Compra porque es la ultima propiedad del monopolio de otro
				// jugador, asi evita que cree el monopolio
				if (tableroController.ultimaPropiedadMonopolioOtroJugador(
						casilleroCalle, jugadorActual)) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroCalle.getTarjetaCalle()
											.getNombre()
									+ " para evitar que otro jugador cree un monopolio completo.");
					return true;
				}

				// Compra porque tiene mucho dinero
				if ((casilleroCalle.getTarjetaCalle().getValorPropiedad() * 7) < jugadorActual
						.getDinero()) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre() + " compra "
							+ casilleroCalle.getTarjetaCalle().getNombre()
							+ " porque tiene mucho dinero.");
					return true;
				}

				break;
			case C_COMPANIA:
				CasilleroCompania casilleroCompania = (CasilleroCompania) casillero;
				precio = casilleroCompania.getTarjetaCompania()
						.getValorPropiedad();

				// No compra porque no tiene dinero
				if (jugadorActual.getDinero() < precio) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " no tiene dinero para comprar "
							+ casilleroCompania.getTarjetaCompania()
									.getNombre());
					return false;
				}
				// Compra porque tiene bastante dinero y ya posee alguna
				// utilidad de servicios
				if ((tableroController.cantPropiedadesMonopolio(
						casilleroCompania, jugadorActual) > 0)
						&& ((precio * 2) < jugadorActual.getDinero())) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroCompania.getTarjetaCompania()
											.getNombre()
									+ " porque tiene dinero suficiente y ya posee alguna utilidad de servicios.");

					return true;
				}
				// Compra como inversión de futuro proque no hay servicios
				// ocupados
				if ((tableroController
						.propiedadesLibresMonopolio(casilleroCompania) == 0)
						&& ((precio * 2) < jugadorActual.getDinero())) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroCompania.getTarjetaCompania()
											.getNombre()
									+ " como inversión de futuro. Quedan libres todas las casillas de servicios.");
					return true;
				}
				// Compra porque tiene mucho dinero
				if ((precio * 7) < jugadorActual.getDinero()) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " compra "
							+ casilleroCompania.getTarjetaCompania()
									.getNombre()
							+ " porque tiene mucho dinero.");
					return true;
				}
				break;

			case C_ESTACION:
				CasilleroEstacion casilleroEstacion = (CasilleroEstacion) casillero;

				precio = casilleroEstacion.getTarjetaEstacion()
						.getValorPropiedad();

				// No compra porque no tiene dinero
				if (jugadorActual.getDinero() < precio) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " no tiene dinero para comprar "
							+ casilleroEstacion.getTarjetaEstacion()
									.getNombre());
					return false;
				}
				// Compra porque ya posee varias estaciones y tiene una cantidad
				// importante de dinero
				if ((tableroController.cantPropiedadesMonopolio(
						casilleroEstacion, jugadorActual) >= 2)
						&& ((precio * 3) < jugadorActual.getDinero())) {

					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroEstacion.getTarjetaEstacion()
											.getNombre()
									+ " porque tiene dinero suficiente y ya posee varias estaciones.");
					return true;

				}
				// Compra como inversión de futuro, quedan libres varias
				// estaciones en el juego
				if (tableroController
						.propiedadesLibresMonopolio(casilleroEstacion) >= 2) {
					GestorLogs
							.registrarDebug("El jugador "
									+ jugadorActual.getNombre()
									+ " compra "
									+ casilleroEstacion.getTarjetaEstacion()
											.getNombre()
									+ " como inversión de futuro. Quedan libres varias estaciones.");
					return true;

				}
				// Compra porque tiene mucho dinero
				if ((precio * 7) < jugadorActual.getDinero()) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " compra "
							+ casilleroEstacion.getTarjetaEstacion()
									.getNombre()
							+ " porque tiene mucho dinero.");
					return true;
				}

				break;

			default:
				return false;
			}

		case TJ_EMPRESARIO:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;
				propiedad = casilleroCalle.getTarjetaCalle();
				break;
			case C_COMPANIA:
				CasilleroCompania casilleroCompania = (CasilleroCompania) casillero;
				propiedad = casilleroCompania.getTarjetaCompania();
				break;

			case C_ESTACION:
				CasilleroEstacion casilleroEstacion = (CasilleroEstacion) casillero;
				propiedad = casilleroEstacion.getTarjetaEstacion();
				break;

			default:
				return false;
			}

			if (jugadorActual.getDinero() > propiedad.getValorPropiedad()) {
				// Sigue una función probabilistica y un valor aleatorio. Si el
				// valor aleatorio es menor que el obtenido
				// en la función, se compra. Si no, no.
				int probabilidad = (jugadorActual.getDinero() - propiedad
						.getValorPropiedad()) * 100 / jugadorActual.getDinero();
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad)
					return true;
			}
			return false;

		case TJ_COMPRADOR_PRIMERIZO:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;
				propiedad = casilleroCalle.getTarjetaCalle();
				break;
			case C_COMPANIA:
				CasilleroCompania casilleroCompania = (CasilleroCompania) casillero;
				propiedad = casilleroCompania.getTarjetaCompania();
				break;

			case C_ESTACION:
				CasilleroEstacion casilleroEstacion = (CasilleroEstacion) casillero;
				propiedad = casilleroEstacion.getTarjetaEstacion();
				break;

			default:
				return false;
			}

			if (jugadorActual.getDinero() > propiedad.getValorPropiedad()) {
				// Sigue una función probabilistica y un valor aleatorio. Si el
				// valor aleatorio es menor que el obtenido
				// en la función, se compra. Si no, no.
				int probabilidad = 50;
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad)
					return true;
			}
			return false;
		}
		return false;
	}

	public boolean decidirSalirPagando(JugadorVirtual jugadorActual) {

		Random rnd = new Random();
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugadorActual.getJuego().getUniqueID());
		TableroController tableroController = juegoController
				.getGestorTablero();

		switch (jugadorActual.getTipoJugador()) {
		// Jugador basado en reglas
		case TJ_MAGNATE:

			// Si quedan menos del x % de propiedades libres para comprar, no se
			// sale de la cárcel porque es más seguro para no pagar. Además debe
			// tener dinero
			// suficiente para salir.
			// Se ha establecido una escala en función de las propiedades que
			// queden libres.
			// También deberé tener dinero para poder comprar y que sea rentable
			// moverse( >50000)
			if ((tableroController.porcPropiedadesLibres() == 100)
					&& jugadorActual.getDinero() > MULTA_CARCEL
					&& (jugadorActual.getDinero() > 50000)) {
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " sale de la cárcel pagando porque es necesario"
						+ " comprar propiedades y están todas libres");
				return true;
			}
			if ((tableroController.porcPropiedadesLibres() >= 50)
					&& jugadorActual.getDinero() > (MULTA_CARCEL * 3)
					&& (jugadorActual.getDinero() > 50000)) {
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " sale de la cárcel pagando porque"
						+ " aún quedan algunas propiedades libres");
				return true;
			}

			if ((tableroController.porcPropiedadesLibres() >= 20)
					&& jugadorActual.getDinero() > (MULTA_CARCEL * 5)
					&& (jugadorActual.getDinero() > 50000)) {
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " sale de la cárcel pagando porque"
						+ " aún quedan algunas propiedades libres");
				return true;
			}

			GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
					+ " no quiere salir de la cárcel pagando"
					+ " porque no lo considera rentable");
			return false;

		case TJ_EMPRESARIO:

			if (jugadorActual.getDinero() > MULTA_CARCEL) {
				// Saldrá con una probabilidad dada por la formula que se aplica
				// a contiinuacion
				// Si el valor aleatorio con M.Twister es menor que el numero
				// sale.
				int probabilidad = (jugadorActual.getCantPropiedades()) * 100
						/ tableroController.getCantPropiedades();
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " sale de la cárcel pagando porque"
							+ " aún quedan algunas propiedades libres");
					return true;
				}
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " no quiere salir de la cárcel pagando"
						+ " porque no lo considera rentable");
				return false;
			}
		case TJ_COMPRADOR_PRIMERIZO:
			if (jugadorActual.getDinero() > MULTA_CARCEL) {
				// Saldrá con una probabilidad dada por la formula que se aplica
				// a contiinuacion
				// Si el valor aleatorio con M.Twister es menor que el numero
				// sale.
				int probabilidad = 50;
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " sale de la cárcel pagando porque"
							+ " aún quedan algunas propiedades libres");
					return true;
				}
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " no quiere salir de la cárcel pagando"
						+ " porque no lo considera rentable");
				return false;
			}

		}

		return false;
	}

	public boolean decidirSalirTarjeta(JugadorVirtual jugadorActual) {

		Random rnd = new Random();
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugadorActual.getJuego().getUniqueID());
		TableroController tableroController = juegoController
				.getGestorTablero();

		switch (jugadorActual.getTipoJugador()) {
		// Jugador basado en reglas
		case TJ_MAGNATE:

			// Sólo sale si quedan muchas casillas libres ( >20% )
			// Debo tener mas de 50000 para que sea rentable al poder comprar
			// otras propiedades
			if ((tableroController.porcPropiedadesLibres() >= 20)
					&& (jugadorActual.getDinero() > 50000)) {
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " sale de la cárcel pagando porque"
						+ " aún quedan algunas propiedades libres");
				return true;
			}
			GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
					+ " no quiere salir de la cárcel pagando"
					+ " porque no lo considera rentable");
			return false;

		case TJ_EMPRESARIO:

			if (jugadorActual.getDinero() > MULTA_CARCEL) {
				// Saldrá con una probabilidad dada por la formula que se aplica
				// a contiinuacion
				// Si el valor aleatorio con M.Twister es menor que el numero
				// sale.
				int probabilidad = (jugadorActual.getCantPropiedades()) * 100
						/ tableroController.getCantPropiedades();
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " sale de la cárcel pagando porque"
							+ " aún quedan algunas propiedades libres");
					return true;
				}
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " no quiere salir de la cárcel pagando"
						+ " porque no lo considera rentable");
				return false;
			}
		case TJ_COMPRADOR_PRIMERIZO:
			if (jugadorActual.getDinero() > MULTA_CARCEL) {
				// Saldrá con una probabilidad dada por la formula que se aplica
				// a contiinuacion
				// Si el valor aleatorio con M.Twister es menor que el numero
				// sale.
				int probabilidad = 50;
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad) {
					GestorLogs.registrarDebug("El jugador "
							+ jugadorActual.getNombre()
							+ " sale de la cárcel pagando porque"
							+ " aún quedan algunas propiedades libres");
					return true;
				}
				GestorLogs.registrarDebug("El jugador "
						+ jugadorActual.getNombre()
						+ " no quiere salir de la cárcel pagando"
						+ " porque no lo considera rentable");
				return false;
			}

		}

		return false;
	}

}
