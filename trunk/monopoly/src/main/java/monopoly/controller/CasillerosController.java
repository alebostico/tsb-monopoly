/**
 * 
 */
package monopoly.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.dao.ITarjetaCompaniaDao;
import monopoly.dao.ITarjetaEstacionDao;
import monopoly.model.tablero.Casillero;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.CasilleroCompania;
import monopoly.model.tablero.CasilleroEstacion;
import monopoly.model.tablero.Casillero.TipoCasillero;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CasillerosController {

	private static ApplicationContext appContext;

	private final static int cantCasilleros = 40;

	/**
	 * Método que genera los casilleros del tablero. Probablemente al pedo
	 * porque lo m&aacute;s seguro es que los carguemos directamente desde la
	 * base de datos. Pero para pruebas sirve.
	 */
	public static Casillero[] getCasilleros() {
		Casillero[] vCasilleros = new Casillero[40];

		appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaCalleDao tarjetaCalleDao = (ITarjetaCalleDao) appContext
				.getBean("tarjetaCalleDao");
		ITarjetaCompaniaDao tarjetaCompaniaDao = (ITarjetaCompaniaDao) appContext
				.getBean("tarjetaCompaniaDao");
		ITarjetaEstacionDao tarjetaEstacionDao = (ITarjetaEstacionDao) appContext
				.getBean("tarjetaEstacionDao");

		vCasilleros[0] = new Casillero(1, TipoCasillero.C_SALIDA);
		vCasilleros[1] = new CasilleroCalle(2, "Ronda de Valencia",
				tarjetaCalleDao.findByNombre("RONDA DE VALENCIA"));
		vCasilleros[2] = new Casillero(3, TipoCasillero.C_COMUNIDAD);
		vCasilleros[3] = new CasilleroCalle(4, "Plaza Lavapiés",
				tarjetaCalleDao.findByNombre("PLAZA DE LAVAPIÉS"));
		vCasilleros[4] = new Casillero(5, TipoCasillero.C_IMPUESTO);
		vCasilleros[5] = new CasilleroEstacion(6, "Estación de Goya",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DE GOYA"));
		vCasilleros[6] = new CasilleroCalle(7, "Glorieta Cuatro Caminos",
				tarjetaCalleDao.findByNombre("GLORIETA CUATRO CAMINOS"));
		vCasilleros[7] = new Casillero(8, TipoCasillero.C_SUERTE);
		vCasilleros[8] = new CasilleroCalle(9, "Avenida Reina Victoria",
				tarjetaCalleDao.findByNombre("AVENIDA REINA VICTORIA"));
		vCasilleros[9] = new CasilleroCalle(10, "Calle Bravo Murillo",
				tarjetaCalleDao.findByNombre("CALLE BRAVO MURILLO"));
		vCasilleros[10] = new Casillero(11, TipoCasillero.C_CARCEL);
		vCasilleros[11] = new CasilleroCalle(12, "Glorieta de Biblbao",
				tarjetaCalleDao.findByNombre("GLORIETA DE BILBAO"));
		vCasilleros[12] = new CasilleroCompania(13, "Compañia de Electricidad",
				tarjetaCompaniaDao.findByNombre("COMPAÑIA DE ELECTRICIDAD"));
		vCasilleros[13] = new CasilleroCalle(14, "Calle Alberto Aguilera",
				tarjetaCalleDao.findByNombre("CALLE ALBERTO AGUILERA"));
		vCasilleros[14] = new CasilleroCalle(15, "Calle Fuencarral",
				tarjetaCalleDao.findByNombre("CALLE FUENCARRAL"));
		vCasilleros[15] = new CasilleroEstacion(16, "Estación de las Delicias",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DE LAS DELICIAS"));
		vCasilleros[16] = new CasilleroCalle(17, "Avenida Felipe II",
				tarjetaCalleDao.findByNombre("AVENIDA FELIPE II"));
		vCasilleros[17] = new Casillero(18, TipoCasillero.C_COMUNIDAD);
		vCasilleros[18] = new CasilleroCalle(19, "Calle Velázquez",
				tarjetaCalleDao.findByNombre("CALLE VELÁZQUEZ"));
		vCasilleros[19] = new CasilleroCalle(20, "Calle Serrano",
				tarjetaCalleDao.findByNombre("CALLE SERRANO"));

		vCasilleros[20] = new Casillero(21, TipoCasillero.C_DESCANSO);
		vCasilleros[21] = new CasilleroCalle(22, "Avenida de América",
				tarjetaCalleDao.findByNombre("AVENIDA AMÉRICA"));
		vCasilleros[22] = new Casillero(23, TipoCasillero.C_SUERTE);
		vCasilleros[23] = new CasilleroCalle(24, "Calle María de Molina",
				tarjetaCalleDao.findByNombre("CALLE MARÍA DE MOLINA"));
		vCasilleros[24] = new CasilleroCalle(25, "Calle Cea Bermúdez",
				tarjetaCalleDao.findByNombre("CALLE CEA BERMÚDEZ"));
		vCasilleros[25] = new CasilleroEstacion(26, "Estación del Mediodí­a",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DEL MEDIODÍA"));
		vCasilleros[26] = new CasilleroCalle(27,
				"Avenida de los Reyes Católicos",
				tarjetaCalleDao.findByNombre("AVENIDA DE LOS REYES CATÓLICOS"));
		vCasilleros[27] = new CasilleroCalle(28, "Calle Bailén",
				tarjetaCalleDao.findByNombre("CALLE BAILÉN"));
		vCasilleros[28] = new CasilleroCompania(29, "Compañia de Aguas",
				tarjetaCompaniaDao.findByNombre("COMPAÑIA DE AGUAS"));
		vCasilleros[29] = new CasilleroCalle(30, "Plaza de España",
				tarjetaCalleDao.findByNombre("PLAZA ESPAÑA"));

		vCasilleros[30] = new Casillero(31, TipoCasillero.C_IRACARCEL);
		vCasilleros[31] = new CasilleroCalle(32, "Puerta del Sol",
				tarjetaCalleDao.findByNombre("PUERTA DEL SOL"));
		vCasilleros[32] = new CasilleroCalle(33, "Calle Alcalá",
				tarjetaCalleDao.findByNombre("CALLE ALCALÁ"));
		vCasilleros[33] = new Casillero(34, TipoCasillero.C_COMUNIDAD);
		vCasilleros[34] = new CasilleroCalle(35, "Gran Ví­a",
				tarjetaCalleDao.findByNombre("GRAN VÍA"));
		vCasilleros[35] = new CasilleroEstacion(36, "Estación del Norte",
				tarjetaEstacionDao.findByNombre("ESTACIÓN DEL NORTE"));
		vCasilleros[36] = new Casillero(37, TipoCasillero.C_SUERTE);
		vCasilleros[37] = new CasilleroCalle(38, "Paseo de la Castellana",
				tarjetaCalleDao.findByNombre("PASEO DE LA CASTELLANA"));
		vCasilleros[38] = new Casillero(39, TipoCasillero.C_IMPUESTO);
		vCasilleros[39] = new CasilleroCalle(40, "Paseo del Prado",
				tarjetaCalleDao.findByNombre("PASEO DEL PRADO"));

		GestorLogs.registrarLog("Casilleros cargados");

		switch (GestorLogs.getLoggingDetailLevel()) {

		case GestorLogs.MSG_DEBUG_DETAIL:
			// loguea info de los casilleros en forma detallada
			StringBuilder mensaje = new StringBuilder("Tablero:\n");

			for (Casillero casillero : vCasilleros) {
				switch (casillero.getTipoCasillero()) {
				case C_CALLE:
					mensaje.append(((CasilleroCalle) casillero).toString());
					break;
				case C_COMPANIA:
					mensaje.append(((CasilleroCompania) casillero).toString());
					break;
				case C_ESTACION:
					mensaje.append(((CasilleroEstacion) casillero).toString());
					break;
				default:
					mensaje.append(casillero.toString());
					break;
				}
				mensaje.append("\n");
			}
			GestorLogs.registrarDebugDetail(mensaje.toString());
			break;

		case GestorLogs.MSG_DEBUG:
			// Loguea info de los casilleros en forma resumida
			StringBuffer toReturn = new StringBuffer();
			toReturn.append("\n===============================================================================================\n");
			toReturn.append("|                                         TABLERO                                             |\n");
			toReturn.append("===============================================================================================\n");
			toReturn.append("| Nro |   Tipo Casillero    |        Nombre Casillero        |         Nombre Tarjeta         |\n");
			toReturn.append("|-----|---------------------|--------------------------------|--------------------------------|\n");

			for (int i = 0; i < cantCasilleros; i++) {
				// Nro =================================================
				toReturn.append("| ");
				toReturn.append(String.format("%02d",
						vCasilleros[i].getNumeroCasillero()));
				toReturn.append("  ");

				// Tipo Casillero ======================================
				toReturn.append("| ");
				toReturn.append(String.format("%1$19s", vCasilleros[i]
						.getTipoCasillero().getNombreTipoCasillero()));
				toReturn.append(" ");

				// Nombre Casillero ====================================
				switch (vCasilleros[i].getTipoCasillero()) {
				case C_CALLE:
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((CasilleroCalle) vCasilleros[i]).getNombreCalle()));
					toReturn.append(" ");
					break;

				case C_ESTACION:
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((CasilleroEstacion) vCasilleros[i])
									.getNombreEstacion()));
					toReturn.append(" ");
					break;

				case C_COMPANIA:
					toReturn.append("| ");
					toReturn.append(String.format("%1$30s",
							((CasilleroCompania) vCasilleros[i])
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

				switch (vCasilleros[i].getTipoCasillero()) {
				case C_CALLE:
					tarjeta = ((CasilleroCalle) vCasilleros[i])
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
				case C_ESTACION:
					tarjeta = ((CasilleroEstacion) vCasilleros[i])
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
				case C_COMPANIA:
					tarjeta = ((CasilleroCompania) vCasilleros[i])
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

			GestorLogs.registrarDebug(toReturn.toString());
			break;
		default:
			break;
		}
		return vCasilleros;
	}
}
