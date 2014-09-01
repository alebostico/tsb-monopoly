/**
 * 
 */
package monopoly.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import monopoly.util.GestorLogs;
import monopoly.util.message.ConstantesMensaje;
import monopoly.util.message.IMensaje;
import monopoly.message.impl.*;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@SuppressWarnings("unused")
public class TCPServerThread extends Thread {

	/*
	 * Datos del socket cliente.
	 */
	private Socket socketCliente;
	static String SOCKET_IP;
	static int SOCKET_PORT;

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
		String readLine;
		String writeLine;
		String[] vStrEntrada;

		try {
			salida = new PrintWriter(socketCliente.getOutputStream(), true);
			entrada = new BufferedReader(new InputStreamReader(
					socketCliente.getInputStream()));

			while ((readLine = entrada.readLine()) != null) {
				vStrEntrada = readLine.split(ConstantesMensaje.DELIMITADOR);
				IMensaje mensaje = (IMensaje) Class.forName(vStrEntrada[0]).newInstance();
				writeLine = mensaje.decodificarMensaje(vStrEntrada);
				if(writeLine != null)
					servidorPadre.enviarMensaje(writeLine, idThreadServer);
				else
					break;
			}
			//socketCliente.close();
		} catch (IOException e) {
			GestorLogs.registrarError("El cliente termino la conexión..\n"
					+ e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
		} catch (Exception et) {
			GestorLogs.registrarError("El cliente termino la conexión..\n"
					+ et.getMessage());
		}
	}

	void enviarMensaje(String cadena) {
		salida.println(cadena);
	}
}
