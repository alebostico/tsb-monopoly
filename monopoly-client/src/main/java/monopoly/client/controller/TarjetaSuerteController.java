/**
 * 
 */
package monopoly.client.controller;

import java.io.Serializable;
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
public class TarjetaSuerteController extends AnchorPane implements
		Serializable, Initializable {

	private static final long serialVersionUID = -2714033349851758167L;

	@FXML
    private Label lblMensaje;

    @FXML
    private Button btnAceptar;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@FXML
    void processAceptar(ActionEvent event) {

    }

}
