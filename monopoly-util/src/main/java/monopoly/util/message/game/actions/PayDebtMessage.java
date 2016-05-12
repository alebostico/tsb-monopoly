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
public class PayDebtMessage extends Exception implements Serializable{

	private static final long serialVersionUID = -3516861299229472964L;
	
	public final String idJuego;
	
	public PayDebtMessage(String idJuego){
		this.idJuego = idJuego;
	}

}
