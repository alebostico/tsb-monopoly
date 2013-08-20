/**
 * 
 */
package monopoly.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import monopoly.util.LectorXML;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPServer {

	public static final int PORT = LectorXML.getPuertoDeConexion();
	static ServerSocket serverSocket;

	public static void main(String argv[]) throws Exception {
		runServer();
	}

	public static void runServer() {
		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.out
					.println("El Servidor no se pudo iniciar correctamente...");
		}
		System.out.println("El Servidor ha sido levantado correctamente...");
		Thread checkServerAlive = new Thread(new Runnable() {
			public void run() {
				try {
					while (serverSocket.isBound()) {
						Thread.sleep(3000);
						System.out
								.println("Servidor Conectado. Esperando mensajes de clientes...");
					}
				} catch (InterruptedException e) {
					System.out
							.println("No se puede comprobar el estado del Servidor...");
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
						connectionSocket);
				serverThread.start();
			} catch (IOException e) {
				System.out
						.println("El servidor no puede aceptar nuevas conexiones...");
			}
		}
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out
					.println("El Servidor no se pudo cerrar correctamente...");
		}
	}

	public static class TCPServerThread extends Thread {
		private static Socket socket;
		static String socketip;
		static int socketport;

		public TCPServerThread(Socket s) {
			// TODO Auto-generated constructor stub
			socket = s;
			socketip = s.getInetAddress().getHostAddress();
			socketport = s.getPort();
		}

		public void run() {
			String mensaje = null;
			try {

				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				while ((mensaje = bufferedReader.readLine()) != null) {
					System.out.println("recibiendo mensajes del cliente: "
							+ mensaje);
				}
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
