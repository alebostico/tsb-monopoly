/**
 * 
 */
package monopoly.util.exception;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class IllegalJugadorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -438077570013700327L;

	//Parameterless Constructor
		public IllegalJugadorException(){}
		
		//Constructor that accepts a message
	    public IllegalJugadorException(String message)
	    {
	       super(message);
	    }
	    
}
