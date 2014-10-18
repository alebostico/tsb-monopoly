/**
 * 
 */
package monopoly.connection;

import java.io.IOException;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 *
 */
public class Main {

	/**
	 * @param args
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// TCPServer threadServer = new TCPServer();
		// threadServer.start();
		// System.out.println("Se inicio el servidor..");
		GameServer gs = null;
		try {
			gs = new GameServer();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gs.shutdownServer();
		}

	}

}
