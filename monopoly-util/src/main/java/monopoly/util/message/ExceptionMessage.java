/**
 * 
 */
package monopoly.util.message;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class ExceptionMessage implements Serializable {

	private static final long serialVersionUID = 2317691159922810533L;
	
	public final Exception message; // Original message from a client.

	/**
	 * @param message
	 */
	public ExceptionMessage(Exception message) {
		super();
		this.message = message;
	}
}
