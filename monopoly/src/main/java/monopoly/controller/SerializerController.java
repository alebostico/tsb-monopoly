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
import java.util.Date;

import monopoly.model.Juego;
import monopoly.util.GestorLogs;
import monopoly.util.message.game.ConfirmGameReloadedMessage;

/**
 * Clase que se encarga de serializar y deserializar un juego en un archivo.
 * También se encarga de mantener actualizada la base de datos con los datos de
 * los juegos guardados.
 * 
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class SerializerController {

	/**
	 * El directorio en donde se van a guardar los juegos.
	 */
	private static final String SAVES_PATH = "saves";

	/**
	 * La extensión de los archivos de "juagos guardados" de Monopoly
	 */
	private static final String FILE_EXTENSION = ".game";

	/**
	 * Guarda (serializa) un juego en un archivo. Además actualiza o guarda los
	 * datos del juego en la base de datos para poder restaurarlo más adelante.
	 * 
	 * @param juegoController
	 *            El {@code JuegoController} del juego que se quiere guardar.
	 * @throws IOException
	 *             Si no se puede serializar el juego.
	 */
	public static void saveGame(JuegoController juegoController)
			throws IOException {
		String gameName;
		File savesPath = new File(SAVES_PATH);
		OutputStream file = null;
		OutputStream buffer = null;
		ObjectOutput output = null;

		try {
			// Creo el directorio SAVES_PATH si no existe
			if (!savesPath.exists())
				savesPath.mkdirs();

			gameName = juegoController.getJuego().getUniqueID();
			file = new FileOutputStream(SAVES_PATH + File.separator + gameName
					+ FILE_EXTENSION);
			buffer = new BufferedOutputStream(file);
			output = new ObjectOutputStream(buffer);
			output.writeObject(juegoController);

			// -------------------------------------------
			// Guardo en la base información del juego
			Juego savedGame = JuegoController
					.buscarJuegoGuardado(juegoController.getJuego().getUniqueID());

			if (savedGame == null) {
				juegoController.getJuego().setNombreArchivo(
						gameName + FILE_EXTENSION);
				juegoController.getJuego().setFechaRestaurado(null);
				juegoController.getJuego().setFechaGuardado(new Date());
				JuegoController.saveJuego(juegoController.getJuego());
			} else {
				// savedGame.setNombreArchivo(gameName + FILE_EXTENSION);
				savedGame.setFechaRestaurado(null);
				savedGame.setFechaGuardado(new Date());
				JuegoController.updateJuego(savedGame);
			}
			// -------------------------------------------
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

	/**
	 * Restaura (deserializa) un juego desde un archivo. No actualiza nada en la
	 * base de datos. El cliente debe enviar un
	 * {@link ConfirmGameReloadedMessage} confirmando que pudo restaurar el
	 * juego correctamente. Luego se debe usar el método
	 * {@link SerializerController#updateRestoreDate(Juego)} para actualizar la
	 * fecha de restauración en la base de datos.
	 * 
	 * @param nombre
	 *            El nombre del juego que se quiere restarurar (deserializar).
	 * @return Un objeto del tipo <code>JuegoController</code> deserializado.
	 * @throws IOException
	 *             If any of the usual Input/Output related exceptions occur.
	 * @throws ClassNotFoundException
	 *             If the class of a serialized object cannot be found.
	 * @see {@link SerializerController#updateRestoreDate(Juego)},
	 *      {@link ConfirmGameReloadedMessage}
	 */
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

			/*
			 * Borro el juego de la base de datos ... pero lo borro cuando me
			 * llega la confirmación de que se cargó correctamente. ver
			 * SerializerController.updateRestoreDate(Juego juego)
			 */
			// JuegoController.deleteJuego(juego);
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

	/**
	 * Actualiza la fecha de restauración de un juego con la fecha actual. Luego
	 * de que el cliente restaura correctamente el juego, envía un
	 * {@link ConfirmGameReloadedMessage} y se actualiza la fecha de
	 * restauración en la base de datos con este método.
	 * 
	 * @param juego
	 *            El juego para actualizar
	 * @return El juego actualizado o {@code null} si no se pudo actualizar
	 */
	public static Juego updateRestoreDate(Juego juego) {
		Juego juegoDB = JuegoController
				.findJuegoByUniqueId(juego.getUniqueID());
		if (juegoDB == null) {
			GestorLogs.registrarError("No se pudo confirmar el juego "
					+ juego.getUniqueID()
					+ " porque no se encontró en la base de datos");
		} else {
			juegoDB.setFechaRestaurado(new Date());
			juegoDB = JuegoController.updateJuego(juegoDB);
			GestorLogs.registrarDebug("Restauración del Juego "
					+ juegoDB.getUniqueID() + " confirmada en la BD con fecha "
					+ juegoDB.getFechaRestaurado());
		}
		return juegoDB;
	}

}
