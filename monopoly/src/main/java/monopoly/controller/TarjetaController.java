/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.dao.ITarjetaCompaniaDao;
import monopoly.dao.ITarjetaComunidadDao;
import monopoly.dao.ITarjetaEstacionDao;
import monopoly.dao.ITarjetaSuerteDao;
import monopoly.model.Jugador;
import monopoly.model.tarjetas.Tarjeta;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;
import monopoly.util.ListUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class TarjetaController {

	private List<TarjetaSuerte> tarjetasSuerteList;

	private List<TarjetaComunidad> tarjetasComunidadList;

	private int proximaTarjetaSuerte;

	private int proximaTarjetaComunidad;

	private static ApplicationContext appContext;

	public TarjetaController() {
		tarjetasSuerteList = tarjetasSuerteList();
		tarjetasComunidadList = tarjetasComunidadList();
		this.mezclarTarjetasComunidad();
		this.mezclarTarjetasSuerte();
		this.proximaTarjetaComunidad = 0;
		this.proximaTarjetaSuerte = 0;
		GestorLogs.registrarLog("Creado el gestor de tarjetas");
	}

	private void mezclarTarjetasSuerte() {
		ListUtils.randomizeList(this.tarjetasSuerteList);
		GestorLogs.registrarLog("Tarjetas Suerte Mezcladas");

		for (TarjetaSuerte tarjetaSuerte : tarjetasSuerteList) {
			GestorLogs.registrarDebug(tarjetaSuerte.toString());
		}
	}

	private void mezclarTarjetasComunidad() {
		ListUtils.randomizeList(this.tarjetasComunidadList);
		GestorLogs.registrarLog("Tarjetas Comunidad Mezcladas");

		for (TarjetaComunidad tarjetaComunidad : tarjetasComunidadList) {
			GestorLogs.registrarDebug(tarjetaComunidad.toString());
		}
	}

	/**
	 * Ejecuta la accion indicada en la tarjeta comunidad:
	 * <ol>
	 * <li>PAGA POR TU PÓLIZA DE SEGURO 50 €.</li>
	 * <li>EN TU CUMPLEAÑOS RECIBES DE CADA JUGADOR 10 €.</li>
	 * <li>COLÓCATE EN LA CASILLA DE SALIDA.</li>
	 * <li>PAGA LA FACTURA DEL MÉDICO 50 €.</li>
	 * <li>HAS GANADO EL SEGUNDO PREMIO DE BELLEZA. RECIBE 10 €.</li>
	 * <li>ERROR EN LA BANCA A TU FAVOR. RECIBE 200 €.</li>
	 * <li>VE A LA CÁRCEL. VE DIRECTAMENTE SIN PASAR POR LA CASILLA DE SALIDA Y
	 * SIN COBRAR LOS 200 €.</li>
	 * <li>HACIENDA TE DEVUELVE 20 €.</li>
	 * <li>COBRAS UNA HERENCIA DE 100 €.</li>
	 * <li>RECIBE 100 € POR LOS INTERESES DE TU PLAZO FIJO.</li>
	 * <li>PAGA AL HOSPITAL 100 €.</li>
	 * <li>RETROCEDE HASTA RONDA DE VALENCIA.</li>
	 * <li>QUEDAS LIBRE DE LA CÁRCEL. Esta carta puede venderse o conservarse
	 * hasta que sea utilizada.</li>
	 * <li>LA VENTA DE TUS ACCIONES TE PRODUCE 50 €.</li>
	 * </ol>
	 * 
	 * @param jugador
	 *            el jugador que tiene la tarjeta
	 * @param tarjetaComunidad
	 *            la tarjeta que tiene el jugador
	 * @return true si se pudo ejecutar la accion
	 * 
	 */

	public boolean jugarTarjetaComunidad(Jugador jugador,
			Tarjeta tarjetaComunidad) {
		switch (((TarjetaComunidad) tarjetaComunidad).getIdTarjeta()) {
		/**
		 * case 1: return banco.pagar(jugador, 50); case 2: return
		 * banco.cobrarATodosPagarAUno(juego.getJugadoresList(), jugador, 10);
		 * case 3: return (juego.getTablero().moverACasillero(jugador, 1, true)
		 * != null);// salida case 4: return banco.pagar(jugador, 50); case 5:
		 * return banco.cobrar(jugador, 10); case 6: return
		 * banco.cobrar(jugador, 200); case 7: return
		 * (juego.getTablero().irACarcel(jugador) != null); case 8: return
		 * banco.pagar(jugador, 20); case 9: return banco.pagar(jugador, 100);
		 * case 10: return banco.pagar(jugador, 100); case 11: return
		 * banco.cobrar(jugador, 100); case 12: return
		 * (juego.getTablero().retrocederA(jugador, 2) != null); // TODO: como
		 * hago aca? case 13:
		 * jugador.getTarjetaCarcelList().add(tarjetaComunidad); return true;
		 * case 14: return banco.pagar(jugador, 50);
		 */
		default:
			return false;
		}
	}

	/**
	 * Ejecuta la accion indicada en la tarjeta suerte:
	 * <ol>
	 * <li>VE AL PASEO DEL PRADO.</li>
	 * <li>VE A LA GLORIETA DE BILBAO. SI PASAS POR LA CASILLA DE SALIDA COBRA
	 * 200 €.</li>
	 * <li>LA BANCA TE PAGA 50 € DE INTERESES.</li>
	 * <li>COLÓCATE EN LA CASILLA DE SALIDA.</li>
	 * <li>ADELANTATE HASTA LA CALLE CEA BERMÚDEZ. SI PASAS POR LA CASILLA DE
	 * SALIDA, COBRA 200 €.</li>
	 * <li>RECIBES EL RESCATE POR EL SEGURO DE TUS EDIFICIOS. COBRA 150 €.</li>
	 * <li>VE A LA CÁRCEL. VE DIRECTAMENTE SIN PASAR POR LA CASILLA DE SALIDA Y
	 * SIN COBRAR LOS 200 €.</li>
	 * <li>MULTA POR EMBRIAGUEZ 20 €.</li>
	 * <li>RETROCEDE TRES CASILLAS.</li>
	 * <li>HAZ REPARACIONES EN TODOS TUS EDIFICIOS. PAGA POR CADA CASA 25 €.
	 * PAGA POR CADA HOTEL 100 €.</li>
	 * <li>LA INSPECCIÓN DE LA CALLE TE OBLIGA A REPARACIONES. PAGA 40 € POR
	 * CADA CASA. PAGA 115 € POR HOTEL.</li>
	 * <li>QUEDAS LIBRE DE LA CÁRCEL. Esta carta puede venderse o conservarse
	 * hasta que sea utilizada.</li>
	 * <li>PAGA POR GASTOS ESCOLARES 150 €.</li>
	 * <li>VE A LA ESTACIÓN DE LAS DELICIAS. SI PASAS POR LA CASILLA DE SALIDA,
	 * COBRA 200 €.</li>
	 * </ol>
	 * 
	 * @param jugador
	 *            el jugador que tiene la tarjeta
	 * @param tarjetaSuerte
	 *            la tarjeta que tiene el jugador
	 * @return true si se pudo ejecutar la accion
	 */

	public boolean jugarTarjetaSuerte(Jugador jugador, Tarjeta tarjetaSuerte) {
		switch (((TarjetaComunidad) tarjetaSuerte).getIdTarjeta()) {
		/*
		 * case 1: return (juego.getTablero().moverACasillero(jugador, 40,
		 * false) != null);// del // prado case 2: return
		 * (juego.getTablero().moverACasillero(jugador, 12, true) != null);//
		 * glorieta // de // bilbao case 3: return banco.pagar(jugador, 50);
		 * case 4: return (juego.getTablero().moverACasillero(jugador, 1, true)
		 * != null);// salida case 5: return
		 * (juego.getTablero().moverACasillero(jugador, 25, true) != null);//
		 * calle // bermudez case 6: return banco.pagar(jugador, 150); case 7:
		 * return (juego.getTablero().irACarcel(jugador) != null); case 8:
		 * return banco.cobrar(jugador, 20); case 9: return
		 * (juego.getTablero().moverAtras(jugador, 3) != null); case 10: return
		 * banco.cobrarPorCasaYHotel(jugador, 25, 100); case 11: return
		 * banco.cobrarPorCasaYHotel(jugador, 40, 115); // TODO: como hago aca?
		 * case 12: jugador.getTarjetaCarcelList().add(tarjetaSuerte); return
		 * true; case 13: return banco.cobrar(jugador, 150); case 14: return
		 * (juego.getTablero().moverACasillero(jugador, 16, true) != null);//
		 * las
		 */// delicias
		default:
			return false;
		}
	}

	public TarjetaSuerte getNextTarjetaSuerte() {
		TarjetaSuerte tmpTarjetaSuerte = this.tarjetasSuerteList
				.get(this.proximaTarjetaSuerte);

		this.proximaTarjetaSuerte++;

		if (this.proximaTarjetaSuerte >= this.tarjetasSuerteList.size())
			this.proximaTarjetaSuerte = 0;

		return tmpTarjetaSuerte;
	}

	public TarjetaComunidad getNextTarjetaComunidad() {
		TarjetaComunidad tmpTarjetaComunidad = this.tarjetasComunidadList
				.get(this.proximaTarjetaComunidad);

		this.proximaTarjetaComunidad++;

		if (this.proximaTarjetaComunidad >= this.tarjetasComunidadList.size())
			this.proximaTarjetaComunidad = 0;

		return tmpTarjetaComunidad;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("Tarjetas { ");

		sb.append("\nTarjetasComunidad [ ");
		for (TarjetaComunidad tarjetaComunidad : tarjetasComunidadList) {
			sb.append("\nidTarjeta=");
			sb.append(tarjetaComunidad.getIdTarjeta());
			sb.append(", objetivo=");
			sb.append(tarjetaComunidad.getObjetivo());
		}
		sb.append(" ]");

		sb.append("\nTarjetasSuerte [ ");
		for (TarjetaSuerte tarjetaSuerte : tarjetasSuerteList) {
			sb.append("\nidTarjeta=");
			sb.append(tarjetaSuerte.getIdTarjeta());
			sb.append(", objetivo=");
			sb.append(tarjetaSuerte.getObjetivo());
		}

		sb.append(" ] ");
		sb.append(" }");

		return sb.toString();
	}

	/**
	 * devuelve todas las tarjetas de la suerte de la base de datos.
	 * 
	 * @return una lista de tarjetas de la suerte
	 */
	public static List<TarjetaSuerte> tarjetasSuerteList() {
		appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaSuerteDao tarjetaSuerteDao = (ITarjetaSuerteDao) appContext
				.getBean("tarjetaSuerteDao");

		GestorLogs.registrarLog("Tarjetas Suerte Cargadas..");
		return tarjetaSuerteDao.getAll();
	}

	/**
	 * devuelve todas las tarjetas de la caja de la comunidad de la base de
	 * datos.
	 * 
	 * @return una lista detarjeta de la caja de la comunidad
	 */
	public static List<TarjetaComunidad> tarjetasComunidadList() {
		appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaComunidadDao tarjetaComunidadDao = (ITarjetaComunidadDao) appContext
				.getBean("tarjetaComunidadDao");

		GestorLogs
				.registrarLog("Tarjetas de la Caja de la Comunidad Cargadas..");
		return tarjetaComunidadDao.getAll();
	}

	/*
	 * devuelve todas las tarjetas de propiedades de la base de datos.
	 * 
	 * @return una lista de tarjetas de propiedades.
	 */
	public static List<TarjetaPropiedad> tarjetasPropiedadesList() {
		List<TarjetaPropiedad> list = new ArrayList<TarjetaPropiedad>();

		ApplicationContext appCon = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaCalleDao callesDao = (ITarjetaCalleDao) appCon
				.getBean("tarjetaCalleDao");
		ITarjetaCompaniaDao companiasDao = (ITarjetaCompaniaDao) appCon
				.getBean("tarjetaCompaniaDao");
		ITarjetaEstacionDao estacionesDao = (ITarjetaEstacionDao) appCon
				.getBean("tarjetaEstacionDao");

		list.addAll(callesDao.getAll());
		list.addAll(companiasDao.getAll());
		list.addAll(estacionesDao.getAll());

		return list;
	}

	/**
	 * devuelve un TreeMap con key; nombre del a propiedad y value, la
	 * propiedad.
	 * 
	 * @return un TreeMap de tarjetas de propiedades
	 */
	public static TreeMap<String, TarjetaPropiedad> getTarjetasPropiedadesTreeMap() {
		TreeMap<String, TarjetaPropiedad> tarjetaPropiedad = new TreeMap<String, TarjetaPropiedad>();
		List<TarjetaPropiedad> list = new ArrayList<TarjetaPropiedad>();

		ApplicationContext appCon = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaCalleDao callesDao = (ITarjetaCalleDao) appCon
				.getBean("tarjetaCalleDao");
		ITarjetaCompaniaDao companiasDao = (ITarjetaCompaniaDao) appCon
				.getBean("tarjetaCompaniaDao");
		ITarjetaEstacionDao estacionesDao = (ITarjetaEstacionDao) appCon
				.getBean("tarjetaEstacionDao");

		list.addAll(callesDao.getAll());
		list.addAll(companiasDao.getAll());
		list.addAll(estacionesDao.getAll());

		for (TarjetaPropiedad tp : list) {
			tarjetaPropiedad.put(tp.getNombrePropiedad(), tp);
		}

		return tarjetaPropiedad;
	}
}
