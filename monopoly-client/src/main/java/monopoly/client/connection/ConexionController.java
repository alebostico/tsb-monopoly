/**
 * 
 */
package monopoly.client.connection;

import monopoly.client.gui.IniciarAplicacion;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ConexionController {

	public static TCPClient THREAD_CLIENTE;
	public static IniciarAplicacion APPLICATION; 

	public static void iniciarConexion(IniciarAplicacion app) {
		THREAD_CLIENTE = new TCPClient();
		THREAD_CLIENTE.start();
		APPLICATION = app;
	}

	public static void cerrarConexion() {
		THREAD_CLIENTE.detenerHilo();
	}
	
}
