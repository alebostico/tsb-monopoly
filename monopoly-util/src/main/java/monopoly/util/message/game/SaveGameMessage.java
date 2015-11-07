package monopoly.util.message.game;

import java.io.IOException;
import java.io.Serializable;

public class SaveGameMessage implements Serializable {

	private static final long serialVersionUID = -7018257136604326629L;

	public final String uniqueIdJuego; // The ID of the client who sent that
										// message.
	public final IOException exception; // Si no se puede guardar el juego
										// devuelve la excepcion

	public SaveGameMessage(String uniqueIdJuego, IOException exception) {
		this.uniqueIdJuego = uniqueIdJuego;
		this.exception = exception;
	}

}
