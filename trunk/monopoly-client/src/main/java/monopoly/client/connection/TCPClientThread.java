/**
 * 
 */
package monopoly.client.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import monopoly.client.controller.LoginController;
import monopoly.model.Usuario;

/**
 * @author pablo
 * 
 */
public class TCPClientThread extends Thread {

	private PrintWriter salida;
	private BufferedReader entrada;
	private TCPClient cliente;
	private LoginController loginController;

	

	public TCPClientThread(TCPClient cliente,
			LoginController ventIniciarSesion, BufferedReader entrada,
			PrintWriter salida) throws IOException {
		this.cliente = cliente;
		this.loginController = ventIniciarSesion;
		this.entrada = entrada;
		this.salida = salida;
	}

	public void run() {

//		try {
//
//			
//
//		} catch (UnknownHostException ex) {
//			GestorLogs.registrarError(ex.getMessage());
//			ex.printStackTrace();
//
//		} catch (IOException ex) {
//			GestorLogs.registrarError(ex.getMessage());
//			ex.printStackTrace();
//		}
	}

	public void iniciarSesion(Usuario usuario) {
		
	}

}
