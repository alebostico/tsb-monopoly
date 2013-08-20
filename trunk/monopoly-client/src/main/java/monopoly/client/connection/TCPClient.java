/**
 * 
 */
package monopoly.client.connection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import monopoly.util.LectorXML;

/**
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class TCPClient {
	
	private static final String IPSERVIDOR = LectorXML.getIpServidor();
	private static final int PUERTO = LectorXML.getPuertoDeConexion();
	static Socket clientSocket;	

	public static void main(String args[]) throws Exception {
		runJuego();
		/*
		String nombre = args[0];
		clientSocket = new Socket(IPSERVIDOR, PUERTO);
		PrintWriter printwriter = new PrintWriter(clientSocket.getOutputStream(),true);
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
				System.in));
		
		while(true){
			String readerInput = bufferedReader.readLine();
			printwriter.println(nombre + ": " + readerInput);
		}
		*/
	}
	
	public static void runJuego() throws IOException{
		clientSocket = new Socket(IPSERVIDOR, PUERTO);  
        DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());  
        dos.writeUTF("A new connection has been made...");
	}

}
