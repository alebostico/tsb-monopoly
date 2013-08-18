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
	 * Método para obtener todos los nodos del XML
	 * con las rutas de las carpetas de resources
	 * @return
	 */
	private static List<Element> getNodosFolder() {
		List<Element> folderList = new ArrayList<Element>();
		try {
			//Ruta donde esta alojado el xml.
			URL xmlFile = LectorXML.class. getClassLoader()
					.getResource("appConfig.xml");
			
			// Utilizamos el parser XML Xerces
			SAXBuilder constructor = new SAXBuilder();
			// Construimos el documento con el arbol XML a partir del archivo
			// XML
			// pasado como argumento
			Document doc = constructor.build(xmlFile);
			// Comprobamos que el archivo sea del tipo departamentos
			// leyendo el elemento raiz
			Element raiz = doc.getRootElement();
			
			if (raiz.getName().equals("configuration")) {
				// obtenemos las configuraciones en una lista y las pasamos a un iterator
				// para recorrerlas
				List<Element> configuraciones = raiz.getChildren();
				Iterator<Element> itConfiguraciones = configuraciones.iterator();
				
				while (itConfiguraciones.hasNext()) {
					
					Element elementos = (Element) itConfiguraciones.next();
					
					if(elementos.getName().equals("path_folders")){
						// Extraemos todos los path de las carpetas de resources del XML.
						folderList.addAll(elementos.getChildren());
						return folderList;
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return folderList;
	}
	
	public static String getPathFichas(){
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if(folderElement.getAttributeValue("id").equals("fichas")){
				path = folderElement.getChild("path").getValue();
			}
		}
		return path;
	}
	
	public static String getPathTablero(){
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if(folderElement.getAttributeValue("id").equals("tablero")){
				path = folderElement.getChild("path").getValue();
			}
		}
		return path;
	}
	
	public static String getPathTarjetas(){
		String path = "";
		for (Element folderElement : getNodosFolder()) {
			if(folderElement.getAttributeValue("id").equals("tarjetas")){
				path = folderElement.getChild("path").getValue();
			}
		}
		return path;
	}
}
