/**
 * 
 */
package monopoly.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import monopoly.controller.GestorJuego;
import monopoly.dao.ITarjetaCalleDao;
import monopoly.dao.ITarjetaCompaniaDao;
import monopoly.dao.ITarjetaComunidadDao;
import monopoly.dao.ITarjetaEstacionDao;
import monopoly.dao.ITarjetaSuerteDao;
import monopoly.model.Banco;
import monopoly.model.Ficha;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.Usuario;
import monopoly.model.tablero.CasilleroCalle;
import monopoly.model.tablero.Tablero;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaComunidad;
import monopoly.model.tarjetas.TarjetaEstacion;
import monopoly.model.tarjetas.TarjetaSuerte;
import monopoly.util.GestorLogs;
import monopoly.util.ListUtils;
import monopoly.util.StringUtils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class Main {

	public static void main(String[] args) {

		GestorLogs.registrarLog("Realizando algunas pruebas...");
		testLoadGame();
		// testRandomizeList();
		// testShuffleList();
		// Main.testStringUtils();
		GestorLogs.registrarLog("END!!!");
	}

	private static void testLoadGame() {
		GestorLogs.registrarLog("Cargando el juego completo...");
		/*
		 * Hacemos algunas preubas. Creamos un juego nuevo, cargamos algunos
		 * jugadores y los movemos para probar lo que está hecho hasta ahora.
		 */
		GestorLogs.setLoggingDetailLevel(GestorLogs.MSG_DEBUG);

		GestorJuego gj = GestorJuego.getInstance();

		Usuario usuario1 = new Usuario("testuser1");
		Juego juego = gj.crearJuego(usuario1, "Test Juego");

		// Ficha ficha1 = new Ficha();
		// ficha1.setNombre("carretilla");
		// Jugador jugador1 = new Jugador(ficha1, usuario1, juego);
		// ficha1.setIdFicha(jugador1.getIdJugador());
		//
		// Usuario usuario2 = new Usuario("testuser2");
		// Ficha ficha2 = new Ficha();
		// ficha2.setNombre("buque");
		// Jugador jugador2 = new Jugador(ficha2, usuario2, juego);
		// ficha2.setIdFicha(jugador2.getIdJugador());

		// gj.addJuego(juego);

		// movemos al jugador1 5 casillero hacia adelante
		// juego.getTablero().moverAdelante(jugador1, 5, true);
		// System.out.println("Jugador1 5 lugares hacia adelante: "
		// + jugador1.getCasilleroActual());
		// juego.getTablero().moverAtras(jugador2, 10);
		// System.out.println("Jugador2 7 lugares hacia atras: "
		// + jugador2.getCasilleroActual());
		// juego.getTablero().moverACasillero(jugador1, 35, false);
		// System.out.println("Jugador1 al casillero 35: "
		// + jugador1.getCasilleroActual());
		// juego.getTablero().moverAdelante(jugador1, 10, true);
		// System.out.println("Jugador1 10 lugares hacia adelante y cobra: "
		// + jugador1.getCasilleroActual());
		// juego.getTablero().moverACasillero(jugador2, 6, false);
		// System.out.println("Jugador2 10 lugares hacia adelante y NO cobra: "
		// + jugador2.getCasilleroActual());

		System.out.println(juego.toString());
		System.out.println(juego.toStringAll());

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

	@SuppressWarnings("unchecked")
	private static void testShuffleList() {

		int cantElementos = 100000;

		long time_start, time_end;

		// create an ArrayList object
		ArrayList<String> arrayList = new ArrayList<String>();
		ArrayList<String> arrayList2 = new ArrayList<String>();

		// Add elements to Arraylist
		arrayList.add("A");
		arrayList.add("B");
		arrayList.add("C");
		arrayList.add("D");
		arrayList.add("E");

		arrayList2 = ((ArrayList<String>) arrayList.clone());

		System.out.println("Before shuffling, ArrayList contains : "
				+ arrayList);
		System.out.println("Before shuffling, ArrayList2 contains : "
				+ arrayList2);

		Collections.shuffle(arrayList);
		arrayList2 = (ArrayList<String>) ListUtils.randomizeList(arrayList2);

		System.out
				.println("After shuffling, ArrayList contains : " + arrayList);
		System.out.println("After shuffling, ArrayList2 contains : "
				+ arrayList2);

		System.out.println("Probando los tiempos");
		arrayList = new ArrayList<String>();
		arrayList2 = new ArrayList<String>();

		for (int i = 0; i < cantElementos; i++) {
			arrayList.add("A");
			arrayList2.add("A");
		}

		// //////////////////////////////////////////////////////////////////////////
		time_start = System.currentTimeMillis();

		Collections.shuffle(arrayList);

		time_end = System.currentTimeMillis();

		System.out.println("Tiempo para ordenar " + cantElementos
				+ " elementos con Collections.shuffle(arrayList) :"
				+ (time_end - time_start) + " milliseconds");

		// //////////////////////////////////////////////////////////////////////////
		time_start = System.currentTimeMillis();

		arrayList2 = (ArrayList<String>) ListUtils.randomizeList(arrayList2);

		time_end = System.currentTimeMillis();

		System.out.println("Tiempo para ordenar " + cantElementos
				+ " elementos con ListUtils.randomizeList(arrayList2) :"
				+ (time_end - time_start) + " milliseconds");

		// //////////////////////////////////////////////////////////////////////////

		/*
		 * Tiempo para ordenar 1.000.000 elementos con
		 * Collections.shuffle(arrayList) :73 milliseconds. <br /> Tiempo para
		 * ordenar 1.000.000 elementos con ListUtils.randomizeList(arrayList2)
		 * :82917 milliseconds
		 */
	}

	private static void testRandomizeList() {

		List<String> testListString = new ArrayList<String>();
		List<String> testListStringUnsorted = new ArrayList<String>();

		List<Integer> testListInt = new ArrayList<Integer>();
		List<Integer> testListIntUnsorted = new ArrayList<Integer>();

		for (int i = 0; i < 100; i++) {
			testListString.add("String de prueba " + i);
			testListInt.add(new Integer(i));
		}

		testListStringUnsorted = ListUtils.randomizeList(new ArrayList<String>(
				testListString));
		testListIntUnsorted = ListUtils.randomizeList(new ArrayList<Integer>(
				testListInt));

		System.out.println("Lista de Strings: ");

		for (int i = 0; i < testListStringUnsorted.size(); i++) {
			System.out.println(" #" + i + " "
					+ testListStringUnsorted.get(i).getClass().getName()
					+ "\tAntes: " + testListString.get(i).toString()
					+ "\tAhora: " + testListStringUnsorted.get(i).toString());
		}

		for (int i = 0; i < testListIntUnsorted.size(); i++) {
			System.out.println(" #" + i + " "
					+ testListIntUnsorted.get(i).getClass().getName()
					+ "\tAntes: " + testListInt.get(i).intValue() + "\tAhora: "
					+ testListIntUnsorted.get(i).intValue());
		}

	}

	private static void testStringUtils() {
		System.out
				.println("Salida esperada para 'hola': 4d186321c1a7f0f354b297e8914ab240");
		System.out.println("  Encriptar password 'hola': "
				+ StringUtils.encPass("hola"));
		System.out.println(((StringUtils.encPass("hola").compareTo(
				"4d186321c1a7f0f354b297e8914ab240") == 0) ? "OK" : "ERROR"));

		System.out
				.println("Salida esperada para '123': 202cb962ac59075b964b07152d234b70");
		System.out.println("  Encriptar password '123': "
				+ StringUtils.encPass("123"));
		System.out.println(((StringUtils.encPass("123").compareTo(
				"202cb962ac59075b964b07152d234b70") == 0) ? "OK" : "ERROR"));

		System.out.println("\nVerificando si algunos mails son validos:");
		System.out.println("correo@electronico.com \t\t- "
				+ StringUtils.validateEmail("correo@electronico.com"));
		System.out.println("correo-100@electronico.com \t- "
				+ StringUtils.validateEmail("correo-100@electronico.com"));
		System.out.println("correo \t\t\t\t- "
				+ StringUtils.validateEmail("correo"));
		System.out.println("correo..100@electronico.com \t- "
				+ StringUtils.validateEmail("correo..100@electronico.com"));

		System.out.println("\nVerificando si algunos username son validos:");
		System.out.println("username34 - "
				+ StringUtils.validateUsername("username34"));
		System.out.println("user_2013 - "
				+ StringUtils.validateUsername("user_2013"));
		System.out.println("user-2013 - "
				+ StringUtils.validateUsername("user-2013"));
		System.out.println("un3-4_2013-01_ - "
				+ StringUtils.validateUsername("un3-4_2013-01_"));
		System.out.println("un - " + StringUtils.validateUsername("un"));
		System.out.println("user@name - "
				+ StringUtils.validateUsername("user@name"));
		System.out.println("username123456789_- - "
				+ StringUtils.validateUsername("username123456789_-"));

	}

}
