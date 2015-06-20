/**
 * 
 */
package monopoly.util.message.game.actions;

import java.io.Serializable;

import monopoly.util.constantes.EnumsTipoImpuesto;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SuperTaxMessage implements Serializable {

	private static final long serialVersionUID = 4308189315498200215L;

	public final String idJuego;
	public final EnumsTipoImpuesto tipoImpuesto;
	
	public SuperTaxMessage(String idJuego, EnumsTipoImpuesto tipoImpuesto){
		this.idJuego = idJuego;
		this.tipoImpuesto = tipoImpuesto;
	}
	
}
