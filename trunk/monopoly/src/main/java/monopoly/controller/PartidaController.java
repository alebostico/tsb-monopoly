/**
 * 
 */
package monopoly.controller;

import java.util.Date;
import java.util.List;

import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class PartidaController {
	
	private List<Juego> juegosList;
	
	private static PartidaController INSTANCE;

	private PartidaController() {
		super();
		//juegosList = new ArrayList<Juego>();
		GestorLogs.registrarLog("Creado el gestor de juegos");
	}

	public static PartidaController getInstance() {
		if (INSTANCE == null)
			INSTANCE = new PartidaController();

		return INSTANCE;
	}

	/**
	 * Agrega un juego a la lista de juegos
	 * 
	 * @param juego
	 *            El juego a agregar
	 * @return true si se agrega el juego. fasle en caso contrario
	 */
	private boolean addJuego(Juego juego) {
		// TODO: verificar que el juego no exista en la bd
		if (!this.existeJuego(juego)) {
			GestorLogs.registrarLog("Agregado al GestorJuegos el juego "
					+ juego.getUniqueID());
			GestorLogs.registrarDebug(juego.toString());
			return this.juegosList.add(juego);
		}
		return false;
	}

	/**
	 * Crea un juego nuevo, lo agrega a la lista de juegos y lo retorna.
	 * 
	 * @param creador
	 *            El usuario creador (due&ntilde;o) del juego
	 * @param nombre
	 *            El nombre del juego
	 * 
	 * @return El juego creado
	 */
	public Juego crearJuego(Usuario creador, String nombre) {
		// TODO: implementar metodo
		// verificar que el nombre del juego no exista
		// tanto en las instancias actuales como en la BD.
		Juego juego = new Juego(creador, nombre);
		if (!this.addJuego(juego))
			return null;
		return juego;
	}

	/**
	 * @return the juegosList
	 */
	public List<Juego> getJuegosList() {
		return juegosList;
	}

	/**
	 * Busca un juego a partir del usuario que lo creo y el nombre del juego
	 * 
	 * @param nombreUsuarioCreador
	 * @param nombreJuego
	 * @return
	 */
	public Juego buscarJuego(String nombreUsuarioCreador, String nombreJuego) {
		// TODO: implementar metodo
		return null;
	}

	/**
	 * Busca todos los juegos de un usuario creados en el período de fechas
	 * pasados como parámetro.
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	public List<Juego> buscarJuegos(String nombreUsuarioCreador,
			Date fechaDesde, Date fechaHasta) {
		// TODO: implementar metodo
		return null;

	}

	/**
	 * Devuelve un juego a partir de su uniqueID
	 * 
	 * @param uniqueID
	 * @return
	 */
	public Juego getJuego(String uniqueID) {
		// TODO: implementar metodo
		return null;
	}

	/**
	 * Verifica si un juego existe en la lista de juegos
	 * 
	 * @param juego
	 *            El juego que cuya existencia se quiere verificar
	 * @return true si el juego existe. false si no existe.
	 */
	public boolean existeJuego(Juego juego) {
		for (Juego juegoIterator : this.juegosList) {
			if (juegoIterator.compareTo(juego) == 0)
				return true;
		}
		return false;
	}

}
