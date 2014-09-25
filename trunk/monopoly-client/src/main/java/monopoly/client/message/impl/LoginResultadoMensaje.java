/**
 * 
 */
package monopoly.client.message.impl;

import monopoly.client.controller.LoginController;
import monopoly.model.Usuario;
import monopoly.util.exception.EmailInvalidoException;
import monopoly.util.message.ConstantesMensaje;
import monopoly.util.message.IMensaje;

/**
 * @author pablo
 *
 */
public class LoginResultadoMensaje implements IMensaje {

	/**
	 * 
	 */
	public LoginResultadoMensaje() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.util.mensajes.IMensaje#decodificarMensaje(java.lang.String[])
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		boolean existe = false;
		Usuario user = null;
		existe = Boolean.parseBoolean(vCadena[1]);
		if (existe) {
			user = new Usuario(vCadena[3], vCadena[4]);
			user.setIdUsuario(Integer.parseInt(vCadena[2]));
			user.setNombre(vCadena[5]);
			try {
				user.setEmail(vCadena[6]);
			} catch (EmailInvalidoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		LoginController.getInstance().evaluarResultadoLogueo(existe, user);

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.mensajes.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.mensajes.IMensaje#getTipoMensaje()
	 */
	@Override
	public String getTipoMensaje() {
		// TODO Auto-generated method stub
		return ConstantesMensaje.LOGIN_RESULTADO;
	}

}
