/**
 * 
 */
package monopoly.client.connection;



/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ConexionController {

	public static TCPClient THREAD_CLIENTE;

	public static void iniciarConexion() {
		THREAD_CLIENTE = new TCPClient();
		THREAD_CLIENTE.start();
	}

	public static void cerrarConexion() {
		THREAD_CLIENTE.detenerHilo();
	}
	
}
