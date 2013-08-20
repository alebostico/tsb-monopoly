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

    @SuppressWarnings("resource")
    public static void main(String argv[]) throws Exception {
	ServerSocket serverSocket = new ServerSocket(PORT);
	
	while (true) {
	    Socket connectionSocket = serverSocket.accept();
	    new TCPServerThread(connectionSocket).start();
	    System.out.println("Servidor levantado..");
	}
    }
    
    public static class TCPServerThread extends Thread{
	Socket socket;
	
	public TCPServerThread(Socket socket) {
	    // TODO Auto-generated constructor stub
	    this.socket = socket;
	}
	
	public void run(){
	    String mensaje = null;
	    try{
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
		while((mensaje = bufferedReader.readLine()) != null){
		    System.out.println("recibiendo mensajes del cliente: " + mensaje);
		}
		socket.close();
	    }
	    catch(IOException e){
		e.printStackTrace();
	    }
	}
    }

}
