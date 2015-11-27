package monopoly.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import monopoly.model.Juego;

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

			// Guardo en la base información del juego
			JuegoController.saveJuego(juego.getJuego());
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

	public static JuegoController loadGame(String nombre) throws IOException,
			ClassNotFoundException {
		Juego juego = JuegoController.buscarJuegoGuardado(nombre);
		JuegoController juegoController = null;

		InputStream file = null;
		InputStream buffer = null;
		ObjectInput input = null;

		String gameName = juego.getNombreArchivo();

		try {
			file = new FileInputStream(SAVES_PATH + File.separator + gameName);
			buffer = new BufferedInputStream(file);
			input = new ObjectInputStream(buffer);
			juegoController = (JuegoController) input.readObject();

			// Borro el juego de la base de datos
			JuegoController.deleteJuego(juego);
		} finally {
			if (input != null)
				input.close();
			input = null;

			if (buffer != null)
				buffer.close();
			buffer = null;

			if (file != null)
				file.close();
			file = null;

		}

		return juegoController;
	}

}
