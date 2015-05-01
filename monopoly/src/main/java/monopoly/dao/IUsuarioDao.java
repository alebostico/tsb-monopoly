/**
 * 
 */
package monopoly.dao;

import java.util.List;

import monopoly.model.Usuario;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 *
 */
public interface IUsuarioDao {

	void save(Usuario usuario);

	void update(Usuario usuario);

	void delete(Usuario usuario);

	List<Usuario> getAll();
	
	Usuario validarUsuario(String userName, String password);
	
}
