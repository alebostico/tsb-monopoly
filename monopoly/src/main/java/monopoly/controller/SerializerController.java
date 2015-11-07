package monopoly.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class SerializerController {

	private static final String SAVES_PATH = "saves";

	/**
	 * Guarda (serializa) un juego en un archivo.
	 * 
	 * @param juego
	 *            El {@code JuegoController} del juego que se quiere guardar.
	 * @throws IOException
	 *             Si no se puede serializar el juego.
	 */
	public static void saveGame(JuegoController juego) throws IOException {
		String gameName;
		File savesPath = new File(SAVES_PATH);
		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;

		// TODO: Agregar a la base de datos información sobre el juego que se
		// guarda.

		try {
			// Creo el directorio SAVES_PATH si no existe
			if (!savesPath.exists()) 
				savesPath.mkdirs();
			
			gameName = juego.getJuego().getUniqueID();
			file = new FileOutputStream(SAVES_PATH + File.separator + gameName
					+ ".game");
			// file = new FileOutputStream("test.game");
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(juego);
		} finally {
			if (output != null)
				output.close();
			output = null;

			if (buffer != null)
				buffer.close();
			buffer = null;

			if (file != null)
				file.close();
			file = null;
		}
	}

}
