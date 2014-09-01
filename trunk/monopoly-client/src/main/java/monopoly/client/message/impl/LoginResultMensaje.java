/**
 * 
 */
package monopoly.client.message.impl;

import monopoly.client.controller.LoginController;
import monopoly.model.Usuario;
import monopoly.util.message.ConstantesMensaje;
import monopoly.util.message.IMensaje;

/**
 * @author pablo
 *
 */
public class LoginResultMensaje implements IMensaje {

	/**
	 * 
	 */
	public LoginResultMensaje() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see monopoly.util.mensajes.IMensaje#decodificarMensaje(java.lang.String[])
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		boolean existe = false;		
		existe = Boolean.parseBoolean(vCadena[1]);
		
		Usuario user = new Usuario();
		user.setIdUsuario(Integer.parseInt(vCadena[2]));
		user.setPassword(vCadena[4]);
		user.setNombre(vCadena[2]);
		user.setEmail(vCadena[5]);
		LoginController.APPLICATION.validarUsuario(existe, user);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see monopoly.util.mensajes.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub				
		return null;
	}
	

	/* (non-Javadoc)
	 * @see monopoly.util.mensajes.IMensaje#getTipoMensaje()
	 */
	@Override
	public String getTipoMensaje() {
		// TODO Auto-generated method stub
		return ConstantesMensaje.LOGIN_RESULTADO;
	}

}
