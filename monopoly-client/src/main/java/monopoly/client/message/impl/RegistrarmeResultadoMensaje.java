/**
 * 
 */
package monopoly.client.message.impl;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import monopoly.client.controller.MenuOpcionesController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Usuario;
import monopoly.util.ConstantesFXML;
import monopoly.util.GestorLogs;
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
		
		String fxml = ConstantesFXML.FXML_MENU_OPCIONES;
		FXMLLoader loader = null;
		Parent root = null;
		try {
			loader = ScreensFramework.getLoader(fxml);
			root = (Parent) loader.load();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		}
		
		MenuOpcionesController.getInstance().showOptionMenu(root,usuario);
		
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
