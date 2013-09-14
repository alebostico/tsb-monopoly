/**                                                     
 * 
 */
package monopoly.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class GestorLogs {

	/**
	 * Determina si el logger imprime también en la consola
	 */
	private static final boolean PRINT_TO_CONSOLE = true;
	private static final String pathFolderLog = System.getProperty("user.home")
			+ File.separator + "monopoly_logs";;
	private static File fileLog = null;
	private static File folderLog = null;
	private static FileWriter escritor;

	
	/**
	 * Inicializa el logger. Crea las carpetas y archivos necesarios.
	 */
	private static void initLogger() {

		try {
			folderLog = new File(pathFolderLog);
			if (!folderLog.exists())
				folderLog.mkdir();

			Date fechaActual = new Date();
			SimpleDateFormat formateador = new SimpleDateFormat("yyMMdd");
			fileLog = new File(pathFolderLog + File.separator + "log_"
					+ formateador.format(fechaActual) + ".txt");
			
			// Imprime al terminal información sobre el logger...
			System.out.print("Logging to file '" + fileLog + "'");
			if (GestorLogs.PRINT_TO_CONSOLE) {
				System.out.print(" and to terminal");
			}
			System.out.print("\n");
			//.....................................................

			if (!fileLog.exists()) {
				fileLog.createNewFile();
			}
			GestorLogs.registrarLog("======================================");
			GestorLogs.registrarLog("Logger iniciado");

		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	/**
	 * Método para registrar logs de las diferentes funcionalidades del sistemas
	 * en un archivo de texto. éste método genera una carpeta en donde se
	 * guardan los logs del día.
	 * 
	 * @param log
	 *            - Mensaje que se va a escribir en el txt
	 */
	public static void registrarLog(String log) {

		if (GestorLogs.fileLog == null) {
			GestorLogs.initLogger();
		}

		Date fechaActual = new Date();
		SimpleDateFormat formateador;

		try {

			escritor = new FileWriter(fileLog, true);
			formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss \t");
			escritor.append(formateador.format(fechaActual) + log + "\n");
			
			if (GestorLogs.PRINT_TO_CONSOLE) {
				formateador = new SimpleDateFormat("HH:mm:ss");
				System.out.println(formateador.format(fechaActual) + " logger: " + log);
			}
			
			escritor.close();
			
		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}
}
