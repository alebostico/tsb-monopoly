/**
 * 
 */
package monopoly.util.constantes;

import java.io.Serializable;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 * Clase para identificar la acción realizada. 
 */
public enum EnumAction implements Serializable{
	
	BUY_PROPERTY("Comprar Propiedad"),
	AUCTION_PROPERTY("Subastar Propiedad"),
	CHANCE_CARD("Tarjeta de la Suerte"),
	COMMUNITY_CARD("Tarjeta de la Comunidad"),
	PAY_TO_BANK("Pagar al Banco");
	
	private String descripcion;
	
	private EnumAction(String descripcion)
	{
		this.descripcion= descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
