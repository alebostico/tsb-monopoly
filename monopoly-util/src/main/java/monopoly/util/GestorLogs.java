package monopoly.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
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
	 * Determina si el logger imprime tambi&eacute;n en la consola
	 */
	private static boolean printToConsole = true;

	/**
	 * Determina el nivel de mensajes que se va a loguear. Loguea el tipo
	 * especificado y menores y se ignoran los mensajes de tipo mayor al
	 * configurado. Ej: si se especifica GestorLogs.MSG_INFO, loguea los
	 * mensajes del tipo GestorLogs.MSG_INFO, GestorLogs.MSG_WARNING y
	 * GestorLogs.MSG_ERROR.
	 */
	// En este caso loguea MSG_INFO, MSG_WARNING y MSG_ERROR.
	// private static int loggingDetailLevel = GestorLogs.MSG_INFO;
	
	// En este caso loguea MSG_DEBUG, MSG_INFO, MSG_WARNING y MSG_ERROR.
	private static int loggingDetailLevel = GestorLogs.MSG_DEBUG;
	
	// En este caso loguea MSG_DEBUG_DETAIL, MSG_DEBUG, MSG_INFO, MSG_WARNING y MSG_ERROR.
		// private static int loggingDetailLevel = GestorLogs.MSG_DEBUG_DETAIL;

	private static final String pathFolderLog = System.getProperty("user.home")
			+ File.separator + "monopoly_logs";;
	private static File fileLog = null;
	private static File folderLog = null;
	private static FileWriter escritor;
	private static PrintStream ps;

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

			// Imprime al terminal informaci&oacute;n sobre el logger...
			System.out.print("Logging to file '" + fileLog + "'");
			if (GestorLogs.printToConsole) {
				System.out.print(" and to terminal");
			}
			System.out.print("\n");
			// .....................................................

			ps = new PrintStream(fileLog);

			if (!fileLog.exists()) {
				fileLog.createNewFile();
			}
			GestorLogs.registrarLog("======================================");
			GestorLogs.registrarLog("Logger iniciado");
			GestorLogs.registrarLog("======================================");

		} catch (IOException io) {
			System.out.println(io.getMessage());
		}
	}

	/**
	 * M&eacute;todo para registrar errores de las diferentes funcionalidades
	 * del sistemas en un archivo de texto. &Eacute;ste m&eacute;todo genera una
	 * carpeta en donde se guardan los logs del d&iacute;a.
	 * 
	 * @param error
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarError(String error) {
		GestorLogs.registrar(GestorLogs.MSG_ERROR, error);
	}

	public static void registrarError(Exception error) {
		StringBuilder sb = new StringBuilder();

		if (!StringUtils.IsNullOrEmpty(error.getMessage()))
			sb.append("Message Error: " + error.getMessage());
		if (error.getStackTrace() != null) {
			if (!StringUtils.IsNullOrEmpty(error.getStackTrace().toString()))
				sb.append("Stack Trace: " + error.getStackTrace().toString());
		}
		GestorLogs.registrar(GestorLogs.MSG_ERROR, sb.toString());
	}

	/**
	 * M&eacute;todo para registrar warnings de las diferentes funcionalidades
	 * del sistemas en un archivo de texto. &eacute;ste m&eacute;todo genera una
	 * carpeta en donde se guardan los logs del d&iacute;a.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarWarning(String log) {
		GestorLogs.registrar(GestorLogs.MSG_WARNING, log);
	}

	/**
	 * M&eacute;todo para registrar logs de las diferentes funcionalidades del
	 * sistemas en un archivo de texto. &Eacute;ste m&eacute;todo genera una
	 * carpeta en donde se guardan los logs del d&iacute;a.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarLog(String log) {
		GestorLogs.registrar(GestorLogs.MSG_INFO, log);
	}

	/**
	 * M&eacute;todo para registrar informaci&oacute;n de debug de las
	 * diferentes funcionalidades del sistemas en un archivo de texto.
	 * &eacute;ste m&eacute;todo genera una carpeta en donde se guardan los logs
	 * del d&iacute;a.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarDebug(String log) {
		GestorLogs.registrar(GestorLogs.MSG_DEBUG, log);
	}

	/**
	 * M&eacute;todo para registrar informaci&oacute;n de debug m&aacute;s
	 * detallada de las diferentes funcionalidades del sistemas en un archivo de
	 * texto. &eacute;ste m&eacute;todo genera una carpeta en donde se guardan
	 * los logs del d&iacute;a.
	 * 
	 * @param log
	 *            Mensaje que se va a escribir en el txt
	 */
	public static void registrarDebugDetail(String log) {
		GestorLogs.registrar(GestorLogs.MSG_DEBUG_DETAIL, log);
	}

	/**
	 * 
	 * @param e
	 */
	public static void registrarException(Exception e) {
		GestorLogs.registrar(GestorLogs.MSG_ERROR, "Exception: ");
		// GestorLogs.registrar(GestorLogs.MSG_ERROR, e.toString());
		// GestorLogs.registrar(GestorLogs.MSG_ERROR, e.getMessage());
		e.printStackTrace(ps);
		ps.flush();
		if (GestorLogs.printToConsole) {
			e.printStackTrace();
		}
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
		if (tipoMensaje > GestorLogs.loggingDetailLevel) {
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

			if (GestorLogs.printToConsole) {
				// formateador = new SimpleDateFormat("HH:mm:ss");

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
		return loggingDetailLevel;
	}

	/**
	 * @return the printToConsole
	 */
	public static boolean isPrintToConsole() {
		return printToConsole;
	}

	/**
	 * @param printToConsole
	 *            the printToConsole to set
	 */
	public static void setPrintToConsole(boolean printToConsole) {
		GestorLogs.printToConsole = printToConsole;
	}

	/**
	 * @param loggingDetailLevel
	 *            the loggingDetailLevel to set
	 */
	public static void setLoggingDetailLevel(int loggingDetailLevel) {
		GestorLogs.loggingDetailLevel = loggingDetailLevel;
	}

}
