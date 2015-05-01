/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.List;

import monopoly.model.Banco;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.MonopolyGameStatus;
import monopoly.model.MonopolyGameStatus.AccionEnCasillero;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCalle.Color;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;
import monopoly.util.exception.CondicionInvalidaException;
import monopoly.util.exception.SinDineroException;

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

	/**
	 * Devuelve la cantidad de casas que tiene un jugador en todo el tablero.
	 * 
	 * @param jugador
	 *            El jugador
	 * @return La cantidad total de casas
	 */
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

	/**
	 * Devuelve la cantidad de hoteles que tiene un un jugador en todo el
	 * tabler.
	 * 
	 * @param jugador
	 *            El jugador
	 * @return La cantidad total de casas
	 */
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

	/**
	 * Devuelve todas las tarjetas de las calles que conforman un monopolio, es
	 * decir que tienen el mismo color.
	 * 
	 * @param color
	 *            El color del monopolio
	 * @return Todas las tarjetas de las calles que tienen el mismo color
	 */
	public List<TarjetaCalle> getMonopolio(Color color) {

		List<TarjetaCalle> monopolio = new ArrayList<TarjetaCalle>();
		for (Casillero casillero : this.tablero.getCasillerosList()) {
			if (casillero.getTipoCasillero() == TipoCasillero.C_CALLE) {
				CasilleroCalle casilleroCalle = (CasilleroCalle) casillero;
				TarjetaCalle tarjetaCalle = casilleroCalle.getTarjetaCalle();
				if (tarjetaCalle.getColor() == color) {
					monopolio.add(tarjetaCalle);
				}

			}
		}
		return monopolio;
	}

	/**
	 * Devuelve todas las las tarjetas de las calles que conforman un monopolio,
	 * es decir que tienen el mismo color de calle.
	 * 
	 * @param tarjeta
	 *            Una de las tarjetas del mopolio
	 * @return Todas las tarjetas de las calles que tienen el mismo color
	 */
	public List<TarjetaCalle> getMonopolio(TarjetaCalle tarjeta) {
		return getMonopolio(tarjeta.getColor());
	}

	/**
	 * Devuelve todas los casilleros de las calles que conforman un monopolio,
	 * es decir que tienen el mismo color de calle.
	 * 
	 * @param casillero
	 *            Un casillero del monopolio
	 * @return Todas los casilleros de las calles que tienen el mismo color
	 */
	public List<CasilleroCalle> getMonopolio(CasilleroCalle casillero) {

		List<CasilleroCalle> monopolio = new ArrayList<CasilleroCalle>();
		for (Casillero c : this.tablero.getCasillerosList()) {
			if (c.getTipoCasillero() == TipoCasillero.C_CALLE) {
				CasilleroCalle casilleroCalle = (CasilleroCalle) c;
				TarjetaCalle tarjetaCalle = (TarjetaCalle) casilleroCalle
						.getTarjetaCalle();
				if (tarjetaCalle.getColor() == casillero.getTarjetaCalle()
						.getColor()) {
					monopolio.add(casilleroCalle);
				}

			}
		}
		return monopolio;
	}

	/**
	 * Devuelve una lista de todos los casilleros que conforman un monopolio.
	 * Puede ser alguno de los siguieintes casos:
	 * <ul>
	 * <li>Si el casillero pasado por par&aacute;metro es del tipo
	 * <code>C_ESTACION</code>, devuelve las cuatro estaciones.</li>
	 * <li>Si el casillero pasado por par&aacute;metro es del tipo
	 * <code>C_COMPANIA</code>, devuelve las dos comunidades.</li>
	 * <li>Si el casillero pasado por parámetro es del tipo <code>C_CALLE</code>
	 * , devuelve las dos o tres calles de un mismo color que la que se
	 * pas&oacute;. Igual que llamar a
	 * <code>getMonopolio(CasilleroCalle casillero)</code> pero menos eficiente
	 * porque implica mas casteos. Siempre utilizar el m&eacute;todo
	 * <code>getMonopolio(CasilleroCalle casillero)</code> excepto en los casos
	 * que no sea posible.</li>
	 * <li>Cualquier otro tipo, devuelve <code>null</code></li>
	 * </ul>
	 * 
	 * @param casillero
	 *            El casillero del cual se desea el monopolio completo.
	 *            Dependiendo el tipo de este casillero se determina que tipo de
	 *            monopolio es (calle, estaci&oacute;n o comunidad)
	 * @return El listado de todas las propiedades que conforman el monopolio
	 */
	public List<Casillero> getMonopolio(Casillero casillero) {

		List<Casillero> casilleros = new ArrayList<Casillero>();
		switch (casillero.getTipoCasillero()) {

		// si es del tipo calle, llamamos al ótro método implementado.
		case C_CALLE:
			List<CasilleroCalle> casilleroCalles = this
					.getMonopolio((CasilleroCalle) casillero);
			casilleros.addAll(casilleroCalles);
			break;

		// si es compañia o estación, devolvemos todos los casilleros del mismo
		// tipo
		case C_COMPANIA:
		case C_ESTACION:
			for (Casillero c : this.tablero.getCasillerosList()) {
				if (c.getTipoCasillero() == casillero.getTipoCasillero())
					casilleros.add(c);
			}
			break;

		// cualquiero otro tipo devolvemos null
		case C_SALIDA:
		case C_CARCEL:
		case C_COMUNIDAD:
		case C_DESCANSO:
		case C_IMPUESTO:
		case C_SUERTE:
		case C_IRACARCEL:
		default:
			return null;
		}
		return casilleros;
	}

	/**
	 * Determina si el {@code jugador} es el &uacute;nico poseedor del monopolio
	 * del casillero
	 * 
	 * @param casillero
	 *            El casillero que representa el monopolio
	 * @param jugador
	 *            El jugador que se desea averiguar
	 * @return {@code true} si el {@code Jugador} es el &uacute;nico poseedor
	 *         del monopolio.
	 */
	public boolean esUnicoPoseedorMonopolio(Casillero casillero, Jugador jugador) {
		return (this.propNoCompradasmonopolio(casillero, jugador) > 0) ? true
				: false;
	}

	/**
	 * Determina si es la &uacute;ltima propiedad del monopolio que queda sin
	 * comprar.
	 * 
	 * @param casillero
	 *            El Casillero que se quiere averiguar
	 * @param jugador
	 *            El jugador que quiere comprar
	 * @return {@code true} si es la &uacute;ltima propiedad sin comprar y si
	 *         las dem&aacute;s propiedades pertenecen al Jugador
	 */
	public boolean esUltimaPropiedadMonopolio(Casillero casillero,
			Jugador jugador) {
		if (this.propNoCompradasmonopolio(casillero, jugador) == 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Determina la cantidad de casas que le faltan comprar al jugador.
	 * 
	 * @param casillero
	 *            El casillero del monopolio
	 * @param jugador
	 *            El jugadro que se desea comprobar
	 * @return alguno de los sigiuentes valores:
	 *         <ul>
	 *         <li>{@code 0}: El jugador compr&oacute; todas las propiedades del
	 *         monopolio</li>
	 *         <li>{@code >0}: La cantidad de propiedades del monopolio que
	 *         a&uacute;n no fueron compradas</li>
	 *         <li>{@code <0}: Al menos una de las propiedades fue comprada por
	 *         otro jugador, sin importar si el jugador compr&oacute; algunas de
	 *         las propiedades</li>
	 *         </ul>
	 */
	private int propNoCompradasmonopolio(Casillero casillero, Jugador jugador) {

		int cantPropNoCompradas = 0;

		List<Casillero> monopolio = this.getMonopolio(casillero);

		for (Casillero casi : monopolio) {

			switch (casi.getTipoCasillero()) {
			case C_CALLE:
				if (!((CasilleroCalle) casi).getTarjetaCalle().getJugador()
						.equals(jugador))
					return -1;
				if (((CasilleroCalle) casi).getTarjetaCalle().getJugador() == null)
					cantPropNoCompradas++;

			case C_COMPANIA:
				if (!((CasilleroCompania) casi).getTarjetaCompania()
						.getJugador().equals(jugador))
					return -1;
				if (((CasilleroCompania) casi).getTarjetaCompania()
						.getJugador() == null)
					cantPropNoCompradas++;

			case C_ESTACION:
				if (!((CasilleroEstacion) casi).getTarjetaEstacion()
						.getJugador().equals(jugador))
					return -1;
				if (((CasilleroEstacion) casi).getTarjetaEstacion()
						.getJugador() == null)
					cantPropNoCompradas++;

			default:
				return -2;
			}
		}

		return cantPropNoCompradas;
	}

	/**
	 * Comprueba que una calle es construible. Para ello se debe cumplir que no
	 * haya llegado al límite de las edificaciones permitidas ( 1 hotel ), no
	 * incumpla la norma de construcciones escalonadas ( máxima diferencia entre
	 * la propiedad más construida y la menos construida debe ser 1 ) y además
	 * tenga el permiso de construcción (true).
	 * 
	 * @param casillero
	 *            El CasilleroCalle que se quiere comprobar
	 * @return true si se puede construir.
	 */
	public boolean esConstruible(CasilleroCalle casillero) {
		List<CasilleroCalle> monopolio = this.getMonopolio(casillero);

		int maxConstruido = 0;
		int minConstruido = 5;
		Jugador dueno = casillero.getTarjetaCalle().getJugador();

		for (CasilleroCalle casilleroCalle : monopolio) {
			if (casilleroCalle.getTarjetaCalle().isHipotecada())
				return false;
			if (casilleroCalle.getNroCasas() > maxConstruido)
				maxConstruido = casilleroCalle.getNroCasas();
			if (casilleroCalle.getNroCasas() < minConstruido)
				minConstruido = casilleroCalle.getNroCasas();
			if (!dueno.equals(casilleroCalle.getTarjetaCalle().getJugador()))
				return false;

		}
		if (maxConstruido - minConstruido == 0)
			return true;

		if (casillero.getNroCasas() == maxConstruido)
			return false;

		return true;
	}

	/**
	 * Una calle puede vender edificios siempre y cuando no incumpla la regla de
	 * construcciones escalonadas (ver método construir)
	 * 
	 * @param casillero
	 *            El CasilleroCalle que se quiere comprobar
	 * @return true si se puede vender un edificio
	 */
	public boolean puedeVenderEdificio(CasilleroCalle casillero) {
		List<CasilleroCalle> monopolio = this.getMonopolio(casillero);

		int maxConstruido = 0;
		int minConstruido = 5;

		for (CasilleroCalle casilleroCalle : monopolio) {
			if (casilleroCalle.getNroCasas() > maxConstruido)
				maxConstruido = casilleroCalle.getNroCasas();
			if (casilleroCalle.getNroCasas() < minConstruido)
				minConstruido = casilleroCalle.getNroCasas();
		}
		if (maxConstruido - minConstruido == 0)
			return true;

		if (casillero.getNroCasas() == minConstruido)
			return false;

		if (casillero.getNroCasas() == 0)
			return false;

		return true;
	}

	public MonopolyGameStatus evaluarAccionEnCasillero(Jugador pJugador,
			Casillero pCasillero, boolean cobraSalida) throws CondicionInvalidaException {
		boolean isComprado;
		boolean isHipotecable;
		boolean hasCasas;
		boolean hasHoteles;
		int montoAPagar=0; 
		Banco banco;
		Tarjeta tarjetaCasillero;
		AccionEnCasillero accionEnCasillero;
		MonopolyGameStatus monopolyGameStatus;
		
		
		switch (pCasillero.getTipoCasillero().getNombreTipoCasillero())
		{
		case Casillero.CASILLERO_CALLE:
		case Casillero.CASILLERO_COMPANIA:
		case Casillero.CASILLERO_ESTACION:
			banco = getBancoController(pJugador.getJuego()).getBanco();
			tarjetaCasillero = banco.getTarjetaPropiedadByCasillero(pCasillero);
			if(((TarjetaPropiedad)tarjetaCasillero).getJugador() == null){
				
			}
			break;
			
		case Casillero.CASILLERO_CARCEL:
		case Casillero.CASILLERO_IRACARCEL:
			// ir a la carcel.
			break;
		case Casillero.CASILLERO_IMPUESTO:
			//Cobrar impuesto.
			break;
		case Casillero.CASILLERO_SUERTE:
			//Adjuntar tarjeta suerte
			break;
		case Casillero.CASILLERO_COMUNIDAD:
			// Adjuntar tarjeta comunidad
			break;
		case Casillero.CASILLERO_DESCANSO:
		case Casillero.CASILLERO_SALIDA:
			break;
			default:
				throw new CondicionInvalidaException("Tipo de Casillero inexistente.");
		}
		
		
				

		return null;
	}

	/**
	 * Método que elimina una edificación sin realizar ninguna comprobación
	 * previa
	 */
	boolean venderSinComprobar(CasilleroCalle casillero, Jugador jugador) {
		JuegoController juegoController = PartidasController.getInstance()
				.buscarControladorJuego(jugador.getJuego().getUniqueID());
		Banco banco = juegoController.getGestorBanco().getBanco();
		// Si tiene alguna casa/hotel para vender...
		if (casillero.getNroCasas() > 0) {
			// si el casillero tiene un hotel
			if (casillero.getNroCasas() == 5) {
				if (banco.getNroCasas() < 4)
					return false;
				banco.setNroHoteles(banco.getNroHoteles() + 1);
				banco.setNroCasas(banco.getNroCasas() - 4);
				jugador.cobrar(casillero.getTarjetaCalle()
						.getPrecioVentaHotel());
			} else { // en cambio si tienen una o mas casas...
				banco.setNroCasas(banco.getNroCasas() + 1);
				jugador.cobrar(casillero.getTarjetaCalle()
						.getPrecioVentaCadaCasa());
			}
			casillero.setNroCasas(casillero.getNroCasas() - 1);
			return true;
		}
		return false;
	}

	public boolean comprarPropiedad(Jugador jugador, TarjetaPropiedad tarjeta)
			throws SinDineroException, CondicionInvalidaException {

		if (jugador.getDinero() < tarjeta.getValorPropiedad()) {
			throw new SinDineroException("El jugador " + jugador.getNombre()
					+ " no tiene dinero suficiente para comprar la propiedad "
					+ tarjeta.getNombre());
		}

		this.getBancoController(jugador.getJuego()).venderPropiedad(jugador,
				tarjeta);

		return true;
	}

	private BancoController getBancoController(Juego juego) {
		return PartidasController.getInstance()
				.buscarControladorJuego(juego.getUniqueID()).getGestorBanco();
	}

}
