/**
 * 
 */
package monopoly.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * @author pablo
 * 
 */
public class LectorXML {

	/**
	 * Método para obtener todos los nodos del XML con las rutas de las carpetas
	 * de resources
	 * 
	 * @return
	 */
	private static List<Element> getNodosFolder() {
		List<Element> folderList = new ArrayList<Element>();
		try {
			Element raiz = getRaizXML();

			if (raiz.getName().equals("configuration")) {
				// obtenemos las configuraciones en una lista y las pasamos a un
				// iterator
				// para recorrerlas
				List<Element> configuraciones = raiz.getChildren();
				Iterator<Element> itConfiguraciones = configuraciones
						.iterator();

				while (itConfiguraciones.hasNext()) {

					Element elementos = (Element) itConfiguraciones.next();

					if (elementos.getName().equals("path_folders")) {
						// Extraemos todos los path de las carpetas de resources
						// del XML.
						folderList.addAll(elementos.getChildren());
						return folderList;
					}
				}
			}
		} catch (Exception ex) {
			GestorLogs.registrarLog("ERROR: " + ex.getMessage());
		}
		return folderList;
	}

	/**
	 * 
	 * @return Devuelve la ruta dentro de resource de la carpeta fichas.
	 */
	public static String getPathFichas() {
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if (folderElement.getAttributeValue("id").equals("fichas")) {
				path = folderElement.getChild("path").getValue();
			}
		}
		return path;
	}

	/**
	 * 
	 * @return Devuelve la ruta de la carpeta tablero dentro de resource.
	 */
	public static String getPathTablero() {
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if (folderElement.getAttributeValue("id").equals("tablero")) {
				path = folderElement.getChild("path").getValue();
			}
		}
		return path;
	}

	/**
	 * 
	 * @return Devuelve la ruta de la carpeta que contienen las tarjetas con la
	 *         imagen frontal de la tarjeta dentro de resource.
	 */
	public static String getPathTarjetasFrente() {
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if (folderElement.getAttributeValue("id").equals("tarjetas")) {
				path = folderElement.getChild("frente").getChild("path")
						.getValue();
			}
		}
		return path;
	}

	/**
	 * 
	 * @return Devuelve la ruta de la carpeta que contienen las tarjetas con la
	 *         imagen dorsal de la tarjeta dentro de resource.
	 */
	public static String getPathTarjetasDorso() {
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if (folderElement.getAttributeValue("id").equals("tarjetas")) {
				path = folderElement.getChild("dorso").getChild("path")
						.getValue();
			}
		}
		return path;
	}

	/**
	 * Método para obtener la ip del servidor.
	 * 
	 * @return Devuelve la ip con el cual se conectará la aplicación cliente.
	 */
	public static String getIpServidor() {
		Element raiz = getRaizXML();
		// obtenemos las configuraciones en una lista y las pasamos a un
		// iterator
		// para recorrerlas
		List<Element> configuraciones = raiz.getChildren();
		Iterator<Element> itConfiguraciones = configuraciones.iterator();

		while (itConfiguraciones.hasNext()) {

			Element elemento = (Element) itConfiguraciones.next();

			if (elemento.getName().equals("socket")) {
				// Extraemos todos los path de las carpetas de resources
				// del XML.
				return elemento.getChild("server_address").getValue();
			}
		}
		return "";
	}
	
	/**
	 * Método para obtener la ip del servidor para establecer un chat entre los jugadores.
	 * 
	 * @return Devuelve la ip con el cual se conectará la aplicación cliente.
	 */
	public static String getIpServidorMessenger() {
		Element raiz = getRaizXML();
		// obtenemos las configuraciones en una lista y las pasamos a un
		// iterator
		// para recorrerlas
		List<Element> configuraciones = raiz.getChildren();
		Iterator<Element> itConfiguraciones = configuraciones.iterator();

		while (itConfiguraciones.hasNext()) {

			Element elemento = (Element) itConfiguraciones.next();

			if (elemento.getName().equals("socketMessenger")) {
				// Extraemos todos los path de las carpetas de resources
				// del XML.
				return elemento.getChild("server_address").getValue();
			}
		}
		return "";
	}

	/**
	 * Método para obtener el puerto de conexión entre la aplicación cliente y
	 * el servidor.
	 * 
	 * @return Devuelve el puerto de conexión
	 */
	public static int getPuertoDeConexion() {

		Element raiz = getRaizXML();

		// obtenemos las configuraciones en una lista y las pasamos a un
		// iterator
		// para recorrerlas
		List<Element> configuraciones = raiz.getChildren();
		Iterator<Element> itConfiguraciones = configuraciones.iterator();

		while (itConfiguraciones.hasNext()) {

			Element elemento = (Element) itConfiguraciones.next();

			if (elemento.getName().equals("socket")) {
				// Extraemos todos los path de las carpetas de resources del
				// XML.
				return Integer.parseInt(elemento.getChild("port").getValue());
			}
		}

		return 0;
	}
	
	/**
	 * Método para obtener el puerto de conexión entre la aplicación cliente y
	 * el servidor.
	 * 
	 * @return Devuelve el puerto de conexión
	 */
	public static int getPuertoDeConexionMessenger() {

		Element raiz = getRaizXML();

		// obtenemos las configuraciones en una lista y las pasamos a un
		// iterator
		// para recorrerlas
		List<Element> configuraciones = raiz.getChildren();
		Iterator<Element> itConfiguraciones = configuraciones.iterator();

		while (itConfiguraciones.hasNext()) {

			Element elemento = (Element) itConfiguraciones.next();

			if (elemento.getName().equals("socketMessenger")) {
				// Extraemos todos los path de las carpetas de resources del
				// XML.
				return Integer.parseInt(elemento.getChild("port").getValue());
			}
		}

		return 0;
	}

	/**
	 * Metodo para obtener el nodo raiz del XML.
	 * @return
	 */
	private static Element getRaizXML() {
		try {
			// Ruta donde esta alojado el xml.
			URL xmlFile = LectorXML.class.getClassLoader().getResource(
					"appConfig.xml");

			// Utilizamos el parser XML Xerces
			SAXBuilder constructor = new SAXBuilder();
			// Construimos el documento con el arbol XML a partir del archivo
			// XML
			// pasado como argumento
			Document doc = constructor.build(xmlFile);
			// Comprobamos que el archivo sea del tipo departamentos
			// leyendo el elemento raiz
			Element raiz = doc.getRootElement();

			if (raiz.getName().equals("configuration"))
				return raiz;

		} catch (Exception ex) {
			GestorLogs.registrarLog("ERROR: " + ex.getMessage());
		}
		return null;
	}
}
