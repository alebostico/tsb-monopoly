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
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class VentaPropiedadController extends AnchorPane implements Initializable{
	@FXML
    private Label lblPrecioPropiedad;

    @FXML
    private ImageView imgTarjetaDetalle;

    @FXML
    private ImageView imgTarjetaPropiedad;

    @FXML
    private Button btnComprar;

    @FXML
    private Button btnSubasta;


    @FXML
    void processComprar(ActionEvent event) {

    }

    @FXML
    void processSubasta(ActionEvent event) {

    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
