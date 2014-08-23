/**
 * 
 */
package monopoly.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import monopoly.controller.MensajesController;
import monopoly.util.GestorLogs;
import monopoly.util.StringUtils;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPServerThread extends Thread {

	/*
	 * Datos del socket cliente.
	 */
	private Socket socketCliente;
	static String SOCKET_IP;
	static int SOCKET_PORT;
	private boolean corre = true;

	/*
	 * Datos del servidor padre.
	 */
	private TCPServer servidorPadre;
	private int idThreadServer = 0;

	/*
	 * Stream para enviar y recibir datos.
	 */
	private PrintWriter salida;
	private BufferedReader entrada;

	
	public TCPServerThread(Socket socketClient, TCPServer server,
			int idThreadServidor) {
		// TODO Auto-generated constructor stub
		socketCliente = socketClient;
		SOCKET_IP = socketClient.getInetAddress().getHostAddress();
		SOCKET_PORT = socketClient.getPort();
		this.servidorPadre = server;
		this.idThreadServer = idThreadServidor;
	}

	public void run() {
		try {
			salida = new PrintWriter(socketCliente.getOutputStream(), true);
			entrada = new BufferedReader(new InputStreamReader(
					socketCliente.getInputStream()));
		} catch (IOException e) {
			GestorLogs.registrarError(e.getMessage());
			e.printStackTrace();
		}
		String lineaEntrada;
		String lineaSalida;
		ArrayList<String> cadenaLineaEntrada = null;

		while (corre) {
			try {
				lineaEntrada = entrada.readLine();
				if (lineaEntrada != null) {
					cadenaLineaEntrada = StringUtils
							.analizarCadena(lineaEntrada);
					lineaSalida = MensajesController.decodificarMensaje(cadenaLineaEntrada);
					servidorPadre.enviarMensaje(lineaSalida, idThreadServer);
				}
			} catch (IOException e) {
				System.out.println("El cliente termino la conexión..\n" + e.getMessage());
				break;
			}
		}
		try {
			socketCliente.close();
		} catch (Exception et) {
			System.out.println("El cliente termino la conexión..\n" + et.getMessage());
		}
	}

	void enviarMensaje(String cadena) {
		salida.println(cadena);
	}

	void recibirMensaje(String cadena) {
		ArrayList<String> cadenaLineaEntrada = StringUtils
				.analizarCadena(cadena);
		MensajesController.decodificarMensaje(cadenaLineaEntrada);
		
	}
}
