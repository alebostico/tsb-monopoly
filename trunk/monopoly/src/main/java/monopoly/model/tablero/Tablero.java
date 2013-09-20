/**
 * 
 */
package monopoly.model.tablero;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.dao.ITarjetaCompaniaDao;
import monopoly.dao.ITarjetaEstacionDao;
import monopoly.model.Banco;
import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Clase que representa al tablero del juego. Mantiene una inastancia de cada
 * casillero.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Tablero {

	// CONSTANTES
	private int cantCasilleros = 40;

	private Casillero[] casillerosList;

	// para cuando el jugador tiene que cobrar los $200 de la salida
	private Banco banco;

	/**
	 * Constructor por defecto.
	 */
	public Tablero(Banco banco) {
		// Cargar los casilleros.
		casillerosList = new Casillero[40];

		this.banco = banco;

		this.loadCasilleros();

		GestorLogs.registrarLog("Tablero cargado");
	}

	/**
	 * Método que genera los casilleros del tablero. Probablemente al pedo
	 * porque lo más seguro es que los carguemos directamente desde la base de
	 * datos. Pero para pruebas sirve.
	 */
	private void loadCasilleros() {

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaCalleDao tarjetaCalleDao = (ITarjetaCalleDao) appContext
				.getBean("tarjetaCalleDao");
		ITarjetaCompaniaDao tarjetaCompaniaDao = (ITarjetaCompaniaDao) appContext
				.getBean("tarjetaCompaniaDao");
		ITarjetaEstacionDao tarjetaEstacionDao = (ITarjetaEstacionDao) appContext
				.getBean("tarjetaEstacionDao");

		this.casillerosList[0] = new Casillero(1, Casillero.CASILLERO_SALIDA);
		this.casillerosList[1] = new CasilleroCalle(2, "Ronda de Valencia",
				tarjetaCalleDao.findByNombre("RONDA DE VALENCIA"));
		this.casillerosList[2] = new Casillero(3, Casillero.CASILLERO_COMUNIDAD);
		this.casillerosList[3] = new CasilleroCalle(4, "Plaza Lavapiés",
				tarjetaCalleDao.findByNombre("PLAZA DE LAVAPIÉS"));
		this.casillerosList[4] = new Casillero(5, Casillero.CASILLERO_IMPUESTO);
		this.casillerosList[5] = new CasilleroEstacion(6, "Estación de Goya",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DE GOYA"));
		this.casillerosList[6] = new CasilleroCalle(7,
				"Glorieta Cuatro Caminos",
				tarjetaCalleDao.findByNombre("GLORIETA CUATRO CAMINOS"));
		this.casillerosList[7] = new Casillero(8, Casillero.CASILLERO_SUERTE);
		this.casillerosList[8] = new CasilleroCalle(9,
				"Avenida Reina Victoria",
				tarjetaCalleDao.findByNombre("AVENIDA REINA VICTORIA"));
		this.casillerosList[9] = new CasilleroCalle(10, "Calle Bravo Murillo",
				tarjetaCalleDao.findByNombre("CALLE BRAVO MURILLO"));

		this.casillerosList[10] = new Casillero(11, Casillero.CASILLERO_CARCEL);
		this.casillerosList[11] = new CasilleroCalle(12, "Glorieta de Biblbao",
				tarjetaCalleDao.findByNombre("GLORIETA DE BILBAO"));
		this.casillerosList[12] = new CasilleroCompania(13,
				"Compañía de Electricidad",
				tarjetaCompaniaDao.findByNombre("COMPAÑIA DE ELECTRICIDAD"));
		this.casillerosList[13] = new CasilleroCalle(14,
				"Calle Alberto Aguilera",
				tarjetaCalleDao.findByNombre("CALLE ALBERTO AGUILERA"));
		this.casillerosList[14] = new CasilleroCalle(15, "Calle Fuencarral",
				tarjetaCalleDao.findByNombre("CALLE FUENCARRAL"));
		this.casillerosList[15] = new CasilleroEstacion(16,
				"Estación de las Delicias",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DE LAS DELICIAS"));
		this.casillerosList[16] = new CasilleroCalle(17, "Avenida Felipe II",
				tarjetaCalleDao.findByNombre("AVENIDA FELIPE II"));
		this.casillerosList[17] = new Casillero(18,
				Casillero.CASILLERO_COMUNIDAD);
		this.casillerosList[18] = new CasilleroCalle(19, "Calle Velázquez",
				tarjetaCalleDao.findByNombre("CALLE VELÁZQUEZ"));
		this.casillerosList[19] = new CasilleroCalle(20, "Calle Serrano",
				tarjetaCalleDao.findByNombre("CALLE SERRANO"));

		this.casillerosList[20] = new Casillero(21,
				Casillero.CASILLERO_DESCANSO);
		this.casillerosList[21] = new CasilleroCalle(22, "Avenida de América",
				tarjetaCalleDao.findByNombre("AVENIDA AMÉRICA"));
		this.casillerosList[22] = new Casillero(23, Casillero.CASILLERO_SUERTE);
		this.casillerosList[23] = new CasilleroCalle(24,
				"Calle María de Molina",
				tarjetaCalleDao.findByNombre("CALLE MARÍA DE MOLINA"));
		this.casillerosList[24] = new CasilleroCalle(25, "Calle Cea Bermúdez",
				tarjetaCalleDao.findByNombre("CALLE CEA BERMÚDEZ"));
		this.casillerosList[25] = new CasilleroEstacion(26,
				"Estación del Mediodía",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DEL MEDIODÍA"));
		this.casillerosList[26] = new CasilleroCalle(27,
				"Avenida de los Reyes Católicos",
				tarjetaCalleDao.findByNombre("AVENIDA DE LOS REYES CATÓLICOS"));
		this.casillerosList[27] = new CasilleroCalle(28, "Calle Bailén",
				tarjetaCalleDao.findByNombre("CALLE BAILÉN"));
		this.casillerosList[28] = new CasilleroCompania(29,
				"Compañía de Aguas",
				tarjetaCompaniaDao.findByNombre("COMPAÑIA DE AGUAS"));
		this.casillerosList[29] = new CasilleroCalle(30, "Plaza de España",
				tarjetaCalleDao.findByNombre("PLAZA ESPAÑA"));

		this.casillerosList[30] = new Casillero(31,
				Casillero.CASILLERO_IRACARCEL);
		this.casillerosList[31] = new CasilleroCalle(32, "Puerta del Sol",
				tarjetaCalleDao.findByNombre("PUERTA DEL SOL"));
		this.casillerosList[32] = new CasilleroCalle(33, "Calle Alcalá",
				tarjetaCalleDao.findByNombre("CALLE ALCALÁ"));
		this.casillerosList[33] = new Casillero(34,
				Casillero.CASILLERO_COMUNIDAD);
		this.casillerosList[34] = new CasilleroCalle(35, "Gran Vía",
				tarjetaCalleDao.findByNombre("GRAN VÍA"));
		this.casillerosList[35] = new CasilleroEstacion(36,
				"Estación del Norte",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DEL NORTE"));
		this.casillerosList[36] = new Casillero(37, Casillero.CASILLERO_SUERTE);
		this.casillerosList[37] = new CasilleroCalle(38,
				"Paseo de la Castellana",
				tarjetaCalleDao.findByNombre("PASEO DE LA CASTELLANA"));
		this.casillerosList[38] = new Casillero(39,
				Casillero.CASILLERO_IMPUESTO);
		this.casillerosList[39] = new CasilleroCalle(40, "Paseo del Prado",
				tarjetaCalleDao.findByNombre("PASEO DEL PRADO"));

		GestorLogs.registrarLog("Casilleros cargados");

		if (GestorLogs.getLoggingDetailLevel() == GestorLogs.MSG_DEBUG_DETAIL) {
			// loguea info de los casilleros en forma detallada
			StringBuilder mensaje = new StringBuilder("Tablero:\n");

			for (Casillero casillero : this.casillerosList) {
				switch (casillero.getTipoCasillero()) {
				case Casillero.CASILLERO_CALLE:
					mensaje.append(((CasilleroCalle) casillero).toString());
					break;
				case Casillero.CASILLERO_COMPANIA:
					mensaje.append(((CasilleroCompania) casillero).toString());
					break;
				case Casillero.CASILLERO_ESTACION:
					mensaje.append(((CasilleroEstacion) casillero).toString());
					break;
				default:
					mensaje.append(casillero.toString());
					break;
				}
				mensaje.append("\n");
			}
			GestorLogs.registrarDebugDetail(mensaje.toString());
		} else if (GestorLogs.getLoggingDetailLevel() == GestorLogs.MSG_DEBUG) {
			// Loguea info de los casilleros en forma resumida
			GestorLogs.registrarDebug(this.toString());
		}

	}

	/**
	 * Devuelve el casillero pasado por parámetro. El parámetro es el Número de
	 * casillero [1-40], NO el indice del vector. Si el 'nroCasillero' es
	 * inválido (menor a 1 o mayor a 40) devuelve null.
	 * 
	 * @param nroCasillero
	 *            El número del casillero a devolver.
	 * @return El casillero correspondiente al número pasado por parámetro o
	 *         null si no existe.
	 */
	public Casillero getCasillero(int nroCasillero) {
		/*
		 * TODO: estaría bueno que si el casillero no existe porque pasa un
		 * nroCasillero incorrecto, en vez de retornar null tire una excepción
		 * CasilleroInvalidoException por ejemplo
		 */

		if (nroCasillero > this.cantCasilleros || nroCasillero < 1)
			return null;

		// Creo que este casteo no es necesario porque afuera hay que volver a
		// preguntar y castear
		// switch (this.casillerosList[nroCasillero + 1].getTipoCasillero()) {
		// case Casillero.CASILLERO_CALLE:
		// return (CasilleroCalle) this.casillerosList[nroCasillero + 1];
		// case Casillero.CASILLERO_COMPANIA:
		// return (CasilleroCompania) this.casillerosList[nroCasillero + 1];
		// case Casillero.CASILLERO_ESTACION:
		// return (CasilleroEstacion) this.casillerosList[nroCasillero + 1];
		// default:
		// return this.casillerosList[nroCasillero + 1];
		// }

		 return this.casillerosList[nroCasillero + 1];
	}

	/**
	 * Devuelve el Casillero de la calle/Estación/Compañía que tiene el nombre
	 * 'nombreCasillero' o null si el nombre no existe.
	 * 
	 * @param nombreCasillero
	 *            El nombre del casillero.
	 * @return El Casillero con el nombre pasado por parámetro o null si la
	 *         calle no existe.
	 */
	public Casillero getCasillero(String nombreCasillero) {

		/*
		 * TODO: estaría bueno que si el casillero no existe porque el nombre de
		 * la calle no existe, en vez de retornar null tire una excepción
		 * NombreInvalidoException por ejemplo
		 */

		Casillero casilleroActual;

		for (int i = 1; i <= this.cantCasilleros; i++) {
			casilleroActual = this.getCasillero(i);

			if (casilleroActual.getTipoCasillero() == Casillero.CASILLERO_CALLE) {
				if (((CasilleroCalle) casilleroActual).getNombreCalle()
						.compareTo(nombreCasillero) == 0) {
					return casilleroActual;
				}
			}

			if (casilleroActual.getTipoCasillero() == Casillero.CASILLERO_COMPANIA) {
				if (((CasilleroCompania) casilleroActual).getNombreCompania()
						.compareTo(nombreCasillero) == 0) {
					return casilleroActual;
				}
			}

			if (casilleroActual.getTipoCasillero() == Casillero.CASILLERO_ESTACION) {
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
	 * atrás si 'cantCasilleros' es negativo) y devuelve el Casillero en el que
	 * cayo. Si el movimiento es hacia adelante, el jugador pasa por la salida y
	 * el parametro 'cobraSalida' es true, cobra los $200
	 * 
	 * @param jugador
	 *            El jugador que se quiere mover.
	 * @param cantCasilleros
	 *            La cantidad de casilleros a mover el jugador. Si es positivo
	 *            mueve hacia adelante. Si es negativo hacia atras (es lo mismo
	 *            que llamar al método 'moverAtras').
	 * @param cobraSalida
	 *            true en el caso que el jugador deba cobrar los $200 si pasa
	 *            por la salida. false si no los cobra.
	 * @return El casillero al cual se movió el jugador.
	 */
	public Casillero moverAdelante(Jugador jugador, int cantCasilleros,
			boolean cobraSalida) {

		boolean cobroSalida = false;
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
			if (cobraSalida) {
				// ... los cobra
				this.banco.cobrar(jugador, 200);
				cobroSalida = true;
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

		this.registrarInfo(jugador, casilleroSiguiente, cobroSalida);

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
	 *            llamar al método 'moverAdelante' con 'cantCasilleros'
	 *            negativa).
	 * @return El casillero al cual se movió el jugador.
	 */
	public Casillero moverAtras(Jugador jugador, int cantCasilleros) {
		return this.moverAdelante(jugador, (cantCasilleros * (-1)), false);
	}

	/**
	 * Mueve el jugador 'jugador' al casillero con el número 'nroCasillero'.
	 * Retorna el Casillero al cual se movio al jugador o null si no existe.
	 * 
	 * @param jugador
	 *            El jugador que se quiere mover.
	 * @param nroCasillero
	 *            El número de casillero al cual se quiere mover el jugador.
	 * @param cobraSalida
	 *            true en el caso que el jugador deba cobrar los $200 si pasa
	 *            por la salida. false si no los cobra.
	 * @return El casillero al cual se movió el jugador si 'nroCasillero' es
	 *         válido (menor a 1 o mayor a 40). null en caso contrario.
	 */
	public Casillero moverACasillero(Jugador jugador, int nroCasillero,
			boolean cobraSalida) {
		/*
		 * TODO: estaría bueno que si el casillero no existe porque pasa un
		 * nroCasillero incorrecto, en vez de retornar null tire una excepción
		 * CasilleroInvalidoException por ejemplo
		 */
		Casillero casilleroActual = jugador.getCasilleroActual();
		Casillero casilleroSiguiente = this.getCasillero(nroCasillero);

		boolean cobroSalida = false;

		// si el nroCasillero es inválido (menor a 1 o mayor a 40) retorna
		// null...
		if (casilleroSiguiente == null)
			return null;

		// si pasa por la salida para ir de un casillero a otro...
		if (casilleroSiguiente.getNumeroCasillero() < casilleroActual
				.getNumeroCasillero()) {
			// y si el jugador tiene que cobrar los $200 en caso de pasar por la
			// salida...
			if (cobraSalida) {
				// ... los cobra
				this.banco.cobrar(jugador, 200);
				cobroSalida = true;
			}
		}

		casilleroActual.removeJugador(jugador);
		casilleroSiguiente.addJugador(jugador);

		jugador.setCasilleroActual(casilleroSiguiente);

		this.registrarInfo(jugador, casilleroSiguiente, cobroSalida);

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
	 * @return El casillero al cual se movió el jugador si 'nombreCasillero'
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
	 *            El número de casillero al cual se mueve.
	 * @return El casillero en el que queda el jugador.
	 */
	public Casillero retrocederA(Jugador jugador, int nroCasillero) {
		return this.moverACasillero(jugador, nroCasillero, false);
	}

	public String toString() {

		StringBuffer toReturn = new StringBuffer();
		toReturn.append("\n===============================================================================================\n");
		toReturn.append("|                                         TABLERO                                             |\n");
		toReturn.append("===============================================================================================\n");
		toReturn.append("| Nro |   Tipo Casillero    |        Nombre Casillero        |         Nombre Tarjeta         |\n");
		toReturn.append("|-----|---------------------|--------------------------------|--------------------------------|\n");

		for (int i = 0; i < this.cantCasilleros; i++) {
			// Nro =================================================
			toReturn.append("| ");
			toReturn.append(String.format("%02d",
					this.casillerosList[i].getNumeroCasillero()));
			toReturn.append("  ");

			// Tipo Casillero ======================================
			toReturn.append("| ");
			toReturn.append(String.format("%1$19s",
					this.casillerosList[i].getTipoCasilleroString()));
			toReturn.append(" ");

			// Nombre Casillero ====================================
			switch (this.casillerosList[i].getTipoCasillero()) {
			case Casillero.CASILLERO_CALLE:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s",
						((CasilleroCalle) this.casillerosList[i])
								.getNombreCalle()));
				toReturn.append(" ");
				break;

			case Casillero.CASILLERO_ESTACION:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s",
						((CasilleroEstacion) this.casillerosList[i])
								.getNombreEstacion()));
				toReturn.append(" ");
				break;

			case Casillero.CASILLERO_COMPANIA:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s",
						((CasilleroCompania) this.casillerosList[i])
								.getNombreCompania()));
				toReturn.append(" ");
				break;

			default:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s", ""));
				toReturn.append(" ");
				break;
			}

			// Nombre Tarjeta ======================================
			TarjetaPropiedad tarjeta;

			switch (this.casillerosList[i].getTipoCasillero()) {
			case Casillero.CASILLERO_CALLE:
				tarjeta = ((CasilleroCalle) this.casillerosList[i])
						.getTarjetaCalle();

				if (tarjeta != null) {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((TarjetaCalle) tarjeta).getNombre()));
					toReturn.append(" ");
				} else {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							"<ERROR: TARJETA NOT FOUND>"));
					toReturn.append(" ");
				}
				break;
			case Casillero.CASILLERO_ESTACION:
				tarjeta = ((CasilleroEstacion) this.casillerosList[i])
						.getTarjetaEstacion();

				if (tarjeta != null) {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((TarjetaEstacion) tarjeta).getNombre()));
					toReturn.append(" ");
				} else {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							"<ERROR: TARJETA NOT FOUND>"));
					toReturn.append(" ");
				}
				break;
			case Casillero.CASILLERO_COMPANIA:
				tarjeta = ((CasilleroCompania) this.casillerosList[i])
						.getTarjetaCompania();

				if (tarjeta != null) {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((TarjetaCompania) tarjeta).getNombre()));
					toReturn.append(" ");
				} else {
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							"<ERROR: TARJETA NOT FOUND>"));
					toReturn.append(" ");
				}
				break;

			default:
				toReturn.append("| ");
				toReturn.append(String.format("%1$30s", ""));
				toReturn.append(" ");
				break;
			}

			toReturn.append("|\n");
		}

		toReturn.append("===============================================================================================\n");

		return toReturn.toString();
	}

	private void registrarInfo(Jugador jugador, Casillero casillero,
			boolean cobroSalida) {
		GestorLogs.registrarLog("El Jugador '"
				+ jugador.getFicha().getNombre()
				+ "' se movio al casillero #"
				+ casillero.getNumeroCasillero()
				+ ((cobroSalida) ? " y cobro $200 en la salida"
						: " y NO cobro $200 en la salida"));

	}
}
