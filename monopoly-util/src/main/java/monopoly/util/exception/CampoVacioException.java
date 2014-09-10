/**
 * 
 */
package monopoly.util.exception;

/**
 * @author pablo
 *
 */
public class CampoVacioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 161043080292310215L;

	//Parameterless Constructor
	public CampoVacioException(){}
	
	//Constructor that accepts a message
    public CampoVacioException(String message)
    {
       super(message);
    }
	
}
