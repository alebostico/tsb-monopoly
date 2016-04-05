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
public class AuctionDecideMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1689259280027955802L;

	public final String idJuego;
	public final String mensaje;
	public final int monto;
	public final Object propiedad;
	public final boolean respuesta;
	public final Object jugadorInicial;
	
	public AuctionDecideMessage(String idJuego, int monto, Object propiedad, boolean respuesta, Object jugadorInicial){
		this.idJuego = idJuego;
		this.mensaje = "";
		this.monto = monto;
		this.propiedad = propiedad;
		this.respuesta = respuesta;
		this.jugadorInicial = jugadorInicial;
	}
	
	public AuctionDecideMessage(String mensaje,int monto, Object propiedad, Object jugadorInicial)
	{
		this.idJuego = null;
		this.mensaje = mensaje;
		this.monto = monto;
		this.propiedad = propiedad;
		this.respuesta = false;
		this.jugadorInicial = jugadorInicial;
	}
}
