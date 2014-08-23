/**
 * 
 */
package monopoly.client.controller;

import java.util.ArrayList;

import monopoly.client.controller.LoginController;
import monopoly.util.ConstantesMensaje;

/**
 * @author pablo
 *
 */
public class MensajesController {
	
	public static String DELIMITADOR = "&-&-&";
	
	public static String codificarMensaje(ArrayList<String> cadenaSalida)
	{
		return String.join(DELIMITADOR, cadenaSalida);
	}
	
	public static String codificarMensaje(String[] cadenaSalida)
	{
		return String.join(DELIMITADOR, cadenaSalida);
	}
	
	public static void decodificarMensaje(ArrayList<String> cadenaEntrada)
	{		
		switch (cadenaEntrada.get(0)) {

		case ConstantesMensaje.LOGIN:
			LoginController.validarUsuario(cadenaEntrada);
			break;

		case ConstantesMensaje.INICIAR_PARTIDA:
			break;
		default:
			break;
		}
	}

}
