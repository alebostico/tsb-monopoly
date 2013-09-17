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

	// Nivel de info a loguear e imprimir
	public static final int MSG_ERROR = 0;
	public static final int MSG_WARNING = 1;
	public static final int MSG_INFO = 2;
	public static final int MSG_DEBUG = 3;
	public static final int MSG_DEBUG_DETAIL = 4;

	/**
	 * Determina si el logger imprime también en la consola
	 */
	private static final boolean PRINT_TO_CONSOLE = true;

	/**
	 * Determina el nivel de mensajes que se va a loguear. Loguea el tipo
	 * especificado y menores y se ignoran los mensajes de tipo mayor al
	 * configurado. Ej: si se especifica GestorLogs.MSG_INFO, loguea los
	 * mensajes del tipo GestorLogs.MSG_INFO, GestorLogs.MSG_WARNING y
	 * GestorLogs.MSG_ERROR.
	 */
	// En este caso loguea MSG_INFO, MSG_WARNING y MSG_ERROR.
	private static final int LOGGING_DETAIL_LEVEL = GestorLogs.MSG_INFO;
	//private static final int LOGGING_DETAIL_LEVEL = GestorLogs.MSG_DEBUG;
	//private static final int LOGGING_DETAIL_LEVEL = GestorLogs.MSG_DEBUG_DETAIL;

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
			// .....................................................

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
	 * Método para registrar errores de las diferentes funcionalidades del
	 * sistemas en un archivo de texto. éste método genera una carpeta en donde
	 * se guardan los logs del día.
	 * 
	 * @param error
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarError(String error) {
		GestorLogs.registrar(GestorLogs.MSG_ERROR, error);
	}

	/**
	 * Método para registrar warnings de las diferentes funcionalidades del
	 * sistemas en un archivo de texto. éste método genera una carpeta en donde
	 * se guardan los logs del día.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarWarning(String log) {
		GestorLogs.registrar(GestorLogs.MSG_WARNING, log);
	}

	/**
	 * Método para registrar logs de las diferentes funcionalidades del sistemas
	 * en un archivo de texto. éste método genera una carpeta en donde se
	 * guardan los logs del día.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarLog(String log) {
		GestorLogs.registrar(GestorLogs.MSG_INFO, log);
	}

	/**
	 * Método para registrar información de debug de las diferentes
	 * funcionalidades del sistemas en un archivo de texto. éste método genera
	 * una carpeta en donde se guardan los logs del día.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarDebug(String log) {
		GestorLogs.registrar(GestorLogs.MSG_DEBUG, log);
	}

	/**
	 * Método para registrar información de debug más detallada de las
	 * diferentes funcionalidades del sistemas en un archivo de texto. éste
	 * método genera una carpeta en donde se guardan los logs del día.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarDebugDetail(String log) {
		GestorLogs.registrar(GestorLogs.MSG_DEBUG_DETAIL, log);
	}

	/**
	 * Registra un mensaje del tipo especificado. Devuelve true si el mensaje se
	 * registro y false si por algun motivo no se registro.
	 * 
	 * @param tipoMensaje
	 *            El tipo de mensaje que se quiere registrar
	 * @param log
	 *            El mensaje que se quiere registrar
	 * 
	 * @return true si el mensaje de log se registro; false en caso contrario
	 */
	public static boolean registrar(int tipoMensaje, String log) {

		// Si el mensaje que se quiere registrar es de un tipo
		// mayor al configurado, no se loguea y se sale.
		if (tipoMensaje > GestorLogs.LOGGING_DETAIL_LEVEL) {
			return false;
		}

		if (GestorLogs.fileLog == null) {
			GestorLogs.initLogger();
		}

		Date fechaActual = new Date();
		SimpleDateFormat formateador;
		StringBuffer mensajeFile = new StringBuffer();

		try {
			escritor = new FileWriter(fileLog, true);
			formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");

			mensajeFile.append(formateador.format(fechaActual));

			switch (tipoMensaje) {
			case GestorLogs.MSG_ERROR:
				mensajeFile.append("ERROR: ");
				break;
			case GestorLogs.MSG_WARNING:
				mensajeFile.append("WARN: ");
				break;
			case GestorLogs.MSG_INFO:
				mensajeFile.append("INFO: ");
				break;
			case GestorLogs.MSG_DEBUG:
				mensajeFile.append("DEBUG: ");
				break;
			case GestorLogs.MSG_DEBUG_DETAIL:
				mensajeFile.append("DETAIL: ");
				break;
			default:
				break;
			}

			mensajeFile.append(log);
			mensajeFile.append("\n");

			escritor.append(mensajeFile.toString());

			if (GestorLogs.PRINT_TO_CONSOLE) {
				//formateador = new SimpleDateFormat("HH:mm:ss");

				// Si es ERROR o WARNING imprimo en rojo en la cosola
				switch (tipoMensaje) {
				case GestorLogs.MSG_ERROR:
					System.err.println("Logger: ERROR - " + log + "\n");
					break;
				case GestorLogs.MSG_WARNING:
					System.err.println("Logger: WARN - " + log + "\n");
					break;
				default:
					System.out.println("Logger: " + log);
					break;
				}
			}

			escritor.close();

			return true;

		} catch (IOException io) {
			System.out.println(io.getMessage());
			return false;
		}

	}

	/**
	 * @return the loggingDetailLevel
	 */
	public static int getLoggingDetailLevel() {
		return LOGGING_DETAIL_LEVEL;
	}

}
