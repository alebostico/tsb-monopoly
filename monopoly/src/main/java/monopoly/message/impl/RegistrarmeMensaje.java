/**
 * 
 */
package monopoly.message.impl;

import monopoly.controller.UsuarioController;
import monopoly.model.Usuario;
import monopoly.util.constantes.ConstantesMensaje;
import monopoly.util.exception.EmailInvalidoException;
import monopoly.util.message.IMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class RegistrarmeMensaje implements IMensaje {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * monopoly.util.message.IMensaje#decodificarMensaje(java.lang.String[])
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		Usuario usuario = new Usuario(vCadena[1], vCadena[2]);
		usuario.setNombre(vCadena[3]);
		try {
			usuario.setEmail(vCadena[4]);
		} catch (EmailInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usuario = UsuarioController.saveUsuario(usuario);

		String[] vStr = new String[] { ConstantesMensaje.REGISTRARME_RESULTADO,
				usuario.getUserName(), usuario.getPassword(),
				usuario.getNombre(), usuario.getEmail(),
				Long.toString(usuario.getIdUsuario()) };

		return String.join(ConstantesMensaje.DELIMITADOR, vStr);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.message.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		String[] vStrSalida = new String[] { getTipoMensaje(), vCadena[0],
				vCadena[1], vCadena[2], vCadena[3] };

		return String.join(ConstantesMensaje.DELIMITADOR, vStrSalida);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see monopoly.util.message.IMensaje#getTipoMensaje()
	 */
	@Override
	public String getTipoMensaje() {
		// TODO Auto-generated method stub
		return ConstantesMensaje.REGISTRARME;
	}

}
