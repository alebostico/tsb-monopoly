/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;

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
public class GestorLogin {
	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"spring/config/BeanLocations.xml");

	
	public static Usuario validarUsuario(ArrayList<String> arrayContenidoMensaje)
	{
		IUsuarioDao usuarioDao = (IUsuarioDao) appContext.getBean("usuarioDao");
		Usuario usuario = usuarioDao.validarUsuario(arrayContenidoMensaje.get(0), arrayContenidoMensaje.get(1));
		return usuario;
	}

}
