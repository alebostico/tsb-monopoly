/**
 * 
 */
package monopoly.util.message.game;

import java.io.Serializable;

/**
 * Mensaje que se usa para que un cliente le informe al servidor que el Jugador
 * se declaró en bancarrota. El servidor debe eliminar al Jugador y
 * desconectarlo. También se usa para que el servidor informe a los clientes que
 * el jugador se declaró en bancarrota y que se fue del juego.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class BankruptcyMessage implements Serializable {

	private static final long serialVersionUID = -742313204947249484L;
	public final String message; // Original message from a client.
	public final String UniqueIdJuego; // The ID of the client who sent that
										// message.

	/**
	 * Crea un nuevo mensaje de bancarrota
	 * 
	 * @param UniqueIdJuego
	 *            El UniqueId del juego
	 * @param mensaje
	 *            El mensaje
	 * 
	 */
	public BankruptcyMessage(String UniqueIdJuego, String mensaje) {
		this.UniqueIdJuego = UniqueIdJuego;
		this.message = mensaje;
	}

}
