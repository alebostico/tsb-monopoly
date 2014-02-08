package monopoly.controller;

import monopoly.dao.IUsuarioDao;
import monopoly.model.Usuario;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class GestorUsuario {

	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"spring/config/BeanLocations.xml");

	
	public static Usuario saveUsuario(Usuario usuario)
	{
		IUsuarioDao usuarioDao = (IUsuarioDao) appContext.getBean("usuarioDao");
		usuarioDao.save(usuario);
		return usuario;
	}

}
