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
     * Método para registrar logs de las diferentes funcionalidades del sistemas en un archivo de texto.
     * éste método genera una carpeta en donde se guardan los logs del día.
     * 
     * @param log - Mensaje que se va a escribir en el txt
     */
    public static void registrarLog(String log) {
	File fileLog = null;
	File folderLog = null;
	FileWriter escritor = null;
	String pathFolderLog = System.getProperty("user.home") + File.separator + "monopoly_logs";

	try{
	    folderLog = new File(pathFolderLog);
	    if(!folderLog.exists())
		folderLog.mkdir();
	    
	    Date fechaActual = new Date();
	    SimpleDateFormat formateador = new SimpleDateFormat("yyMMdd");
	    fileLog = new File(pathFolderLog + File.separator + "log_" + formateador.format(fechaActual) + ".txt");
	    if(fileLog.exists())
	    {
		escritor = new FileWriter(fileLog);
		formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss \t");
		escritor.write(formateador.format(fechaActual) + log);
		escritor.close();
	    }
	    else
	    {
		fileLog.createNewFile();
		escritor = new FileWriter(fileLog);
		formateador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss \t");
		escritor.write(formateador.format(fechaActual) + log);
		escritor.close();
	    }
	}
	catch(IOException io){
	    System.out.println(io.getMessage());
	}
    }
}
