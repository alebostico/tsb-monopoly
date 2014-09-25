/**
 * 
 */
package monopoly.util.exception;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class EmailInvalidoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailInvalidoException(){}
	
	public EmailInvalidoException(String message)
	{
		super(message);
	}
}
