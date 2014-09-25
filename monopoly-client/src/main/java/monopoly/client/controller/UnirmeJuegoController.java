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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class UnirmeJuegoController extends AnchorPane implements Initializable {

	@FXML
    private TableView<?> tblJuegos;

    @FXML
    private TextField txtUserName;

    @FXML
    private Button btnCrearJuego;

    @FXML
    private TableColumn<?, ?> collumnNombre;

    @FXML
    private TableColumn<?, ?> collumnFecha;

    @FXML
    private TextField txtNombreJuego;

    @FXML
    private Button btnCancelar;

    @FXML
    private TableColumn<?, ?> collumnParticipantes;

    @FXML
    private TextField txtFechaHasta;

    @FXML
    private TableColumn<?, ?> collumnCreador;

    @FXML
    private TextField txtFechaDesde;

    @FXML
    private Button btnBuscar;

    @FXML
    void processCreateGame(ActionEvent event) {

    }

    @FXML
    void processCancel(ActionEvent event) {

    }

    @FXML
    void processSearch(ActionEvent event) {

    }

    
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
