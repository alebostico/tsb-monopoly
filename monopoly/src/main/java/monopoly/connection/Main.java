/**
 * 
 */
package monopoly.connection;

import java.io.IOException;

import monopoly.controller.PartidasController;


/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
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
			gs.setAutoreset(true);
			PartidasController.getInstance().setMonopolyGame(gs);

		} catch (IOException e) {
			e.printStackTrace();
			gs.shutdownServer();
		}

	}

}
