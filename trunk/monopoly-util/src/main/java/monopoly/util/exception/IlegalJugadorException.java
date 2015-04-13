/**
 * 
 */
package monopoly.util.exception;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class IlegalJugadorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -438077570013700327L;

	//Parameterless Constructor
		public IlegalJugadorException(){}
		
		//Constructor that accepts a message
	    public IlegalJugadorException(String message)
	    {
	       super(message);
	    }
	    
}
