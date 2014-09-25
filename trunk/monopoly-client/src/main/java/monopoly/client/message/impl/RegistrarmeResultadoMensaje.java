/**
 * 
 */
package monopoly.client.message.impl;

import monopoly.client.controller.MenuOpcionesController;
import monopoly.model.Usuario;
import monopoly.util.exception.EmailInvalidoException;
import monopoly.util.message.ConstantesMensaje;
import monopoly.util.message.IMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class RegistrarmeResultadoMensaje implements IMensaje {

	
	/* (non-Javadoc)
	 * @see monopoly.util.message.IMensaje#decodificarMensaje(java.lang.String[])
	 */
	@Override
	public String decodificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		Usuario usuario= new Usuario(vCadena[1], vCadena[2]);
		usuario.setNombre(vCadena[3]);
		try {
			usuario.setEmail(vCadena[4]);
		} catch (EmailInvalidoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		usuario.setIdUsuario(Long.parseLong(vCadena[5]));
		
		MenuOpcionesController.getInstance().showOptionMenu(usuario);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see monopoly.util.message.IMensaje#codificarMensaje(java.lang.String[])
	 */
	@Override
	public String codificarMensaje(String[] vCadena) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see monopoly.util.message.IMensaje#getTipoMensaje()
	 */
	@Override
	public String getTipoMensaje() {
		// TODO Auto-generated method stub
		return ConstantesMensaje.REGISTRARME_RESULTADO;
	}

}
