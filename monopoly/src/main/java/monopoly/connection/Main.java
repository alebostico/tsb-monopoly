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

	private static GameServer gs;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		gs = null;
		try {
			gs = new MonopolyGame();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			gs.shutdownServer();
		}

	}

}
