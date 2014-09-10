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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author pablo
 *
 */
public class MenuOpcionesController extends AnchorPane implements Initializable {

	@FXML
    private Button btnSalir;

    @FXML
    private Button btnAcercaDe;

    @FXML
    private Button btnUnirmeJuego;

    @FXML
    private Button btnReanudarJuego;

    @FXML
    private Button btnAyuda;

    @FXML
    private Button btnNuevoJuego;

    @FXML
    private Button btnOpciones;
    
	private Stage prevStage;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

	@FXML
    void processNewGame(ActionEvent event) {

    }

    @FXML
    void processLoadGame(ActionEvent event) {

    }

    @FXML
    void processOptions(ActionEvent event) {

    }

    @FXML
    void processHelp(ActionEvent event) {

    }

    @FXML
    void processExit(ActionEvent event) {

    }

    @FXML
    void processAbout(ActionEvent event) {

    }

    @FXML
    void processJoinGame(ActionEvent event) {

    }

	/**
	 * @return the prevStage
	 */
	public Stage getPrevStage() {
		return prevStage;
	}

	/**
	 * @param prevStage
	 *            the prevStage to set
	 */
	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}

}
