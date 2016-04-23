/**
 * 
 */
package monopoly.client.util;

import java.net.URL;
import java.nio.file.Paths;

import monopoly.util.LectorXML;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class GestorPathResources {

	public static String getPathFichas() {
		String pathResource = LectorXML.getPathFichas();
		URL url = GestorPathResources.class.getResource(pathResource);
		return url.getPath();
	}

	public static String getPathTablero() {
		String pathResource = LectorXML.getPathTablero();
		URL url = GestorPathResources.class.getResource(pathResource);
		return url.getPath();
	}

	public static String getPathTarjetasFrente() {
		String pathResource = LectorXML.getPathTarjetasFrente();
		URL url = GestorPathResources.class.getResource(pathResource);
		return url.getPath();
	}

	public static String getPathTarjetasDorso() {
		String pathResource = LectorXML.getPathTarjetasDorso();
		URL url = GestorPathResources.class.getResource(pathResource);
		return url.getPath();
	}
	
	public static String getPathLogos(String imagen) {
		String pathResource = LectorXML.getPathLogos();
		URL url = GestorPathResources.class.getResource(Paths.get(pathResource,imagen).toString());
		return url.getPath();
	}
}
