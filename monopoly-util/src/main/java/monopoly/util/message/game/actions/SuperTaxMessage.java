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
public class SuperTaxMessage implements Serializable {

	private static final long serialVersionUID = 4308189315498200215L;

	public final String idJuego;
	//public final EnumsTipoImpuesto tipoImpuesto;
	public final int montoAPagar;
	
//	public SuperTaxMessage(String idJuego, EnumsTipoImpuesto tipoImpuesto){
//		this.idJuego = idJuego;
//		this.tipoImpuesto = tipoImpuesto;
//	}
	
	public SuperTaxMessage(String idJuego, int monto){
		this.idJuego = idJuego;
		this.montoAPagar = monto;
	}
	
}
