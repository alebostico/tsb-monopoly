package monopoly.controller;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import monopoly.model.Estado.EstadoJugador;
import monopoly.model.Jugador;
import monopoly.model.JugadorVirtual;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.TarjetaPropiedadComparator;
import monopoly.util.exception.SinDineroException;
import monopoly.util.exception.SinEdificiosException;

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
	 * Cuando el jugador cae en el casillero 05 "Impuesto sobre el capital"
	 * puede estimar su capital en $200 y pagar ese monto o bien pagar el 10%
	 * del capital real, calculado como la sumatoria de: el valor de todas las
	 * propiedades que posea, el valor de las casas/hoteles que posea en sus
	 * propiedades y el dinero en efectivo.
	 */
	private static final int IMP_ESP_MONTO = 200;
	private static final int IMP_ESP_PORC = 10;

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

	/**
	 * Decide si le conviene pagar para salir de la carcel o no
	 * 
	 * @param jugadorActual
	 *            El jugador que está en la carcel
	 * @return true si sale de la carcel. False si no lo hace.
	 */
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

	/**
	 * Decide si le conviene salir de la carcel usando la tarjeta de salida.
	 * 
	 * @param jugadorActual
	 *            El jugador que está en la carcel
	 * @return true si sale de la carcel. False si no lo hace.
	 */
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

	/**
	 * <p>
	 * Cuando el jugador cae en el casillero 05 "Impuesto sobre el capital"
	 * puede estimar su capital en $200 y pagar ese monto o bien pagar el 10%
	 * del capital real, calculado como la sumatoria de:
	 * <ul>
	 * <li>el valor de todas las propiedades que posea,</li>
	 * <li>el valor de las casas/hoteles que posea en sus propiedades y</li>
	 * <li>el dinero en efectivo.</li>
	 * </ul>
	 * <p>
	 * El jugador decidirá tomar la opción a pagar que menor gasto suponga
	 * 
	 * @param jugadorActual
	 *            El jugador que va a pagar el impuesto
	 * @return El monto a pagar
	 * 
	 */
	public int decidirImpuestoEspecial(JugadorVirtual jugadorActual) {
		int capitalJugador = jugadorActual.getCapital();
		int aPagar = ((capitalJugador * IMP_ESP_PORC) / 100);

		if (IMP_ESP_MONTO <= aPagar) {
			GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
					+ " decide pagar el monto fijo de $200");
			return IMP_ESP_MONTO;
		}
		GestorLogs.registrarDebug("El jugador " + jugadorActual.getNombre()
				+ " decide pagar el 10% de su capital: $" + aPagar);
		return aPagar;
	}

	/**
	 * Método que hipoteca una serie de propiedades del jugador con
	 * comportamiento aleatorio. Hipotecará todas las propiedades desde la más
	 * cara hasta la más barata hasta que cubra la deuda.
	 * 
	 * @param cantidad
	 *            La cantidad que necesita obtener de la hipoteca
	 * @param jugadorActual
	 *            El jugador que quiere hipotecar
	 * @return La sumatoria de todas las hipotecas (puede ser igual o menor a
	 *         cantidad)
	 */
	private static int hipotecarAleatorio(int cantidad,
			JugadorVirtual jugadorActual) {

		// 1- Copiar ordenadamente las propiedades hipotecables
		List<TarjetaPropiedad> propiedadesHipotecables = new LinkedList<>();
		int totalHipoteca = 0;

		for (TarjetaPropiedad tarjetaPropiedad : jugadorActual
				.getTarjPropiedadList()) {

			if (!tarjetaPropiedad.isHipotecada()) {
				if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE
						&& ((CasilleroCalle) tarjetaPropiedad.getCasillero())
								.getNroCasas() != 0)
					continue;
				propiedadesHipotecables.add(tarjetaPropiedad);
			}

		}

		// Ordenamos la lista por precio de mayor a menor
		Collections.sort(propiedadesHipotecables,
				Collections.reverseOrder(new TarjetaPropiedadComparator()));

		// 2- Hipotecar desde la más cara hasta alcanzar el dinero necesario.

		for (TarjetaPropiedad tarjetaPropiedad : propiedadesHipotecables) {
			int valorHipoteca = jugadorActual
					.hipotecarPropiedad(tarjetaPropiedad);
			if (valorHipoteca > 0) {
				cantidad -= valorHipoteca;
				totalHipoteca += valorHipoteca;
			}

			// llegamos al monto necesario...
			if (cantidad <= 0)
				break;

		}
		return totalHipoteca;

	}

	/**
	 * Deshipoteca las cantidades de una forma aleatoria en funci�n de unas
	 * probabilidades dadas
	 * 
	 * @param jugadorActual
	 *            El Jugador que va a deshipotecar
	 */
	@SuppressWarnings("unused")
	private static void deshipotecarAleatorio(JugadorVirtual jugadorActual) {
		Random rnd = new Random();

		// 1- Copiar ordenadamente las propiedades hipotecables
		List<TarjetaPropiedad> propiedadesHipotecadas = new LinkedList<>();

		for (TarjetaPropiedad tarjetaPropiedad : jugadorActual
				.getTarjPropiedadList()) {

			if (tarjetaPropiedad.isHipotecada()) {
				propiedadesHipotecadas.add(tarjetaPropiedad);
			}

		}
		// Ordenamos la lista por precio de mayor a menor
		Collections.sort(propiedadesHipotecadas,
				Collections.reverseOrder(new TarjetaPropiedadComparator()));

		for (TarjetaPropiedad tarjetaPropiedad : propiedadesHipotecadas) {
			if (jugadorActual.getDinero() > tarjetaPropiedad
					.getValorDeshipotecario()) {

				int probabilidad = (jugadorActual.getDinero() - tarjetaPropiedad
						.getValorDeshipotecario())
						* 100
						/ jugadorActual.getDinero();
				int resultado = rnd.nextInt() % 100;
				// Si el resultado esta dentro de la probabilidad, deshipoteca
				if (resultado < probabilidad) {
					jugadorActual.dehipotecarPropiedad(tarjetaPropiedad);

				}

			}
		}

	}

	/**
	 * Método para vender casas del jugador aleatorio. Venderá todas las casas
	 * desde la propiedad más cara hasta la más barata hasta superar la cantidad
	 * necesaria. Así se asegura alcanzar el máximo dinero posible.
	 * 
	 * @param cantidad
	 * @param jugadorActual
	 * @return La sumatoria de todas las ventas (puede ser igual o menor a
	 *         cantidad)
	 */
	@SuppressWarnings("unused")
	private static int venderAleatorio(int cantidad,
			JugadorVirtual jugadorActual) {

		Random rnd = new Random();
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugadorActual.getJuego().getUniqueID());
		TableroController tableroController = juegoController
				.getGestorTablero();
		int totalVendido = 0;

		// 1- Selecciono el monopolio a construir entre todos los posibles.
		// Para ello busco todas las calles con permiso de construcción y las
		// ordeno en una lista
		// Creare otra para meter las calles que ya se han comprobado y no
		// repetir sobre el mismo
		List<TarjetaCalle> propiedadesEdificadas = new LinkedList<>();

		for (TarjetaPropiedad tarjetaPropiedad : jugadorActual
				.getTarjPropiedadList()) {
			if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE) {
				if (tableroController
						.cantEdificiosMonopolio((TarjetaCalle) tarjetaPropiedad) > 0)
					propiedadesEdificadas.add((TarjetaCalle) tarjetaPropiedad);
			}
		}

		// Ordenamos la lista por precio de mayor a menor
		Collections.sort(propiedadesEdificadas,
				Collections.reverseOrder(new TarjetaPropiedadComparator()));

		// Selecciono el monopolio a vender -> El primero, si no hay dinero
		// suficiente, el segundo...

		for (TarjetaCalle tarjeta : propiedadesEdificadas) {

			int montoVenta = tableroController
					.venderTodosLosEdificios((CasilleroCalle) tarjeta
							.getCasillero())
					* tarjeta.getPrecioCadaCasa();
			cantidad -= montoVenta;
			totalVendido += montoVenta;

			// llegamos al monto necesario...
			if (cantidad <= 0)
				break;

		}

		return totalVendido;

	}

	/**
	 * Contruye casas y/o hoteles en alguna propiedad del jugador
	 * 
	 * @param jugadorActual
	 *            El jugador que va a construír
	 * @throws SinEdificiosException
	 *             Cuando el banco no dipone de las casas/hoteles necesarios
	 *             para construír
	 * @throws SinDineroException
	 *             Cuando el jugador no dispone dinero para comprar las
	 *             casas/hoteles
	 */
	public static void construirAleatorio(JugadorVirtual jugadorActual)
			throws SinEdificiosException, SinDineroException {

		Random rnd = new Random();
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugadorActual.getJuego().getUniqueID());
		TableroController tableroController = juegoController
				.getGestorTablero();

		// 1- Selecciono el monopolio a construir entre todos los posibles.
		// Para ello busco todas las calles con permiso de construcción y las
		// ordeno en una lista
		// Creare otra para meter las calles que ya se han comprobado y no
		// repetir sobre el mismo

		List<TarjetaCalle> propiedadesConstruibles = new LinkedList<>();

		for (TarjetaPropiedad tarjetaPropiedad : jugadorActual
				.getTarjPropiedadList()) {
			if (tarjetaPropiedad.getCasillero().getTipoCasillero() == TipoCasillero.C_CALLE) {
				if (tableroController
						.esConstruible(((CasilleroCalle) tarjetaPropiedad
								.getCasillero()))) {

					boolean existe = false;
					for (TarjetaCalle tarjetaCalle : propiedadesConstruibles) {
						if (tarjetaCalle.getColor() == ((TarjetaCalle) tarjetaPropiedad)
								.getColor()) {
							existe = true;
							break;
						}
					}
					if (!existe)
						propiedadesConstruibles
								.add((TarjetaCalle) tarjetaPropiedad);
				}
			}
		}

		// Ordenamos la lista por precio de mayor a menor
		Collections.sort(propiedadesConstruibles,
				Collections.reverseOrder(new TarjetaPropiedadComparator()));

		// Selecciono el monopolio a construir

		// 2- Construir sobre el monopolio seleccionado (variable construir)
		// Se intentará construir hasta que el azar diga que se pare y sea
		// posible construir algo.
		for (TarjetaCalle tarjetaCalle : propiedadesConstruibles) {
			if (jugadorActual.getDinero() >= tarjetaCalle.getPrecioCadaCasa()) {
				int probabilidad = (jugadorActual.getDinero() - tarjetaCalle
						.getPrecioCadaCasa()) * 100 / jugadorActual.getDinero();
				int resultado = rnd.nextInt() % 100;
				if (resultado < probabilidad) {

					int casasMonopolio = tableroController
							.cantEdificiosMonopolio(tarjetaCalle);
					int paraConstruir = (tableroController
							.cantPropiedadesMonopolio(tarjetaCalle
									.getCasillero()) * 5)
							- casasMonopolio;

					while (paraConstruir > 0) {
						probabilidad = (jugadorActual.getDinero() - tarjetaCalle
								.getPrecioCadaCasa())
								* 100
								/ jugadorActual.getDinero();
						resultado = rnd.nextInt() % 100;

						if (resultado < probabilidad) {
							casasMonopolio++;
							paraConstruir--;
						} else {
							break;
						}
					}

					tableroController.comprarEdificio(casasMonopolio,
							(CasilleroCalle) tarjetaCalle.getCasillero());
				}
			}
		}

	}

	/**
	 * Método por el cual un jugador paga una cantidad. Si la cantidad es
	 * positiva, el jugador pagará dinero, en caso de ser negativa, el jugador
	 * estará cobrando dinero. Al pagar, se debe restar la cantidad del dinero
	 * total y del capital. Si no tiene dinero suficiente se tratará de
	 * conseguir automáticamente mediante hipotecas y ventas de edificios. Si
	 * aun así no consigue el dinero suficiente se declara como moroso. Si es
	 * moroso devuelve -1.
	 * 
	 * @param jugador
	 *            El jugador que debe pagar
	 * @param cantidad
	 *            La cantidad que tiene que pagar
	 * @return El dinero que le quedó al jugador después de pagar
	 */
	public static int pagar(JugadorVirtual jugador, int cantidad) {
		// 1. Intento vender edificios si no tengo dinero suficiente
		if (cantidad > jugador.getDinero()) {
			int dineroNecesario = cantidad - jugador.getDinero();
			switch (jugador.getTipoJugador()) {
			// Jugador basado en reglas
			case TJ_MAGNATE: {
				venderAleatorio(dineroNecesario, jugador);
				break;
			}
			// Jugador con comportamiento aleatorio
			case TJ_EMPRESARIO:
			case TJ_COMPRADOR_PRIMERIZO:
				venderAleatorio(dineroNecesario, jugador);
				break;
			}
		}

		// 2. Si aún no dispongo de dinero suficiente, intento hipotecar
		// propiedades
		if (cantidad > jugador.getDinero()) {
			int dineroNecesario = cantidad - jugador.getDinero();
			switch (jugador.getTipoJugador()) {
			case TJ_MAGNATE: // Jugador inteligente
			{
				hipotecarAleatorio(dineroNecesario, jugador);
				break;
			}
			case TJ_EMPRESARIO:
			case TJ_COMPRADOR_PRIMERIZO: // Jugador aleatorio
				hipotecarAleatorio(dineroNecesario, jugador);
				break;
			}
		}

		// PAGAR:
		// Si tengo dinero suficiente, se paga (descontando de dinero total y
		// capital). Aumentando si es cobrar
		if (cantidad <= jugador.getDinero()) {
			jugador.pagar(cantidad);
		}
		// Declaro al jugador moroso
		else {
			// Bancarrota
			jugador.setEstadoJugador(EstadoJugador.EJ_BANCARROTA);
			return -1;
		}
		return jugador.getDinero();
	}

}
