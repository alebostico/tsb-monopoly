/**
 * 
 */
package monopoly.controller;

import java.util.ArrayList;

import monopoly.util.ConstantesMensaje;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class MensajesController {
	
	/*
	 * Atributos para la lógica del juego.
	 */
	//private GestorJuego gestorJuegos = GestorJuego.getInstance();
	public static String DELIMITADOR = "&-&-&";
	
	public static String codificarMensaje(ArrayList<String> cadenaSalida)
	{
		return String.join(DELIMITADOR, cadenaSalida);
	}
	
	public static String codificarMensaje(String[] cadenaSalida)
	{
		return String.join(DELIMITADOR, cadenaSalida);
	}
	
	public static String decodificarMensaje(ArrayList<String> cadenaEntrada)
	{
		String cadenaSalida = "";
		
		switch (cadenaEntrada.get(0)) {
		
		case ConstantesMensaje.LOGIN:
			System.out
					.println("Se recibió un mensaje para loguearse en el juego");
			
			cadenaSalida= LoginController
					.validarUsuario(cadenaEntrada.get(1), cadenaEntrada.get(2));
			break;
		case ConstantesMensaje.INICIAR_PARTIDA:
			System.out
					.println("Se recibió un mensaje para iniciar partida en el juego");
			// numUsers = clientesActivos.size();
			// salida.writeInt(numUsers);
			// for (int i = 0; i < numUsers; i++)
			// salida.writeUTF(clientesActivos.get(i).nameUser);
			break;
		default:
			System.out.println("valor por defecto");
			// amigo = entrada.readUTF();// captura nombre de amigo
			// mencli = entrada.readUTF();// mensage enviado
			// enviaMsg(amigo, mencli);
			break;
		}
		return cadenaSalida;
	}

}
