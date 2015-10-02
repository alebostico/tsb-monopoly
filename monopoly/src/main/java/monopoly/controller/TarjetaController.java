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
import monopoly.model.AccionEnTarjeta;
import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;
import monopoly.util.ListUtils;
import monopoly.util.StringUtils;
import monopoly.util.exception.SinDineroException;

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
		this.tarjetasSuerteList = ListUtils
				.randomizeList(this.tarjetasSuerteList);
		GestorLogs.registrarLog("Tarjetas Suerte Mezcladas");

		for (TarjetaSuerte tarjetaSuerte : tarjetasSuerteList) {
			GestorLogs.registrarDebug(tarjetaSuerte.toString());
		}
	}

	private void mezclarTarjetasComunidad() {
		this.tarjetasComunidadList = ListUtils
				.randomizeList(this.tarjetasComunidadList);
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
	 * @throws SinDineroException
	 * 
	 */

	public AccionEnTarjeta jugarTarjetaComunidad(Jugador jugador,
			TarjetaComunidad tarjetaComunidad) {

		AccionEnTarjeta accion = null;

		switch (tarjetaComunidad.getIdTarjeta()) {

		case 1:
			accion = AccionEnTarjeta.PAGAR;
			accion.setMonto(50);
			accion.setMensaje(String.format("Paga %s por su póliza de seguro.",
					StringUtils.formatearAMoneda(50)));
			break;
		case 2:
			accion = AccionEnTarjeta.COBRAR_TODOS;
			accion.setMonto(10);
			accion.setMensaje(String.format(
					"Cobra %s de todos por su cumpleaños.",
					StringUtils.formatearAMoneda(10)));
			// banco.cobrarATodosPagarAUno(jugador, 10);
			break;
		case 3:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(1);
			accion.setCobraSalida(true);
			accion.setMensaje("Va al casillero SALIDA.");
			break;
		// return (tableroController.moverACasillero(jugador, 1, true) != null);
		case 4:
			accion = AccionEnTarjeta.PAGAR;
			accion.setMonto(50);
			accion.setMensaje(String.format(
					"Paga %s por la factura del médico.",
					StringUtils.formatearAMoneda(50)));
			break;
		// return banco.pagar(jugador, 50);
		case 5:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(10);
			accion.setMensaje(String.format(
					"Cobra %s por un concurso de belleza.",
					StringUtils.formatearAMoneda(10)));
			// banco.cobrar(jugador, 10);
			break;
		case 6:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(200);
			accion.setMensaje(String.format(
					"Cobra %s por un error en la banca.",
					StringUtils.formatearAMoneda(200)));
			// banco.cobrar(jugador, 200);
			break;
		case 7:
			accion = AccionEnTarjeta.IR_A_CARCEL;
			accion.setMensaje(String.format(
					"Va a la CARCEL sin pasar por la SALIDA y sin cobrar %s.",
					StringUtils.formatearAMoneda(200)));
			// return (tableroController.irACarcel(jugador) != null);
			break;
		case 8:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(20);
			accion.setMensaje(String.format("La hacienda le devolvió %s.",
					StringUtils.formatearAMoneda(20)));
			// return banco.pagar(jugador, 20);
			break;
		case 9:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(100);
			accion.setMensaje(String.format("Cobra %s por una herencia.",
					StringUtils.formatearAMoneda(100)));
			// return banco.pagar(jugador, 100);
			break;
		case 10:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(100);
			accion.setMensaje(String.format(
					"Cobra %s por los intereses de un plazo fijo.",
					StringUtils.formatearAMoneda(100)));
			// return banco.pagar(jugador, 100);
			break;
		case 11:
			accion = AccionEnTarjeta.PAGAR;
			accion.setMonto(100);
			accion.setMensaje(String.format("Paga %s al hospital.",
					StringUtils.formatearAMoneda(100)));
			// banco.cobrar(jugador, 100);
			break;
		case 12:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(2);
			accion.setCobraSalida(false);
			accion.setMensaje("Retrocede hasta RONDA DE VALENCIA");
			// return (tableroController.retrocederA(jugador, 2) != null);
			break;
		case 13:
			accion = AccionEnTarjeta.LIBRE_DE_CARCEL;
			accion.setTarjetaCarcel(tarjetaComunidad);
			accion.setMensaje("Queda libre de la carcel.");
			// jugador.getTarjetaCarcelList().add(tarjetaComunidad);
			break;
		case 14:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(50);
			accion.setMensaje(String.format(
					"Cobra %s por la venta de acciones.",
					StringUtils.formatearAMoneda(50)));
			// return banco.pagar(jugador, 50);
			break;
		default:
			break;
		}
		return accion;
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
	 * @throws SinDineroException
	 *             Si se debe cobrar al jugador y no tiene suficiente dinero
	 *             para pagar, se lanza una {@code SinDineroException}
	 */

	public AccionEnTarjeta jugarTarjetaSuerte(Jugador jugador,
			TarjetaSuerte tarjetaSuerte) {

		AccionEnTarjeta accion = null;

		switch (tarjetaSuerte.getIdTarjeta()) {

		case 1:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(40);
			accion.setCobraSalida(true);
			accion.setMensaje("Va al casillero PASEO DEL PRADO.");
			break;
		// return (tableroController.moverACasillero(jugador, 40, true) !=
		// null);
		case 2:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(12);
			accion.setCobraSalida(true);
			accion.setMensaje("Va al casillero GLORIETA DE BILBAO.");
			break;
		// return (tableroController.moverACasillero(jugcobraSalidaador, 12,
		// true) !=
		// null);
		case 3:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(50);
			accion.setMensaje(String.format(
					"Cobra %s por intereses del banco.",
					StringUtils.formatearAMoneda(50)));
			break;
		// return banco.pagar(jugador, 50);
		case 4:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(1);
			accion.setCobraSalida(true);
			accion.setMensaje("Va al casillero SALIDA.");
			break;

		// return (tableroController.moverACasillero(jugador, 1, true) !=
		// null);
		case 5:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(25);
			accion.setCobraSalida(true);
			accion.setMensaje("Va al casillero CEA BERMÚDEZ.");
			break;
		// return (tableroController.moverACasillero(jugador, 25, true) !=
		// null);
		case 6:
			accion = AccionEnTarjeta.COBRAR;
			accion.setMonto(150);
			accion.setMensaje(String.format(
					"Cobra %s por el seguro de los edificios.",
					StringUtils.formatearAMoneda(150)));
			break;
		// return banco.pagar(jugador, 150);
		case 7:
			accion = AccionEnTarjeta.IR_A_CARCEL;
			accion.setMensaje(String.format(
					"Va a la CARCEL sin pasar por la SALIDA y sin cobrar %s.",
					StringUtils.formatearAMoneda(200)));
			break; // return (tableroController.irACarcel(jugador) != null);
		case 8:
			accion = AccionEnTarjeta.PAGAR;
			accion.setMonto(20);
			accion.setMensaje(String.format(
					"Paga %s por una multa por embriaguez.",
					StringUtils.formatearAMoneda(20)));
			break;
		// banco.cobrar(jugador, 20);
		// return true;

		case 9:
			accion = AccionEnTarjeta.MOVER;
			accion.setNroCasilleros(-3);
			accion.setMensaje("Retrocede tres lugares.");
			break;
		// return (tableroController.moverAtras(jugador, 3) != null);
		case 10:
			accion = AccionEnTarjeta.PAGAR_POR_CASA_HOTEL;
			accion.setPrecioPorCasa(25);
			accion.setPrecioPorHotel(100);
			String.format(
					"Paga %s por cada casa y %s por cada hotel a causa de reparaciones.",
					StringUtils.formatearAMoneda(25),
					StringUtils.formatearAMoneda(100));
			break;
		// banco.cobrarPorCasaYHotel(jugador, 25, 100);
		// return true;
		case 11:
			accion = AccionEnTarjeta.PAGAR_POR_CASA_HOTEL;
			accion.setPrecioPorCasa(40);
			accion.setPrecioPorHotel(115);
			String.format(
					"Paga %s por cada casa y %s por cada hotel a causa de reparaciones.",
					StringUtils.formatearAMoneda(40),
					StringUtils.formatearAMoneda(115));
			break;
		// banco.cobrarPorCasaYHotel(jugador, 40, 115);
		// return true;
		case 12:
			accion = AccionEnTarjeta.LIBRE_DE_CARCEL;
			accion.setTarjetaCarcel(tarjetaSuerte);
			accion.setMensaje("Queda libre de la carcel.");
			break;
		// jugador.getTarjetaCarcelList().add(tarjetaSuerte);
		// return true;
		case 13:
			accion = AccionEnTarjeta.PAGAR;
			accion.setMonto(150);
			accion.setMensaje(String.format("Paga %s por gastos escolares.",
					StringUtils.formatearAMoneda(150)));
			break;
		// banco.cobrar(jugador, 150);
		// return true;
		case 14:
			accion = AccionEnTarjeta.MOVER_A;
			accion.setNroCasilleros(16);
			accion.setCobraSalida(true);
			accion.setMensaje("Va al casillero ESTACIÓN DE LAS DELICIAS.");
			break;
		// return (tableroController.moverACasillero(jugador, 16, true) !=
		// null);
		default:
			break;
		}
		return accion;
	}

	public TarjetaSuerte getNextTarjetaSuerte() {
		TarjetaSuerte tmpTarjetaSuerte = this.tarjetasSuerteList
				.get(this.proximaTarjetaSuerte);

		this.proximaTarjetaSuerte++;

		if (this.proximaTarjetaSuerte >= this.tarjetasSuerteList.size()) {
			this.tarjetasSuerteList = ListUtils
					.randomizeList(this.tarjetasSuerteList);
			this.proximaTarjetaSuerte = 0;
		}

		return tmpTarjetaSuerte;
	}

	public TarjetaComunidad getNextTarjetaComunidad() {
		TarjetaComunidad tmpTarjetaComunidad = this.tarjetasComunidadList
				.get(this.proximaTarjetaComunidad);

		this.proximaTarjetaComunidad++;

		if (this.proximaTarjetaComunidad >= this.tarjetasComunidadList.size()) {
			this.tarjetasComunidadList = ListUtils
					.randomizeList(this.tarjetasComunidadList);
			this.proximaTarjetaComunidad = 0;
		}

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
	 * Busca la tarjeta de la suerte correspondiente
	 * al id recibido por parámetro.
	 * @param idTarjeta id de Tarjeta suerte procedente
	 * de la base de datos. 
	 * @return objeto Tarjeta Suerte.
	 */
	public TarjetaSuerte getTarjetaSuerteById(int idTarjeta)
	{
		for (TarjetaSuerte tarjetaSuerte : tarjetasSuerteList) {
			if(tarjetaSuerte.getIdTarjeta() == idTarjeta)
				return tarjetaSuerte;
		}
		return null;
	}
	
	/**
	 * Busca la tarjeta de la comunidad correspondiente
	 * al id recibido por parámetro.
	 * @param idTarjeta id de tarjeta comunidad procedente
	 * de la base de datos.
	 * @return objecto Tarjeta Comunidad.
	 */
	public TarjetaComunidad getTarjetaComunidadById(int idTarjeta){
		for (TarjetaComunidad tarjetaComunidad : tarjetasComunidadList) {
			if(tarjetaComunidad.getIdTarjeta() == idTarjeta)
				return tarjetaComunidad;
		}
		return null;
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

	/**
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
