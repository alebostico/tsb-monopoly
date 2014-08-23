package monopoly.controller;

import java.util.List;

import monopoly.dao.IFichaDao;
import monopoly.model.Ficha;
import monopoly.util.GestorLogs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FichasController {

	// public static final String F_AUTO = "auto";
	// public static final String F_SOMBRERO = "sombrero";
	// public static final String F_BOTA = "bota";
	// public static final String F_PLANCHA = "plancha";
	// public static final String F_CARRETILLA = "carretilla";
	// public static final String F_DEDAL = "dedal";
	// public static final String F_BARCO = "barco";
	// public static final String F_PERRO = "perro";
	// public static final String F_BOLSA_DINERO = "bolsa de dinero";
	// public static final String F_CABALLO = "caballo";
	// public static final String F_CANON = "cañón";

	public List<Ficha> fichas;

	/**
	 * Constructor por defecto. Crea las fichas.
	 */
	public FichasController() {
		super();
		this.cargarFichas();

	}

	private List<Ficha> cargarFichas() {

		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");

		// Cargar las tarjetas de Comunidad
		IFichaDao fichaDao = (IFichaDao) appContext.getBean("fichaDao");
		this.fichas = fichaDao.getAll();

		GestorLogs.registrarLog("Fichas cargadas");
		if (GestorLogs.getLoggingDetailLevel() >= GestorLogs.MSG_DEBUG)
			GestorLogs.registrarDebug(this.toString());

		return this.fichas;
	}

	/**
	 * Devuelve la ficha que tiene el nombre pasado por parámetro. El nombre
	 * debe ser alguna de las constantes estáticas de la clase Ficha:
	 * <ul>
	 * <li>Ficha.TIPO_CARRETILLA</li>
	 * <li>Ficha.TIPO_AUTO</li>
	 * <li>Ficha.TIPO_SOMBRERO</li>
	 * <li>Ficha.TIPO_BOTA</li>
	 * <li>Ficha.TIPO_PLANCHA</li>
	 * <li>Ficha.TIPO_CARRETILLA</li>
	 * <li>Ficha.TIPO_DEDAL</li>
	 * <li>Ficha.TIPO_BARCO</li>
	 * <li>Ficha.TIPO_PERRO</li>
	 * <li>Ficha.TIPO_BOLSA_DINERO</li>
	 * <li>Ficha.TIPO_CABALLO</li>
	 * <li>Ficha.TIPO_CANON</li>
	 * </ul>
	 * 
	 * @param nombreFicha
	 *            El nombre de la ficha
	 * @return La instancia de la clase Ficha o null si no se encuentra una
	 *         ficha con ese nombre.
	 */
	public Ficha getFicha(String nombreFicha) {
		for (Ficha ficha : this.fichas) {
			if (ficha.getNombre().compareTo(nombreFicha) == 0) {
				return ficha;
			}
		}
		return null;
	}
	
	public String toString() {

		StringBuilder sb = new StringBuilder("GestorFichas [ ");

		sb.append("fichas=");
		for (Ficha ficha : this.fichas) {
			sb.append("'");
			sb.append(ficha.getNombre());
			sb.append("' ");

		}

		sb.append("]");
		return sb.toString();
	}
}
