/**
 * 
 */
package monopoly.controller;

import monopoly.model.Jugador;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tablero.Tablero;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TableroController {

	private Tablero tablero;

	private int cantCasilleros;

	private TarjetaController gestorTarjetas;

	public TableroController() {
		this.tablero = new Tablero(CasillerosController.getCasilleros());
		this.gestorTarjetas = new TarjetaController();
		cantCasilleros = this.tablero.getCasillerosList().length;
	}

	public Tablero getTablero() {
		return tablero;
	}

	public void setTablero(Tablero tablero) {
		this.tablero = tablero;
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

	public void resetCasilleros() {
		tablero.setCasillerosList(CasillerosController.getCasilleros());
	}

	/**
	 * Devuelve el casillero pasado por par&aacute;metro. El par&aacute;metro es
	 * el N&uacute;mero de casillero [1-40], NO el indice del vector. Si el
	 * 'nroCasillero' es inv&aacute;lido (menor a 1 o mayor a 40) devuelve null.
	 * 
	 * @param nroCasillero
	 *            El n&uacute;mero del casillero a devolver.
	 * @return El casillero correspondiente al n&uacute;mero pasado por
	 *         par&aacute;metro o null si no existe.
	 */
	public Casillero getCasillero(int nroCasillero) {
		/*
		 * TODO: estar&iacute;a bueno que si el casillero no existe porque pasa
		 * un nroCasillero incorrecto, en vez de retornar null tire una
		 * excepci&oacute;n CasilleroInvalidoException por ejemplo
		 */

		if (nroCasillero > this.cantCasilleros || nroCasillero < 1)
			return null;

		// Creo que este casteo no es necesario porque afuera hay que volver a
		// preguntar y castear
		// switch (this.casillerosList[nroCasillero - 1].getTipoCasillero()) {
		// case Casillero.CASILLERO_CALLE:
		// return (CasilleroCalle) this.casillerosList[nroCasillero - 1];
		// case Casillero.CASILLERO_COMPANIA:
		// return (CasilleroCompania) this.casillerosList[nroCasillero - 1];
		// case Casillero.CASILLERO_ESTACION:
		// return (CasilleroEstacion) this.casillerosList[nroCasillero - 1];
		// default:
		// return this.casillerosList[nroCasillero + 1];
		// }

		return this.tablero.getCasillerosList()[nroCasillero - 1];
	}

	/**
	 * Devuelve el Casillero de la calle/Estaci&oacute;n/Compañi­a que tiene el
	 * nombre 'nombreCasillero' o null si el nombre no existe.
	 * 
	 * @param nombreCasillero
	 *            El nombre del casillero.
	 * @return El Casillero con el nombre pasado por par&aacute;metro o null si
	 *         la calle no existe.
	 */
	public Casillero getCasillero(String nombreCasillero) {

		/*
		 * TODO: estar&iacute;a bueno que si el casillero no existe porque el
		 * nombre de la calle no existe, en vez de retornar null tire una
		 * excepci&oacute;n NombreInvalidoException por ejemplo
		 */

		Casillero casilleroActual;

		for (int i = 1; i <= this.cantCasilleros; i++) {
			casilleroActual = this.getCasillero(i);

			if (casilleroActual.getTipoCasillero() == TipoCasillero.C_CALLE) {
				if (((CasilleroCalle) casilleroActual).getNombreCalle()
						.compareTo(nombreCasillero) == 0) {
					return casilleroActual;
				}
			}

			if (casilleroActual.getTipoCasillero() == TipoCasillero.C_COMPANIA) {
				if (((CasilleroCompania) casilleroActual).getNombreCompania()
						.compareTo(nombreCasillero) == 0) {
					return casilleroActual;
				}
			}

			if (casilleroActual.getTipoCasillero() == TipoCasillero.C_ESTACION) {
				if (((CasilleroEstacion) casilleroActual).getNombreEstacion()
						.compareTo(nombreCasillero) == 0) {
					return casilleroActual;
				}
			}
		}

		return null;

	}

	/**
	 * Mueve a un Jugador 'cantCasilleros' casilleros hacia adelante (o hacia
	 * atrÃ¡s si 'cantCasilleros' es negativo) y devuelve el Casillero en el que
	 * cayo. Si el movimiento es hacia adelante, el jugador pasa por la salida y
	 * el parametro 'cobraSalida' es true, cobra los $200
	 * 
	 * @param jugador
	 *            El jugador que se quiere mover.
	 * @param cantCasilleros
	 *            La cantidad de casilleros a mover el jugador. Si es positivo
	 *            mueve hacia adelante. Si es negativo hacia atras (es lo mismo
	 *            que llamar al mÃ©todo 'moverAtras').
	 * @param cobraSalida
	 *            true en el caso que el jugador deba cobrar los $200 si pasa
	 *            por la salida. false si no los cobra.
	 * @return El casillero al cual se moviÃ³ el jugador.
	 */
	public Casillero moverAdelante(Jugador jugador, int cantCasilleros,
			boolean cobraSalida) {

		int cobroSalida = 0;
		// busco el casillero actual en el que esta el jugador
		Casillero casilleroActual = jugador.getCasilleroActual();

		// calculo el nro de casillero al que tiene que ir
		int nroCasilleroSiguiente = casilleroActual.getNumeroCasillero()
				+ cantCasilleros;

		// si el casillero es mayor a la cantidad de casilleros total...
		// (es decir, si pasa por la salida...)
		if (nroCasilleroSiguiente > this.cantCasilleros) {
			// ... recalculo el valor del casillero siguiente
			nroCasilleroSiguiente -= this.cantCasilleros;

			// si el jugador tiene que cobrar los $200 en caso de pasar por la
			// salida...
			cobroSalida = -1;
			if (cobraSalida) {
				// ... los cobra
				// this.banco.cobrar(jugador, 200);
				cobroSalida = 1;
			}

		}

		// si esta moviendo para atras y pasa por la salida...
		if (nroCasilleroSiguiente < 1) {
			// ... recalculo el casillero en el que queda
			nroCasilleroSiguiente += this.cantCasilleros;
		}

		Casillero casilleroSiguiente = this.getCasillero(nroCasilleroSiguiente);

		casilleroActual.removeJugador(jugador);
		casilleroSiguiente.addJugador(jugador);

		jugador.setCasilleroActual(casilleroSiguiente);

		this.registrarInfo(jugador, casilleroActual, casilleroSiguiente,
				cobroSalida);

		return casilleroSiguiente;
	}

	/**
	 * Mueve a un Jugador 'cantCasilleros' casilleros hacia atras y devuelve el
	 * Casillero en el que cayo. Cuando mueve para atras NUNCA cobra si pasa por
	 * la salida.
	 * 
	 * @param jugador
	 *            El jugador que se quiere mover.
	 * @param cantCasilleros
	 *            La cantidad de casilleros a mover el jugador (Es lo mismo que
	 *            llamar al mÃ©todo 'moverAdelante' con 'cantCasilleros'
	 *            negativa).
	 * @return El casillero al cual se moviÃ³ el jugador.
	 */
	public Casillero moverAtras(Jugador jugador, int cantCasilleros) {
		return this.moverAdelante(jugador, (cantCasilleros * (-1)), false);
	}

	/**
	 * Mueve el jugador 'jugador' al casillero con el nÃºmero 'nroCasillero'.
	 * Retorna el Casillero al cual se movio al jugador o null si no existe.
	 * 
	 * @param jugador
	 *            El jugador que se quiere mover.
	 * @param nroCasillero
	 *            El nÃºmero de casillero al cual se quiere mover el jugador.
	 * @param cobraSalida
	 *            true en el caso que el jugador deba cobrar los $200 si pasa
	 *            por la salida. false si no los cobra.
	 * @return El casillero al cual se movió el jugador si 'nroCasillero' es
	 *         válido (entre 1 y 40). null en caso contrario.
	 */
	public Casillero moverACasillero(Jugador jugador, int nroCasillero,
			boolean cobraSalida) {
		/*
		 * TODO: estarÃ­a bueno que si el casillero no existe porque pasa un
		 * nroCasillero incorrecto, en vez de retornar null tire una excepciÃ³n
		 * CasilleroInvalidoException por ejemplo
		 */
		Casillero casilleroActual = jugador.getCasilleroActual();
		Casillero casilleroSiguiente = this.getCasillero(nroCasillero);

		int cobroSalida = 0;

		// si el nroCasillero es invÃ¡lido (menor a 1 o mayor a 40) retorna
		// null...
		if (casilleroSiguiente == null)
			return null;

		// si pasa por la salida para ir de un casillero a otro...
		if (casilleroSiguiente.getNumeroCasillero() < casilleroActual
				.getNumeroCasillero()) {
			// y si el jugador tiene que cobrar los $200 en caso de pasar por la
			// salida...
			cobroSalida = -1;
			if (cobraSalida) {
				// ... los cobra
				// this.banco.cobrar(jugador, 200);
				cobroSalida = 1;
			}
		}

		casilleroActual.removeJugador(jugador);
		casilleroSiguiente.addJugador(jugador);

		jugador.setCasilleroActual(casilleroSiguiente);

		this.registrarInfo(jugador, casilleroActual, casilleroSiguiente,
				cobroSalida);

		return casilleroSiguiente;
	}

	/**
	 * Mueve el jugador 'jugador' al CasilleroCalle con el nombre 'nombreCalle'.
	 * Retorna el Casillero al cual se movio al jugador o null si no existe.
	 * 
	 * @param jugador
	 *            El jugador que se quiere mover.
	 * @param nombreCasillero
	 *            El nombre de la calle.
	 * @param cobraSalida
	 *            true en el caso que el jugador deba cobrar los $200 si pasa
	 *            por la salida. false si no los cobra.
	 * @return El casillero al cual se moviÃ³ el jugador si 'nombreCasillero'
	 *         existe. null en caso contrario.
	 */
	public Casillero moverACasillero(Jugador jugador, String nombreCasillero,
			boolean cobraSalida) {

		Casillero casilleroAMover = this.getCasillero(nombreCasillero);

		if (casilleroAMover != null) {
			return this.moverACasillero(jugador,
					casilleroAMover.getNumeroCasillero(), cobraSalida);
		}

		return null;
	}

	/**
	 * Mueve al jugador al casillero de la carcel. No cobra si pasa por la
	 * salida.
	 * 
	 * @param jugador
	 *            El jugador que se manda a la carcel.
	 * @return El casillero de la carcel.
	 */
	public Casillero irACarcel(Jugador jugador) {
		// mando al casillero de la carcel y si pasa por la salida no cobra
		return this.moverACasillero(jugador, 11, false);
	}

	/**
	 * Hace retroceder al jugador hasta el casillero especificado. Es lo mismo
	 * que llamar a 'moverACasillero' con 'cobraSalida' false. Devuelve el
	 * casillero al cual se mueve el jugador.
	 * 
	 * @param jugador
	 *            El jugador que se mueve.
	 * @param nroCasillero
	 *            El nÃºmero de casillero al cual se mueve.
	 * @return El casillero en el que queda el jugador.
	 */
	public Casillero retrocederA(Jugador jugador, int nroCasillero) {
		return this.moverACasillero(jugador, nroCasillero, false);
	}

	/**
	 * Registra la información de los movimientos el el logger
	 * 
	 * @param jugador
	 *            El jugador que se movio
	 * @param casAnterior
	 *            El casillero en el que estaba el jugador
	 * @param casActual
	 *            El casillero al cual se movió el jugador
	 * @param cobroSalida
	 *            Un entero que representa si el jugador paso por la salida o no
	 *            y si cobró los $200.
	 *            <ul>
	 *            <li><b>-1</b> = Paso por la salida y NO cobró los $200</li>
	 *            <li><b>0</b> = NO paso por la salida (y no cobró los $200)</li>
	 *            <li><b>1</b> = Paso por la salida y cobró los $200</li>
	 *            </ul>
	 */
	private void registrarInfo(Jugador jugador, Casillero casAnterior,
			Casillero casActual, int cobroSalida) {
		StringBuilder sb = new StringBuilder();

		sb.append("El Jugador '");
		sb.append(jugador.getFicha().getNombre());
		sb.append("' se movio #");
		sb.append(casAnterior.getNumeroCasillero());
		sb.append(" --> #");
		sb.append(casActual.getNumeroCasillero());

		switch (cobroSalida) {
		case -1:
			sb.append(", paso por la salida y NO cobró los $200");
			break;
		case 0:
			sb.append(", NO paso por la salida (y no cobró los $200)");
			break;
		case 1:
			sb.append(", paso por la salida y cobró los $200");
			break;
		}

		GestorLogs.registrarLog(sb.toString());

	}

	public int casasPorJugador(Jugador jugador) {
		int casas = 0;
		CasilleroCalle casilleroCalle = null;
		for (Casillero casillero : tablero.getCasillerosList()) {
			if (casillero instanceof CasilleroCalle
					&& casillero.getJugadores().contains(jugador)) {
				casilleroCalle = (CasilleroCalle) casillero;
				if (casilleroCalle.getNroCasas() < 5)
					casas = casas + casilleroCalle.getNroCasas();
			}
		}
		return casas;
	}

	public int hotelesPorJugador(Jugador jugador) {
		int casas = 0;
		CasilleroCalle casilleroCalle = null;
		for (Casillero casillero : tablero.getCasillerosList()) {
			if (casillero instanceof CasilleroCalle
					&& casillero.getJugadores().contains(jugador)) {
				casilleroCalle = (CasilleroCalle) casillero;
				if (casilleroCalle.getNroCasas() == 5)
					casas = casas + casilleroCalle.getNroCasas();
			}
		}
		return casas;
	}

}
