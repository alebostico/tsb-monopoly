/**
 * 
 */
package monopoly.client.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import monopoly.client.gui.FXMLIniciarSesion;
import monopoly.client.gui.FXMLVentanaJuego;
import monopoly.model.Usuario;
import monopoly.util.EnumMensaje;
import monopoly.util.GestorLogs;
import monopoly.util.LectorXML;

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
	private DataInputStream entrada;
	private DataOutputStream salida;
	
	private Usuario usuarioLogueado;
	private FXMLIniciarSesion ventanaIniciarSesion;
	
	/*
	 * Atributos para la codificación de mensajes.
	 */
	private String delimitador = "&-&-&";

	/**
	 * @param jugador
	 */
	public TCPClient(Usuario usuario, FXMLIniciarSesion ventIniciarSesion) {
		super();
		usuarioLogueado = usuario;
		ventanaIniciarSesion =ventIniciarSesion;
	}

	@Override
	public void run() {
		try {
			clientSocket = new Socket(IPSERVIDOR, PUERTO);
			entrada = new DataInputStream(clientSocket.getInputStream());
			salida = new DataOutputStream(clientSocket.getOutputStream());
			//salida.writeUTF("Una nueva conexión ha sido establecida...");
			
			new TCPClientThread(this, ventanaIniciarSesion);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
			e.printStackTrace();
			GestorLogs.registrarError(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			GestorLogs.registrarError(e.getMessage());
			e.printStackTrace();
			GestorLogs.registrarError(e.getMessage());
		}
	}
	
	public void iniciarSesion(Usuario usuario){
		try {
			salida.writeInt(1);
			salida.writeUTF(usuario.getUserName() + delimitador + usuario.getPassword());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
