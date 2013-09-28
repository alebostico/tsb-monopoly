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
import monopoly.util.ConstantesMensaje;
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

	public TCPServer() {
	};

	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			GestorLogs
					.registrarLog("El Servidor ha sido levantado en el puerto: "
							+ PORT);
		} catch (IOException e) {
			GestorLogs
					.registrarError("El Servidor no se pudo iniciar, el puerto "
							+ PORT + " se encuentra en uso.");
		}

		Thread checkServerAlive = new Thread(new Runnable() {
			public void run() {
				try {
					while (serverSocket.isBound()) {
						Thread.sleep(3000);
						System.out.println("Servidor Conectado. Esperando mensajes de clientes...");
					}
				} catch (InterruptedException e) {
					GestorLogs.registrarError("No se puede comprobar el estado del Servidor...");
				}
			}
		});
		
		checkServerAlive.start();
		while (serverSocket.isBound()) {
			Socket connectionSocket = null;
			try {
				connectionSocket = serverSocket.accept();
				TCPServerThread serverThread = new TCPServerThread(
						connectionSocket, this, listaServidores.size());
				serverThread.start();
				GestorLogs
				.registrarLog("Se conectó un cliente..." + " id: " + listaServidores.size());
				listaServidores.add(serverThread);
			} catch (IOException ex) {
				GestorLogs.registrarError("El servidor no puede aceptar nuevas conexiones...\n" + ex.getMessage());
			}
		}
		try {
			serverSocket.close();
		} catch (IOException ex) {
			GestorLogs.registrarError("No se pudo cerrar el servidor... \n"
					+ ex.getMessage());
		}

	}

	public void avisarResultadoLogueo(Usuario usuario, int idThreadServer) {
		String contenidoLineaSalida = "";
		if (usuario == null) {
			contenidoLineaSalida = ConstantesMensaje.LOGIN
					+ delimitador + "false";
		} else {
			contenidoLineaSalida = ConstantesMensaje.LOGIN
					+ delimitador + "true"
					+ delimitador + usuario.getIdUsuario()
					+ delimitador + usuario.getUserName()
					+ delimitador + usuario.getPassword()
					+ delimitador + usuario.getNombre()
					+ delimitador + usuario.getEmail();
		}
		for (int i = 0; i < this.listaServidores.size(); i++) {
			if (i == idThreadServer) {
				listaServidores.get(i).avisarResultadoLogueo(
						contenidoLineaSalida);
			}
		}
	}
}
