/**
 * 
 */
package monopoly.client.connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import monopoly.client.gui.FXMLVentanaJuego;
import monopoly.client.model.Jugador;
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
	
	private Jugador jugador;
	private FXMLVentanaJuego juego;

	public static void main(String args[]) throws Exception {
		//runJuego();

		/*
		 * String nombre = args[0]; clientSocket = new Socket(IPSERVIDOR,
		 * PUERTO); PrintWriter printwriter = new
		 * PrintWriter(clientSocket.getOutputStream(),true);
		 * 
		 * BufferedReader bufferedReader = new BufferedReader(new
		 * InputStreamReader( System.in));
		 * 
		 * while(true){ String readerInput = bufferedReader.readLine();
		 * printwriter.println(nombre + ": " + readerInput); }
		 */
	}

	/**
	 * @param jugador
	 */
	public TCPClient(FXMLVentanaJuego juego , Jugador jugador) {
		super();
		this.jugador = jugador;
		this.juego = juego;
	}

	@Override
	public void run() {
		try {
			clientSocket = new Socket(IPSERVIDOR, PUERTO);
			entrada = new DataInputStream(clientSocket.getInputStream());
			salida = new DataOutputStream(clientSocket.getOutputStream());
			salida.writeUTF("Una nueva conexión ha sido establecida...");
			
			new ThreadClient();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @return the jugador
	 */
	public Jugador getJugador() {
		return jugador;
	}

	/**
	 * @param jugador the jugador to set
	 */
	public void setJugador(Jugador jugador) {
		this.jugador = jugador;
	}

}
