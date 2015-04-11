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
	 * El jugador decidirá la nueva cantidad a pujar, si lo considerá oportuno.
	 * El jugador basado en reglas lo hará en función de su razonamiento, y el
	 * aleatorio por puro azar.
	 * 
	 * @param p
	 * @param maxActual
	 * @param ganador
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
	 * El jugador decidirá al caer sobre una propiedad libre si quiere comprarla
	 * o no. La decisión se tomará en función del tipo de agente de IA que se
	 * trate.
	 * 
	 * @param casillero
	 * @param jugadorActual
	 * @return
	 */
	public boolean decidirComprar(Casillero casillero,
			JugadorVirtual jugadorActual) {

		switch (jugadorActual.getTipoJugador()) {
		// Jugador basado en reglas
		case TJ_MAGNATE:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;
				
				break;
			case C_COMPANIA:
				CasilleroCompania casilleroCompania = (CasilleroCompania) casillero;
				
				break;
				
			case C_ESTACION:
				CasilleroEstacion casilleroEstacion = (CasilleroEstacion) casillero;
				
				break;
				
			default:
				return false;
			}

		case TJ_EMPRESARIO:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;
				
				break;
			case C_COMPANIA:
				CasilleroCompania casilleroCompania = (CasilleroCompania) casillero;
				
				break;
				
			case C_ESTACION:
				CasilleroEstacion casilleroEstacion = (CasilleroEstacion) casillero;
				
				break;
				
			default:
				return false;
			}
		case TJ_COMPRADOR_PRIMERIZO:
			switch (casillero.getTipoCasillero()) {
			case C_CALLE:
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;
				
				break;
			case C_COMPANIA:
				CasilleroCompania casilleroCompania = (CasilleroCompania) casillero;
				
				break;
				
			case C_ESTACION:
				CasilleroEstacion casilleroEstacion = (CasilleroEstacion) casillero;
				
				break;
				
			default:
				return false;
			}
		}
		return false;
	}

}
