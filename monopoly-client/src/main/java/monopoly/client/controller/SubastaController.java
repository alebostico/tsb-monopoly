/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import monopoly.model.tarjetas.TarjetaPropiedad;
import monopoly.util.StringUtils;
import monopoly.util.constantes.EnumEstadoSubasta;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
public class SubastaController extends AnchorPane implements Initializable {

	@FXML
	private ImageView imgPropiedad;

	@FXML
	private TableView<SubastaHistory> tblSubasta;

	@FXML
	private TableColumn<SubastaHistory, String> columnUsuario;

	@FXML
	private TableColumn<SubastaHistory, String> columnHistorico;

	@FXML
	private TextField txtMejorOferta;

	@FXML
	private TextField txtMiOferta;

	@FXML
	private Button btnPujar;

	@FXML
	private Button btnAbandonarSubasta;

	@FXML
	private Label lblMessage;

	private TarjetaPropiedad tarjetaSubasta;

	private EnumEstadoSubasta estadoSubasta;

	private static SubastaController instance;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		estadoSubasta = EnumEstadoSubasta.CREADA;
		if (txtMiOferta != null) {
			txtMiOferta.lengthProperty().addListener(
					new ChangeListener<Number>() {

						@Override
						public void changed(
								ObservableValue<? extends Number> observable,
								Number oldValue, Number newValue) {

							if (newValue.intValue() > oldValue.intValue()) {
								char ch = txtMiOferta.getText().charAt(
										oldValue.intValue());
								System.out.println("Length:" + oldValue + "  "
										+ newValue + " " + ch);

								// Check if the new character is the number or
								// other's
								if (!(ch >= '0' && ch <= '9')) {

									// if it's not number then just setText to
									// previous one
									txtMiOferta.setText(txtMiOferta.getText()
											.substring(
													0,
													txtMiOferta.getText()
															.length() - 1));
								}
							}
						}

					});
		}		
	}

	private void pujarSubasta() {
		int cantidadOfertada = 0;
		if (estadoSubasta == EnumEstadoSubasta.CREADA) {
			if (!StringUtils.IsNullOrEmpty(txtMiOferta.getText())) {
				lblMessage.setText("");
				cantidadOfertada = Integer.parseInt(txtMiOferta.getText());
				if (cantidadOfertada > 0) {
					// TODO: enviar mensaje para empezar la subasta.
				} else
					lblMessage.setText("la oferta debería ser mayor a 0.");
			} else
				lblMessage.setText("Campo Mi Oferta Obligatorio.");
		} else {

		}
	}

	private void abandonarSubasta() {

	}

	@FXML
	void processPujar(ActionEvent event) {
		try 
		{
			pujarSubasta();
			
		} catch (NumberFormatException nfe) {
			lblMessage.setText("La oferta debe ser un valor númerico.");
		} catch (Exception ex) {
			lblMessage.setText(ex.getMessage());
		}
	}

	@FXML
	void processDejarSubasta(ActionEvent event) {

		try {
			abandonarSubasta();
		} catch (Exception ex) {
			lblMessage.setText(ex.getMessage());
		}
	}

	public TarjetaPropiedad getTarjetaSubasta() {
		return tarjetaSubasta;
	}

	public void setTarjetaSubasta(TarjetaPropiedad tarjetaSubasta) {
		this.tarjetaSubasta = tarjetaSubasta;
	}

	public static SubastaController getInstance() {
		return instance;
	}

	public EnumEstadoSubasta getEstadoSubasta() {
		return estadoSubasta;
	}

	public void setEstadoSubasta(EnumEstadoSubasta estadoSubasta) {
		this.estadoSubasta = estadoSubasta;
	}

	public static class SubastaHistory {
		private final SimpleObjectProperty<String> usuario;
		private final SimpleObjectProperty<String> historia;

		public SubastaHistory(String usuario, String historia) {
			this.usuario = new SimpleObjectProperty<String>(usuario);
			this.historia = new SimpleObjectProperty<String>(historia);
		}

		public SimpleObjectProperty<String> getUsuario() {
			return usuario;
		}

		public SimpleObjectProperty<String> getHistoria() {
			return historia;
		}
	}

}