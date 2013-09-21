/**
 * 
 */
package monopoly.connection;

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TCPServer threadServer = new TCPServer();
		threadServer.start();
	}

}
