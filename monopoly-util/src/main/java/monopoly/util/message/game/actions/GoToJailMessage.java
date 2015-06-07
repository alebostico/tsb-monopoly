/**
 * 
 */
package monopoly.util.message.game.actions;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class GoToJailMessage implements Serializable {

	private static final long serialVersionUID = -1105475992097892486L;
	
	public final String idJuego;
	
	public GoToJailMessage(String idJuego){
		this.idJuego = idJuego;
	}
}
