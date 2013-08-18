/**
 * 
 */
package monopoly.client.util;

import java.net.URL;

import monopoly.util.LectorXML;



/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 * @author Oliva Pablo
 * 
 */
public class GestorPathResources{

    private static final ClassLoader loader = GestorPathResources.class.getClassLoader();
    
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

    public static String getPathTarjetas() {
	String pathResource = LectorXML.getPathTarjetas();
	URL url = loader.getResource(pathResource);
	return url.getPath();
    }
}
