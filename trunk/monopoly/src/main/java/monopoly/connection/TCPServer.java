/**
 * 
 */
package monopoly.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.LectorXML;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPServer extends Thread {

	public static final int PORT = LectorXML.getPuertoDeConexion();
	public List<TCPServerThread> listaServidores = new ArrayList<TCPServerThread>();
	private static ServerSocket serverSocket;
	
	private String delimitador = "&-&-&";

	public TCPServer(){};

	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			GestorLogs
					.registrarLog("El Servidor ha sido levantado en el puerto: "
							+ PORT);
		} catch (IOException e) {
			GestorLogs.registrarError("El Servidor no se pudo iniciar correctamente...");
		}

		Thread checkServerAlive = new Thread(new Runnable() {
			public void run() {
				try {
					while (serverSocket.isBound()) {
						Thread.sleep(3000);
						GestorLogs
						.registrarLog("Servidor Conectado. Esperando mensajes de clientes...");
					}
				} catch (InterruptedException e) {
					GestorLogs.registrarError("No se puede comprobar el estado del Servidor...");
				}
			}
		});
		checkServerAlive.start();

		// World world = new World();
		// world.start();
		while (serverSocket.isBound()) {
			try {
				Socket connectionSocket = serverSocket.accept();
				TCPServerThread serverThread = new TCPServerThread(
						connectionSocket, this, listaServidores.size());
				serverThread.start();
				GestorLogs
				.registrarLog("Se conectó un cliente..." + " id: " + listaServidores.size());
				listaServidores.add(serverThread);
			} catch (IOException e) {
				GestorLogs.registrarError("El servidor no puede aceptar nuevas conexiones...");
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			GestorLogs.registrarError("El Servidor no se pudo cerrar correctamente...");
		}
	}
	
	public void avisarResultadoLogueo(Usuario usuario, int idThreadServer)
	{
		String contenidoLineaEntrada = "";
		if(usuario == null){
			contenidoLineaEntrada = "false";
		}
		else
		{
			contenidoLineaEntrada = "false" + delimitador + usuario.getIdUsuario() + delimitador
					+ usuario.getUserName() + delimitador + usuario.getPassword() + delimitador
					+ usuario.getNombre() + delimitador + usuario.getEmail();
		}
		for (int i = 0; i < this.listaServidores.size(); i++) {
            if (i == idThreadServer) {
            	listaServidores.get(i).avisarResultadoLogueo(contenidoLineaEntrada);
            }
        }
	}
}
