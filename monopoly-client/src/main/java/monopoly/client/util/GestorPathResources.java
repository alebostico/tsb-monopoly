/**
 * 
 */
package monopoly.client.util;

import java.net.URL;

import monopoly.util.LectorXML;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * 
 */
public class GestorPathResources {

	private static final ClassLoader loader = GestorPathResources.class
			.getClassLoader();

	public static String getPathFichas() {
		String pathResource = LectorXML.getPathFichas();
		URL url = loader.getResource(pathResource);
		return url.getPath();
	}

	public static String getPathTablero() {
		String pathResource = LectorXML.getPathTablero();
		URL url = loader.getResource(pathResource);
		return url.getPath();
	}

	public static String getPathTarjetasFrente() {
		String pathResource = LectorXML.getPathTarjetasFrente();
		URL url = loader.getResource(pathResource);
		return url.getPath();
	}

	public static String getPathTarjetasDorso() {
		String pathResource = LectorXML.getPathTarjetasDorso();
		URL url = loader.getResource(pathResource);
		return url.getPath();
	}
}
