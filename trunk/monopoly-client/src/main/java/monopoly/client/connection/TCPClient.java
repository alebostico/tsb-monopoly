/**
 * 
 */
package monopoly.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import monopoly.client.controller.LoginController;
import monopoly.util.GestorLogs;
import monopoly.util.LectorXML;
import monopoly.util.message.ConstantesMensaje;
import monopoly.util.message.IMensaje;
import monopoly.message.impl.*;
import monopoly.client.message.impl.*;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
@SuppressWarnings("unused")
public class TCPClient extends Thread {

	private static final String IPSERVIDOR = LectorXML.getIpServidor();
	private static final int PUERTO = LectorXML.getPuertoDeConexion();
	private static Socket clientSocket;

	private PrintWriter salida;
	private BufferedReader entrada;
	private boolean continuar = true;

	/**
	 * @param jugador
	 */
	public TCPClient() {
		super();
	}

	@Override
	public void run() {

		String readLine;
		String writeLine;
		String[] vStrEntrada;

		try {
			clientSocket = new Socket(IPSERVIDOR, PUERTO);
			salida = new PrintWriter(clientSocket.getOutputStream(), true);
			entrada = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
			GestorLogs.registrarLog("Cliente en linea...");

			while (continuar) {
				if ((readLine = entrada.readLine()) != null) {
					vStrEntrada = readLine.split(ConstantesMensaje.DELIMITADOR);
					Class<?> claseMsj = Class.forName(vStrEntrada[0]);
					IMensaje mensaje = (IMensaje) claseMsj.newInstance();
					writeLine = mensaje.decodificarMensaje(vStrEntrada);
					if (writeLine != null)
						enviarMensaje(writeLine);
				}
			}

		} catch (UnknownHostException ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
			ex.printStackTrace();
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
			ex.printStackTrace();
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void detenerHilo() {
		continuar = false;
		try {
			clientSocket.shutdownInput();
			clientSocket.shutdownOutput();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
			e.printStackTrace();
		}
		GestorLogs.registrarLog("Saliendo del Juego...");
	}

	public void enviarMensaje(String cadena) {
		salida.println(cadena);
	}

}
