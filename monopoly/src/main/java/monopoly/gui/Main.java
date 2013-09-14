/**
 * 
 */
package monopoly.gui;

import java.util.ArrayList;
import java.util.List;

import monopoly.dao.ITarjetaCalleDao;
import monopoly.dao.ITarjetaCompaniaDao;
import monopoly.dao.ITarjetaComunidadDao;
import monopoly.dao.ITarjetaEstacionDao;
import monopoly.dao.ITarjetaSuerteDao;
import monopoly.model.Banco;
import monopoly.model.Ficha;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static ArrayList<Ficha> fichas;

	public static void main(String[] args) {

		GestorLogs.registrarLog("Realizando algunas pruebas...");
		testLoadGame();
	}
	
	private static void testLoadGame(){
		GestorLogs.registrarLog("Cargando el juego completo...");
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		
		// Cargar el Banco
		Banco banco = new Banco();		
		
		// Cargar el tablero
		Tablero tablero = new Tablero(banco);
		
		// Cargar las tarjetas de Comunidad
		ITarjetaComunidadDao tarjetaComunidadDao = (ITarjetaComunidadDao) appContext
				.getBean("tarjetaComunidadDao");
		List<TarjetaComunidad> tarjetasComunidadList = new ArrayList<TarjetaComunidad>();
		tarjetasComunidadList = tarjetaComunidadDao.getAll();

		// Cargar las tarjetas de Suerte
		ITarjetaSuerteDao tarjetaSuerteDao = (ITarjetaSuerteDao) appContext
				.getBean("tarjetaSuerteDao");
		List<TarjetaSuerte> tarjetasSuerteList = new ArrayList<TarjetaSuerte>();
		tarjetasSuerteList = tarjetaSuerteDao.getAll();
		
		// Imprimir info para ver si está todo bien
		GestorLogs.registrarLog("Mostrar tablero con todos los casilleros");
		System.out.println(tablero.toString());

		GestorLogs.registrarLog("Mostrar lista de tarjetas de Comunidad");
		for (TarjetaComunidad tarjetaComunidad : tarjetasComunidadList) {
			System.out.println(tarjetaComunidad.toString());
		}

		GestorLogs.registrarLog("Mostrar lista de tarjetas de Suerte");
		for (TarjetaSuerte tarjetaSuerte : tarjetasSuerteList) {
			System.out.println(tarjetaSuerte.toString());
		}
		
	}

	private static void testLoadOneCasillero() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");
		ITarjetaCalleDao tarjetaCalleDao = (ITarjetaCalleDao) appContext
				.getBean("tarjetaCalleDao");

		Banco banco = new Banco();
		Tablero tablero = new Tablero(banco);

		// Test link cassillero-tarjeta

		GestorLogs.registrarLog("Cargando tarjeta 'RONDA DE VALENCIA'");
		TarjetaCalle tarjetaCalleTemp = tarjetaCalleDao
				.findByNombre("RONDA DE VALENCIA");
		
		GestorLogs.registrarLog("Cargando casillero 'Ronda de Valencia'");
		CasilleroCalle casillerosTest = new CasilleroCalle(2,
				"Ronda de Valencia", tarjetaCalleTemp);
	}

	private static void testLoadTarjetas() {
		ApplicationContext appContext = new ClassPathXmlApplicationContext(
				"spring/config/BeanLocations.xml");

		// Cargar las tarjetas de las calles
		ITarjetaCalleDao tarjetaCalleDao = (ITarjetaCalleDao) appContext
				.getBean("tarjetaCalleDao");
		List<TarjetaCalle> tarjetasCallesList = new ArrayList<TarjetaCalle>();
		tarjetasCallesList = tarjetaCalleDao.getAll();

		// Cargar las tarjetas de las companias
		ITarjetaCompaniaDao tarjetaCompaniaDao = (ITarjetaCompaniaDao) appContext
				.getBean("tarjetaCompaniaDao");
		List<TarjetaCompania> tarjetasCompaniaList = new ArrayList<TarjetaCompania>();
		tarjetasCompaniaList = tarjetaCompaniaDao.getAll();

		// Cargar las tarjetas de las Estaciones
		ITarjetaEstacionDao tarjetaEstacionDao = (ITarjetaEstacionDao) appContext
				.getBean("tarjetaEstacionDao");
		List<TarjetaEstacion> tarjetasEstacionList = new ArrayList<TarjetaEstacion>();
		tarjetasEstacionList = tarjetaEstacionDao.getAll();

		// Cargar las tarjetas de Comunidad
		ITarjetaComunidadDao tarjetaComunidadDao = (ITarjetaComunidadDao) appContext
				.getBean("tarjetaComunidadDao");
		List<TarjetaComunidad> tarjetasComunidadList = new ArrayList<TarjetaComunidad>();
		tarjetasComunidadList = tarjetaComunidadDao.getAll();

		// Cargar las tarjetas de Suerte
		ITarjetaSuerteDao tarjetaSuerteDao = (ITarjetaSuerteDao) appContext
				.getBean("tarjetaSuerteDao");
		List<TarjetaSuerte> tarjetasSuerteList = new ArrayList<TarjetaSuerte>();
		tarjetasSuerteList = tarjetaSuerteDao.getAll();

		// //////////////////////////////////////////////////////
		// Imprimimos info de debug para ver si anda todo......
		for (TarjetaCalle tarjetaCalle : tarjetasCallesList) {
			System.out.println(tarjetaCalle.toString() + "\n");
		}
		GestorLogs.registrarLog("Mostrar lista de tarjetas de Calles");

		for (TarjetaCompania tarjetaCompania : tarjetasCompaniaList) {
			System.out.println(tarjetaCompania.toString() + "\n");
		}
		GestorLogs.registrarLog("Mostrar lista de tarjetas de Compania");

		for (TarjetaEstacion tarjetaEstacion : tarjetasEstacionList) {
			System.out.println(tarjetaEstacion.toString() + "\n");
		}
		GestorLogs.registrarLog("Mostrar lista de tarjetas de Estacion");

		for (TarjetaComunidad tarjetaComunidad : tarjetasComunidadList) {
			System.out.println(tarjetaComunidad.toString() + "\n");
		}
		GestorLogs.registrarLog("Mostrar lista de tarjetas de Comunidad");

		for (TarjetaSuerte tarjetaSuerte : tarjetasSuerteList) {
			System.out.println(tarjetaSuerte.toString() + "\n");
		}
		GestorLogs.registrarLog("Mostrar lista de tarjetas de Suerte");
		// //////////////////////////////////////////////////////
	}

	public static ArrayList<Ficha> crearFichas() {
		fichas = new ArrayList<Ficha>();
		Ficha carretilla = new Ficha();
		carretilla.setNombre("carretilla");
		fichas.add(carretilla);
		Ficha buque = new Ficha();
		buque.setNombre("buque");
		fichas.add(buque);
		Ficha sacoDinero = new Ficha();
		sacoDinero.setNombre("sacoDinero");
		fichas.add(sacoDinero);
		Ficha jineteCaballo = new Ficha();
		jineteCaballo.setNombre("jineteCaballo");
		fichas.add(jineteCaballo);
		Ficha vehiculo = new Ficha();
		vehiculo.setNombre("vehiculo");
		fichas.add(vehiculo);
		Ficha tren = new Ficha();
		tren.setNombre("tren");
		fichas.add(tren);
		Ficha dedal = new Ficha();
		dedal.setNombre("dedal");
		fichas.add(dedal);
		Ficha obus = new Ficha();
		obus.setNombre("obus");
		fichas.add(obus);
		Ficha zapato = new Ficha();
		zapato.setNombre("zapato");
		fichas.add(zapato);
		Ficha perro = new Ficha();
		perro.setNombre("perro");
		fichas.add(perro);
		Ficha plancha = new Ficha();
		plancha.setNombre("plancha");
		fichas.add(plancha);
		Ficha sombreroCopa = new Ficha();
		sombreroCopa.setNombre("sombreroCopa");
		fichas.add(sombreroCopa);

		return fichas;
	}

}
