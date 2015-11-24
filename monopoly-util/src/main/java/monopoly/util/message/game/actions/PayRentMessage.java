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
public class PayRentMessage implements Serializable {

	private static final long serialVersionUID = 4365138030864238155L;

	public final String idJuego;
	public final int propiedadId;
	
	public PayRentMessage(String idJuego, int propiedadId){
		this.idJuego = idJuego;
		this.propiedadId = propiedadId;
	}

}
