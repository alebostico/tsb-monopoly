/**
 * 
 */
package monopoly.controller;

import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import monopoly.model.Juego;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class PartidasController {

	private TreeMap<String, Juego> juegosList;

	private static PartidasController instance;

	private PartidasController() {
		super();
		juegosList = new TreeMap<String, Juego>();
		GestorLogs.registrarLog("Creando el gestor de juegos");
	}

	public static PartidasController getInstance() {
		if (instance == null)
			instance = new PartidasController();

		return instance;
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
			if (juegosList.put(juego.getUniqueID(), juego) == null)
				return true;
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
	// public TreeMap<String, Juego> getJuegosList() {
	// return juegosList;
	// }

	/**
	 * Busca un juego a partir del usuario que lo creo y el nombre del juego
	 * 
	 * @param nombreUsuarioCreador
	 * @param nombreJuego
	 * @return
	 */
	public Juego buscarJuego(String nombreUsuarioCreador, String nombreJuego) {
		// TODO: implementar metodo
		for (Juego juegoIterator : juegosList.values()) {
			if (juegoIterator.getNombreJuego().equals(nombreJuego)
					&& juegoIterator.getOwner().getUserName()
							.equals(nombreUsuarioCreador))
				return juegoIterator;
		}
		return null;
	}

	/**
	 * Retorna todos los juegos cuyos nombres coinciden con un criterio de
	 * búsqueda
	 * 
	 * @param likeNombre 
	 * @return
	 */
	public List<Juego> buscarJuegos(String likeNombre) {
		// TODO: Implementar método
		return null;
	}

	/**
	 * Devuelve un juego a partir del UniqueID del juego
	 * 
	 * @param juegoUniqueId
	 *            el UniqueID del juego
	 * @return El Juego
	 */
	public Juego buscarJuego(String juegoUniqueId) {
		return juegosList.get(juegoUniqueId);
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
		return buscarJuego(uniqueID);
	}

	/**
	 * Verifica si un juego existe en la lista de juegos
	 * 
	 * @param juego
	 *            El juego que cuya existencia se quiere verificar
	 * @return true si el juego existe. false si no existe.
	 */
	public boolean existeJuego(Juego juego) {
		for (Juego juegoIterator : this.juegosList.values()) {
			if (juegoIterator.compareTo(juego) == 0)
				return true;
		}
		return false;
	}

}
