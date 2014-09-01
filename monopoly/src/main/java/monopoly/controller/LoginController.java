/**
 * 
 */
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
public class LoginController {
	
	static ApplicationContext appContext = new ClassPathXmlApplicationContext(
			"spring/config/BeanLocations.xml");
	
		
//	public static String validarUsuario(String userName, String password)
//	{
//		IUsuarioDao usuarioDao = (IUsuarioDao) appContext.getBean("usuarioDao");
//		Usuario usuario = usuarioDao.validarUsuario(userName, password);
//		
//		ArrayList<String> vCadena = new ArrayList<String>();
//		
//		if (usuario == null) {
//			vCadena.add(ConstantesMensaje.LOGIN);
//			vCadena.add("false");			
//		} else {
//			vCadena.add(ConstantesMensaje.LOGIN);
//			vCadena.add("true");
//			vCadena.add(Long.toString(usuario.getIdUsuario()));
//			vCadena.add(usuario.getUserName());
//			vCadena.add(usuario.getPassword());
//			vCadena.add(usuario.getNombre());
//			vCadena.add(usuario.getEmail());
//			 
//		}				
//		return MensajesController.codificarMensaje(vCadena);
//	}
	
	public static Usuario validarUsuario(String userName, String password)
	{
		IUsuarioDao usuarioDao = (IUsuarioDao) appContext.getBean("usuarioDao");
		Usuario usuario = usuarioDao.validarUsuario(userName, password);
				
		return usuario;
	}

}
