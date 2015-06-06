/**
 * 
 */
package monopoly.util.message.game;

import java.io.Serializable;

import monopoly.util.constantes.EnumAction;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class CompleteTurnMessage implements Serializable{

	private static final long serialVersionUID = -4855020643940477373L;

	public final String message;
	public final EnumAction action;
	public final Object status;
	
	public CompleteTurnMessage(String message,EnumAction action, Object status){
		this.message = message;
		this.action= action;
		this.status = status;
	}
}
