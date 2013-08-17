/**
 * 
 */
package monopoly.model.tablero;

import java.util.ArrayList;
import java.util.List;

import monopoly.model.Jugador;
import monopoly.model.tarjetas.TarjetaCalle;
import monopoly.model.tarjetas.TarjetaCompania;
import monopoly.model.tarjetas.TarjetaEstacion;

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

	//CONSTANTES
	private int cantCasilleros = 40;
	
	private Casillero[] casillerosList;

	/**
	 * Constructor por defecto.
	 */
	public Tablero() {
		// Cargar los casilleros.
		casillerosList = new Casillero[40];

		this.loadCasilleros();
	}

	/**
	 * Método que genera los casilleros del tablero. Probablemente al pedo
	 * porque lo más seguro es que los carguemos directamente desde la base de
	 * datos. Pero para pruebas sirve.
	 */
	private void loadCasilleros() {

		this.casillerosList[0] = new Casillero(1, Casillero.CASILLERO_SALIDA);
		this.casillerosList[1] = new CasilleroCalle(2, "Ronda de Valencia",
				new TarjetaCalle());
		this.casillerosList[2] = new Casillero(3, Casillero.CASILLERO_COMUNIDAD);
		this.casillerosList[3] = new CasilleroCalle(4, "Plaza Lavapiés",
				new TarjetaCalle());
		this.casillerosList[4] = new Casillero(5, Casillero.CASILLERO_IMPUESTO);
		this.casillerosList[5] = new CasilleroEstacion(6, "Estación de Goya",
				new TarjetaEstacion());
		this.casillerosList[6] = new CasilleroCalle(7,
				"Glorieta Cuatro Caminos", new TarjetaCalle());
		this.casillerosList[7] = new Casillero(8, Casillero.CASILLERO_SUERTE);
		this.casillerosList[8] = new CasilleroCalle(9,
				"Avenida Reina Victoria", new TarjetaCalle());
		this.casillerosList[9] = new CasilleroCalle(10, "Calle Bravo Murillo",
				new TarjetaCalle());

		this.casillerosList[10] = new Casillero(11, Casillero.CASILLERO_CARCEL);
		this.casillerosList[11] = new CasilleroCalle(12, "Glorieta de Biblbao",
				new TarjetaCalle());
		this.casillerosList[12] = new CasilleroCompania(13,
				"Compañía de Electricidad", new TarjetaCompania());
		this.casillerosList[13] = new CasilleroCalle(14,
				"Calle Alberto Aguilera", new TarjetaCalle());
		this.casillerosList[14] = new CasilleroCalle(15, "Calle Fuencarral",
				new TarjetaCalle());
		this.casillerosList[15] = new CasilleroEstacion(16,
				"Estación de las Delicias", new TarjetaEstacion());
		this.casillerosList[16] = new CasilleroCalle(17, "Avenida Felipe II",
				new TarjetaCalle());
		this.casillerosList[17] = new Casillero(18,
				Casillero.CASILLERO_COMUNIDAD);
		this.casillerosList[18] = new CasilleroCalle(19, "Calle Velázquez",
				new TarjetaCalle());
		this.casillerosList[19] = new CasilleroCalle(20, "Calle Serrano",
				new TarjetaCalle());

		this.casillerosList[20] = new Casillero(21,
				Casillero.CASILLERO_DESCANSO);
		this.casillerosList[21] = new CasilleroCalle(22, "Avenida de América",
				new TarjetaCalle());
		this.casillerosList[22] = new Casillero(23, Casillero.CASILLERO_SUERTE);
		this.casillerosList[23] = new CasilleroCalle(24,
				"Calle María de Molina", new TarjetaCalle());
		this.casillerosList[24] = new CasilleroCalle(25, "Calle Cea Bermúdez",
				new TarjetaCalle());
		this.casillerosList[25] = new CasilleroEstacion(26,
				"Estación del Mediodía", new TarjetaEstacion());
		this.casillerosList[26] = new CasilleroCalle(27,
				"Avenida de los Reyes Católicos", new TarjetaCalle());
		this.casillerosList[27] = new CasilleroCalle(28, "Calle Bailén",
				new TarjetaCalle());
		this.casillerosList[28] = new CasilleroCompania(29,
				"Compañía de Aguas", new TarjetaCompania());
		this.casillerosList[29] = new CasilleroCalle(30, "Plaza de España",
				new TarjetaCalle());

		this.casillerosList[30] = new Casillero(31,
				Casillero.CASILLERO_IRACARCEL);
		this.casillerosList[31] = new CasilleroCalle(32, "Puerta del Sol",
				new TarjetaCalle());
		this.casillerosList[32] = new CasilleroCalle(33, "Calle Alcalá",
				new TarjetaCalle());
		this.casillerosList[33] = new Casillero(34,
				Casillero.CASILLERO_COMUNIDAD);
		this.casillerosList[34] = new CasilleroCalle(35, "Gran Vía",
				new TarjetaCalle());
		this.casillerosList[35] = new CasilleroEstacion(36,
				"Estación del Norte", new TarjetaEstacion());
		this.casillerosList[36] = new Casillero(37, Casillero.CASILLERO_SUERTE);
		this.casillerosList[37] = new CasilleroCalle(38,
				"Paseo de la Castellana", new TarjetaCalle());
		this.casillerosList[38] = new Casillero(39,
				Casillero.CASILLERO_IMPUESTO);
		this.casillerosList[39] = new CasilleroCalle(40, "Paseo del Prado",
				new TarjetaCalle());
		
	}

	/**
	 * Devuelve el casillero pasado por parámetro. El parámetro es el Número de
	 * casillero [1-40], NO el indice del vector.
	 * 
	 * @param nroCasillero
	 *            El número del casillero a devolver.
	 */
	public Casillero getCasillero(int nroCasillero) {
		return this.casillerosList[nroCasillero + 1];
	}

	
	public Casillero moverAdelante(Jugador jugador, int nroCasillero) {
		
		
		return this.casillerosList[0];
	}
	
	//TODO: int numero de casillas que se mueve atras?
	public Casillero moverAtras(Jugador jugador, int nroCasillero) {
		
		
		return this.casillerosList[0];
	}
	
	public Casillero moverACasillero(Jugador jugador, int nroCasillero){
		
		return this.casillerosList[0];
	}
	
	public Casillero moverACasillero(Jugador jugador, String nombreCalle){
		
		return this.casillerosList[0];
	}
	
	public Casillero irACarcel(Jugador jugador){
		return this.moverACasillero(jugador, 11);
	}
	
	//TODO: retrocede a... supongo que no cobra los 200
	public Casillero retrocederA(Jugador jugador, int nroCasillero){
		return this.casillerosList[0];
	}
}
