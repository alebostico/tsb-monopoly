/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author pablo
 *
 */
public class OptionMenuController extends AnchorPane implements Initializable {

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

	public void processNewGame(ActionEvent event) {

	}

	public void processLoadGame(ActionEvent event) {

	}

	public void processOptions(ActionEvent event) {

	}

	public void processHelp(ActionEvent event) {

	}
	
	public void processExit(ActionEvent event) {

	}
	
	public void processAbout(ActionEvent event) {

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
