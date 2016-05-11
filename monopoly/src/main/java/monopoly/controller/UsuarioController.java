package monopoly.controller;

import monopoly.dao.IUsuarioDao;
import monopoly.model.Usuario;
import monopoly.util.exception.UsuarioExistenteException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 *
 */
public class UsuarioController {

	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"spring/config/BeanLocations.xml");

	
	public static Usuario saveUsuario(Usuario usuario) throws UsuarioExistenteException 
	{
		IUsuarioDao usuarioDao = (IUsuarioDao) appContext.getBean("usuarioDao");
		if(usuarioDao.validarUsuario(usuario.getUserName()) == null)
			usuarioDao.save(usuario);
		else
			throw new UsuarioExistenteException(String.format("Ya existe el usuario %s. Ingrese otro por favor.", usuario.getUserName()));
		return usuario;
	}
	
	public static Usuario validarUsuario(String userName, String password)
	{
		IUsuarioDao usuarioDao = (IUsuarioDao) appContext.getBean("usuarioDao");
		Usuario usuario = usuarioDao.validarUsuario(userName, password);
				
		return usuario;
	}

}
