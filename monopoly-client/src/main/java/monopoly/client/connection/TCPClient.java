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
import java.util.ArrayList;

import monopoly.client.controller.LoginController;
import monopoly.client.controller.MensajesController;
import monopoly.util.GestorLogs;
import monopoly.util.LectorXML;
import monopoly.util.StringUtils;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPClient extends Thread {

	private static final String IPSERVIDOR = LectorXML.getIpServidor();
	private static final int PUERTO = LectorXML.getPuertoDeConexion();
	private static Socket clientSocket;

	private PrintWriter salida;
	private BufferedReader entrada;

	private LoginController loginController;

	private boolean listening = true;
	

	/**
	 * @param jugador
	 */
	public TCPClient(LoginController ventIniciarSesion) {
		super();
		loginController = ventIniciarSesion;
	}

	@Override
	public void run() {
		if (loginController != null) {

			try {
				clientSocket = new Socket(IPSERVIDOR, PUERTO);
				salida = new PrintWriter(clientSocket.getOutputStream(), true);
				entrada = new BufferedReader(new InputStreamReader(
						clientSocket.getInputStream()));
				GestorLogs.registrarLog("Cliente en linea...");

				String strEntrada = "";
				ArrayList<String> vCadena = null;

				while (listening) {
					strEntrada = entrada.readLine();
					if (strEntrada != null) {
						vCadena = StringUtils
								.analizarCadena(strEntrada);
						MensajesController.decodificarMensaje(vCadena);
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
			}
			catch(Exception ex)
			{
				// TODO Auto-generated catch block
				GestorLogs.registrarError(ex.getMessage());
				ex.printStackTrace();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void detenerHilo() {
		
		try {
			this.stop();
			
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
			e.printStackTrace();
		}
		GestorLogs.registrarLog("Saliendo del Juego...");
	}
	
	public void enviarMensaje(String cadena)
	{
		salida.println(cadena);
	}

}
