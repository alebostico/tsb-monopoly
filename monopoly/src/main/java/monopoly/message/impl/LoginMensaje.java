/**
 * 
 */
package monopoly.message.impl;

import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.message.IMensaje;

/**
 * @author pablo
 *
 */
public class LoginMensaje implements IMensaje {

	public LoginMensaje() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.mensajes.IMensaje#decodificarMensaje(java.lang.String)
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		String[] vStrSalida = null;
		Usuario usuario = new Usuario(""); // LoginController.validarUsuario(vCadena[1],
											// vCadena[2]);
		// if (usuario == null) {
		// GestorLogs.registrarLog("Usuario: " + vCadena[1]+ " inválido..");
		// vStrSalida = new String[] { ConstantesMensaje.LOGIN_RESULTADO,
		// "false",
		// "", "", "", "", "" };
		// } else {
		// GestorLogs.registrarLog("Usuario " + vCadena[1] + " válido..");
		// vStrSalida = new String[] { ConstantesMensaje.LOGIN_RESULTADO,
		// "true",
		// Long.toString(usuario.getIdUsuario()),
		// usuario.getUserName(), usuario.getPassword(),
		// usuario.getNombre(), usuario.getEmail() };
		//
		// }
		return String.join(ConstantesMensaje.DELIMITADOR, vStrSalida);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.mensajes.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		String[] vStrSalida = new String[] { getTipoMensaje(), vCadena[0],
				vCadena[1] };

		return String.join(ConstantesMensaje.DELIMITADOR, vStrSalida);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.mensajes.IMensaje#getTipoMensaje()
	 */
	public String getTipoMensaje() {
		return ConstantesMensaje.LOGIN;
	}

}
