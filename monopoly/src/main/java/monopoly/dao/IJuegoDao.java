package monopoly.dao;

import java.util.List;

import monopoly.model.Juego;
import monopoly.model.Usuario;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 *
 */
public interface IJuegoDao {
	
	void save(Juego juego);

	void update(Juego juego);

	void delete(Juego juego);

	List<Juego> getAll();
	
	List<Juego> getJuegoGuardados(Usuario usuario);
	
	Juego findJuegoByName(String nombre);

	Juego findJuegoByUniqueId(String uniqueID); 
}
