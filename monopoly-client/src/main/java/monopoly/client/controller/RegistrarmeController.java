/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.FXUtils;
import monopoly.model.Usuario;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.encriptacion.Encrypter;
import monopoly.util.encriptacion.VernamEncrypter;
import monopoly.util.exception.CampoVacioException;
import monopoly.util.exception.EmailInvalidoException;

/**
 * @author pablo
 *
 */
public class RegistrarmeController extends AnchorPane implements Initializable {

	@FXML
	private Label lblMsgError;

	@FXML
	private PasswordField txtRepeatPassword;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtUserName;

	@FXML
	private TextField txtEmail;

	@FXML
	private Button btnGuardar;

	@FXML
	private Button btnCancelar;

	@FXML
	public Stage prevStage;

	@FXML
	private Stage currentStage;

	private static RegistrarmeController instance = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		txtRepeatPassword.focusedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(
							ObservableValue<? extends Boolean> observable,
							Boolean oldValue, Boolean newValue) {
						if (!newValue.booleanValue())
							if (!txtPassword.getText().equals(
									txtRepeatPassword.getText())) {
								lblMsgError.setVisible(true);
								txtRepeatPassword.focusedProperty();
							} else {
								lblMsgError.setVisible(false);
							}
					}
				});
	}

	@FXML
	void processCancel(ActionEvent event) {
		prevStage.show();
		currentStage.close();
	}

	@FXML
	void processSave(ActionEvent event) {
		String nombre = "";
		String userName = "";
		String passwordEnc = "";
		String email = "";
		Usuario usuario = null;

		try {
			if (validarCampos()) {
				Encrypter enc = new VernamEncrypter(txtPassword.getText());
				enc.code();
				passwordEnc = enc.getEncrypted();
				nombre = txtNombre.getText();
				userName = txtUserName.getText();
				email = txtEmail.getText();

				usuario = new Usuario(userName, passwordEnc);
				usuario.setEmail(email);

				usuario.setNombre(nombre);

				ConnectionController.getInstance().iniciarConexionToAccount(
						usuario);
			}
		} catch (EmailInvalidoException eie) {
			lblMsgError.setText(eie.getMessage());
		} catch (CampoVacioException cve) {
			lblMsgError.setText(cve.getMessage());
		} catch(Exception ex){
			lblMsgError.setText(ex.getMessage());
		}
	}

	public void iniciarSesion(final Usuario user) throws Exception{
		FutureTask<Void> taskMostrarOpciones = null;

		taskMostrarOpciones = new FutureTask<Void>(new Callable<Void>() {

			private Stage stage = null;

			@Override
			public Void call() throws Exception {

				MenuOpcionesController controller = null;
				String fxml = "";

				if (user != null) {

					GestorLogs.registrarLog("Desplegar Menú de Opciones desde registrar..");

					fxml = ConstantesFXML.FXML_MENU_OPCIONES;
					stage = new Stage();
					controller = (MenuOpcionesController) FXUtils.cargarStage(
							stage, fxml, "Monopoly - Menú de Opciones", false,
							false, Modality.APPLICATION_MODAL,
							StageStyle.DECORATED);
					controller.setCurrentStage(stage);
					controller.setUsuarioLogueado(user);
					prevStage.close();
					controller.getCurrentStage().showAndWait();

				} else {
					lblMsgError.setText("Usuario produjo un error.");
				}
				return null;
			}
		});
		Platform.runLater(taskMostrarOpciones);
	}

	private boolean validarCampos() throws CampoVacioException {
		lblMsgError.setText("");
		if (txtNombre.getText().isEmpty()) {
			txtNombre.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Nombre no puede estar vacio!");
		}

		if (txtUserName.getText().isEmpty()) {
			txtUserName.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Usuario no puede estar vacio!");
		}

		if (txtPassword.getText().isEmpty()) {
			txtPassword.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Contraseña no puede estar vacio!");
		}

		if (txtRepeatPassword.getText().isEmpty()) {
			txtRepeatPassword.requestFocus();
			throw new CampoVacioException(
					"¡El Campo Confirmar Contraseña no puede estar vacio!");
		}
		return true;
	}

	public static RegistrarmeController getInstance() {
		if (instance == null)
			instance = new RegistrarmeController();
		return instance;
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

	/**
	 * @return the currentStage
	 */
	public Stage getCurrentStage() {
		return currentStage;
	}

	/**
	 * @param currentStage
	 *            the currentStage to set
	 */
	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

}
