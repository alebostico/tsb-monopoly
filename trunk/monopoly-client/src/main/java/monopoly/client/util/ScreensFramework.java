/**
 * 
 */
package monopoly.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import monopoly.client.gui.FXMLIniciarSesion;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author pablo
 *
 */
public class ScreensFramework {
	
	public static final String MAIN_SCREEN = "FXMLIniciarSesion";
    public static final String MAIN_SCREEN_FXML = "IniciarSesion.fxml";
    public static final String OPTIONS_SCREEN = "poker";
    public static final String OPTIONS_SCREEN_FXML =
                                         "poker.fxml";
    
    public static Initializable replaceSceneContent(String fxml, Stage stage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader(FXMLIniciarSesion.class.getResource(fxml), null, new JavaFXBuilderFactory());
		InputStream in = FXMLIniciarSesion.class.getResourceAsStream(fxml);
		
		Parent root;
		try {
			root = (Parent) fxmlLoader.load(in);
		} finally {
			in.close();
		}

		Scene scene = new Scene(root);
		stage.setScene(scene);

		return (Initializable) fxmlLoader.getController();
	}
    
    public static Object getFXMLLoader(String fxml) throws IOException
    {
        URL url = FXMLIniciarSesion.class.getResource(fxml);
    	FXMLLoader fxmlLoader = new FXMLLoader(url, null, new JavaFXBuilderFactory());
    	return fxmlLoader.load(url.openStream());
    }
    
    public static Parent getParent(String fxml) throws IOException
    {
        URL url = FXMLIniciarSesion.class.getResource(fxml);
    	FXMLLoader fxmlLoader = new FXMLLoader(url, null, new JavaFXBuilderFactory());
    	InputStream in = FXMLIniciarSesion.class.getResourceAsStream(fxml);
    	
    	Parent root;
		try {
			root = (Parent) fxmlLoader.load(in);
		} finally {
			in.close();
		}
		
    	return root;
    }
    
    public static Initializable getController(String fxml) throws IOException
    {
    	FXMLLoader fxmlLoader = new FXMLLoader(FXMLIniciarSesion.class.getResource(fxml), null, new JavaFXBuilderFactory());
    	InputStream in = FXMLIniciarSesion.class.getResourceAsStream(fxml);
    	try {
			fxmlLoader.load(in);
		} finally {
			in.close();
		}
    	return (Initializable) fxmlLoader.getController();
    }

}
