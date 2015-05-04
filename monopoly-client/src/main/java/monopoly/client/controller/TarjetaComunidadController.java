/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class TarjetaComunidadController extends AnchorPane implements
		Initializable {

	@FXML
    private Label lblMensaje;

    @FXML
    private Button btnAceptar;
    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}
	
	@FXML
    private void processAceptar(ActionEvent event) {

    }

}
