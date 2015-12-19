/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import monopoly.model.tarjetas.TarjetaPropiedad;

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
    
    private TarjetaPropiedad tarjetaSubasta;
    
    private static SubastaController instance;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		instance = this;
		txtMiOferta.setFocusTraversable(true);
	}
	
	@FXML
    void processPujar(ActionEvent event) {

    }

    @FXML
    void processDejarSubasta(ActionEvent event) {

    }
	
	public TarjetaPropiedad getTarjetaSubasta() {
		return tarjetaSubasta;
	}

	public void setTarjetaSubasta(TarjetaPropiedad tarjetaSubasta) {
		this.tarjetaSubasta = tarjetaSubasta;
	}

	public static SubastaController getInstance(){
		return instance;
	}
	
	public static class SubastaHistory{
		private final SimpleObjectProperty<String> usuario;
		private final SimpleObjectProperty<String> historia;
		
		public SubastaHistory(String usuario, String historia){
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