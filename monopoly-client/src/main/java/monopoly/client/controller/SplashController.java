/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SplashController extends AnchorPane implements Initializable {

	@FXML
	private Label lblMensaje;
	
	private AnchorPane controller;
	
	private Stage currentStage;
	
	private static SplashController instance;
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		instance = this;
	}
	/**
	 * @return the instance
	 */
	public static SplashController getInstance() {
		if(instance == null)
			instance = new SplashController();
		return instance;
	}
	/**
	 * @return the controller
	 */
	public AnchorPane getController() {
		return controller;
	}
	/**
	 * @param controller the controller to set
	 */
	public void setController(AnchorPane controller) {
		this.controller = controller;
	}
	
	/**
	 * @return the currentStage
	 */
	public Stage getCurrentStage() {
		return currentStage;
	}
	/**
	 * @param currentStage the currentStage to set
	 */
	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}
	/**
	 * @return the lblMensaje
	 */
	public String getLblMensaje() {
		return lblMensaje.getText();
	}
	/**
	 * @param lblMensaje the lblMensaje to set
	 */
	public void setLblMensaje(String lblMensaje) {
		this.lblMensaje.setText(lblMensaje);
	}
	
}
