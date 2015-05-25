/**
 * 
 */
package monopoly.client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import monopoly.client.connection.ConnectionController;
import monopoly.client.util.ScreensFramework;
import monopoly.model.Ficha;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.JugadorVirtual;
import monopoly.model.JugadorVirtual.TipoJugador;
import monopoly.util.GestorLogs;
import monopoly.util.constantes.ConstantesFXML;
import monopoly.util.message.game.LoadGameMessage;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
@SuppressWarnings("deprecation")
public class CrearJugadoresController extends AnchorPane implements
		Initializable {

	private static CrearJugadoresController instance;

	@FXML
	private TextField txtNombreVirtual;

	@FXML
	private RadioButton rbEmpresario;

	@FXML
	private ToggleGroup TipoJugador;

	@FXML
	private ImageView imgBackPc;

	@FXML
	private ImageView imgOk;

	@FXML
	private ImageView imgFicha;

	@FXML
	private ImageView imgNext;

	@FXML
	private ImageView imgFichaPc;

	@FXML
	private Button btnRestablecerReglas;

	@FXML
	private ImageView imgBack;

	@FXML
	private RadioButton rbCompradorPrimerizo;

	@FXML
	private TableView<VirtualPlayer> tblJugadoresVirtuales;

	@FXML
	TableColumn<VirtualPlayer, Jugador> colNombreJugador;

	@FXML
	private TableColumn<VirtualPlayer, Ficha> colFicha;

	@FXML
	private TableColumn<VirtualPlayer, TipoJugador> colTipoJugador;

	@FXML
	private TableColumn<VirtualPlayer, VirtualPlayer> colAction;

	@FXML
	private TextField txtNroJugadores;

	@FXML
	private Button btnModificarReglas;

	@FXML
	private RadioButton rbMagnate;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnEmpezarJuego;

	@FXML
	private ImageView imgNextPc;

	@FXML
	private Slider sldNroJugadores;

	@FXML
	private Stage currentStage;

	@FXML
	private Stage prevStage;

	private Juego juego;

	private List<Ficha> fichaList;

	private int[] vIndexFichas;

	private Ficha fichaPlayer;

	private Ficha fichaPlayerVirtual;

	private List<VirtualPlayer> jugadoresVirtualesList;

	private ObservableList<VirtualPlayer> oListJugadoresVirtuales;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javafx.fxml.Initializable#initialize(java.net.URL,
	 * java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		instance = this;

		jugadoresVirtualesList = new ArrayList<VirtualPlayer>();

		Tooltip t = new Tooltip("Crear Jugador Virtual");
		Tooltip.install(imgOk, t);

		settearActions();

	}

	@FXML
	void processCancel(ActionEvent event) {
		prevStage.show();
		currentStage.close();
	}

	@FXML
	void processRestoreRules(ActionEvent event) {

	}

	@FXML
	void processModificateRules(ActionEvent event) {

	}

	@FXML
	void processStartGame(ActionEvent event) {
		String nombre = juego.getOwner().getUserName();
		Jugador playerOwner = new JugadorHumano(nombre, fichaPlayer, juego, null,
				juego.getOwner(), ConnectionController.getInstance().getIdPlayer());
		juego.addJugador(playerOwner);
		int cantSldJugadores = !txtNroJugadores.getText().isEmpty() ? Integer
				.parseInt(txtNroJugadores.getText()) : 0;

		if (cantSldJugadores > 0 || jugadoresVirtualesList.size() > 0) {
			juego.setCantJugadores(1 + cantSldJugadores
					+ jugadoresVirtualesList.size());
			for (VirtualPlayer j : jugadoresVirtualesList) {
				juego.addJugador(new JugadorVirtual(j.name.get().getNombre(),
						j.nameFicha.get(), juego, null,j.nameTipo.get()));
			}

			String fxml = ConstantesFXML.FXML_MOSTRAR_TABLERO;

			try {
				Parent root;
				Stage stage = new Stage();
				FXMLLoader loader = ScreensFramework.getLoader(fxml);

				root = (Parent) loader.load();
				TableroController controller = (TableroController) loader
						.getController();
				controller.setPrevStage(currentStage);
				controller.setJuego(juego);
				controller.setUsuarioLogueado(juego.getOwner());

				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Monopoly - Tablero");
				stage.centerOnScreen();
				controller.setCurrentStage(stage);
				controller.showTableroDeJuego();
				
				int senderId = ConnectionController.getInstance().getIdPlayer();
				ConnectionController.getInstance().send(
						new LoadGameMessage(senderId, juego));

			} catch (Exception ex) {
				// TODO Auto-generated catch block
				GestorLogs.registrarError(ex.getMessage());
			}
		} else {
			Dialogs.create().owner(currentStage).title("Advertencia")
			.masthead("Límites de jugadores").message("¡El juego permite un mínimo de 2 jugadores en total!")
			.showWarning();
			return;
		}
	}

	public static CrearJugadoresController getInstance() {
		if (instance == null)
			instance = new CrearJugadoresController();
		return instance;
	}

	public Stage getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(Stage currentStage) {
		this.currentStage = currentStage;
	}

	public Stage getPrevStage() {
		return prevStage;
	}

	public void setPrevStage(Stage prevStage) {
		this.prevStage = prevStage;
	}
	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public void inicializarVariables() {

		if (juego != null) {
			fichaList = juego.getFichasPlayerList();

			vIndexFichas = new int[fichaList.size()];
			int i = 0;
			for (Ficha ficha : fichaList) {
				vIndexFichas[i] = ficha.getIdFicha();
				i++;
			}

			fichaPlayer = fichaList.get(0);
			fichaPlayerVirtual = fichaList.get(0);

			fichaPlayer.setSelected(true);

			Image img = new Image(
					CrearJugadoresController.class.getResourceAsStream(fichaPlayer
							.getPathImgBig()), 90, 87, true, true);
			imgFicha.setImage(img);
			imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());

			imgFichaPc.setImage(img);
			imgFichaPc.setId("ficha_" + fichaPlayerVirtual.getIdFicha());
			txtNombreVirtual.setText(fichaPlayerVirtual.getNombre());
		}
	}

	public static class VirtualPlayer {

		private final SimpleObjectProperty<Jugador> name;
		private final SimpleObjectProperty<Ficha> nameFicha;
		private final SimpleObjectProperty<TipoJugador> nameTipo;

		private VirtualPlayer(Jugador pJugador) {
			this.name = new SimpleObjectProperty<Jugador>(
					((JugadorVirtual) pJugador));
			this.nameFicha = new SimpleObjectProperty<Ficha>(
					((JugadorVirtual) pJugador).getFicha());
			this.nameTipo = new SimpleObjectProperty<TipoJugador>(
					((JugadorVirtual) pJugador).getTipoJugador());
		}

		/**
		 * @return the name
		 */
		public String getName() {
			return name.get().getNombre();
		}

		/**
		 * @return the nameFicha
		 */
		public String getNameFicha() {
			return nameFicha.get().getNombre();
		}

		/**
		 * @return the nameTipo
		 */
		public String getNameTipo() {
			return nameTipo.get().getNombreTipo();
		}
	}

	private void settearActions() {
		// Método para mostrar la siguiente imagen del usuario creador del juego
		imgNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int indexOf = fichaList.indexOf(fichaPlayer);

				if (indexOf < fichaList.size() - 1) {
					fichaPlayer.setSelected(false);
					fichaPlayer = fichaList.get(indexOf + 1);
					fichaPlayer.setSelected(true);

					Image img = new Image(CrearJugadoresController.class
							.getResourceAsStream(fichaPlayer.getPathImgBig()),
							90, 87, true, true);
					imgFicha.setImage(img);
					imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());
				} else
					return;
			}
		});

		// Método para mostrar la imagen anterior del usuario creador del juego
		imgBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int indexOf = fichaList.indexOf(fichaPlayer);

				if (indexOf > 0) {
					fichaPlayer.setSelected(false);
					fichaPlayer = fichaList.get(indexOf - 1);
					fichaPlayer.setSelected(true);

					Image img = new Image(CrearJugadoresController.class
							.getResourceAsStream(fichaPlayer.getPathImgBig()),
							90, 87, true, true);
					imgFicha.setImage(img);
					imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());
				} else
					return;
			}
		});

		// Método para mostrar la siguiente imagen del jugador virtual
		imgNextPc.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int indexOf = fichaList.indexOf(fichaPlayerVirtual);

				for (int i = indexOf + 1; i < fichaList.size(); i++) {
					if (!fichaList.get(i).isSelected()) {
						fichaPlayerVirtual = fichaList.get(i);
						Image img = new Image(CrearJugadoresController.class
								.getResourceAsStream(fichaPlayerVirtual
										.getPathImgBig()), 90, 87, true, true);
						imgFichaPc.setImage(img);
						imgFichaPc.setId("ficha_"
								+ fichaPlayerVirtual.getIdFicha());
						txtNombreVirtual.setText(fichaPlayerVirtual.getNombre());
						break;
					}
				}
			}
		});

		// Método para mostrar la imagen anterior del jugador virtual
		imgBackPc.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				int indexOf = fichaList.indexOf(fichaPlayerVirtual);

				for (int i = indexOf - 1; i >= 0; i--) {
					if (!fichaList.get(i).isSelected()) {
						fichaPlayerVirtual = fichaList.get(i);
						Image img = new Image(CrearJugadoresController.class
								.getResourceAsStream(fichaPlayerVirtual
										.getPathImgBig()), 90, 87, true, true);
						imgFichaPc.setImage(img);
						imgFichaPc.setId("ficha_"
								+ fichaPlayerVirtual.getIdFicha());
						txtNombreVirtual.setText(fichaPlayerVirtual.getNombre());
						break;
					}
				}
			}
		});

		sldNroJugadores.valueProperty().addListener(
				new ChangeListener<Number>() {
					public void changed(ObservableValue<? extends Number> ov,
							Number old_val, Number new_val) {
						txtNroJugadores.setText(String.valueOf(String.format(
								"%.0f", new_val)));
						sldNroJugadores.setTooltip(new Tooltip(String.format(
								"%.0f", sldNroJugadores.getValue())));
					}
				});

		imgOk.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@SuppressWarnings("unchecked")
			@Override
			public void handle(MouseEvent e) {
				System.out.println("processAddVirtualPlayer");
				int cantJugadoresSeleccionado = !txtNroJugadores.getText()
						.isEmpty() ? Integer.parseInt(txtNroJugadores.getText())
						: 0;

				if (cantJugadoresSeleccionado < 5) {
					if (!(cantJugadoresSeleccionado
							+ jugadoresVirtualesList.size() >= 5)) {

						monopoly.model.JugadorVirtual.TipoJugador tipoJugador = null;
						if (rbCompradorPrimerizo.isSelected())
							tipoJugador = monopoly.model.JugadorVirtual.TipoJugador.TJ_COMPRADOR_PRIMERIZO;
						else if (rbEmpresario.isSelected()) {
							tipoJugador = monopoly.model.JugadorVirtual.TipoJugador.TJ_EMPRESARIO;
						} else {
							tipoJugador = monopoly.model.JugadorVirtual.TipoJugador.TJ_MAGNATE;
						}

						if (!fichaPlayerVirtual.isSelected()) {
							Jugador jv = new JugadorVirtual(txtNombreVirtual
									.getText(), fichaPlayerVirtual, juego, null,
									tipoJugador);
							VirtualPlayer vp = new VirtualPlayer(jv);
							jugadoresVirtualesList.add(vp);
							fichaPlayerVirtual.setSelected(true);
							ajustarSlider(5 - jugadoresVirtualesList.size());
						} else {
							Dialogs.create().owner(currentStage).title("Advertencia")
							.masthead("Ficha Seleccionada")
							.message("¡La Ficha "
									+ fichaPlayerVirtual.getNombre()
									+ " ya está seleccionada, por favor seleccione otra!")
							.showWarning();
						}

						configurarTable();

						oListJugadoresVirtuales = FXCollections
								.observableArrayList(jugadoresVirtualesList);
						tblJugadoresVirtuales.setItems(oListJugadoresVirtuales);
						tblJugadoresVirtuales.getColumns().setAll(
								colNombreJugador, colFicha, colTipoJugador,
								colAction);
					} else {
						Dialogs.create().owner(currentStage).title("Advertencia")
						.masthead("Límites de jugadores")
						.message("¡El juego permite un máximo de 6 jugadores en total!")
						.showWarning();
						
					}
				} else {
					Dialogs.create().owner(currentStage).title("Advertencia")
					.masthead("Límites de jugadores")
					.message("¡El juego permite un máximo de 6 jugadores en total!")
					.showWarning();
				}
			}
		});
	}

	private void configurarTable() {
		// Columna Jugador
		colNombreJugador = new TableColumn<>("Jugador");
		colNombreJugador
				.setCellValueFactory(new PropertyValueFactory<VirtualPlayer, Jugador>(
						"name"));

		// Columna Ficha
		colFicha = new TableColumn<>("Ficha");
		colFicha.setCellValueFactory(new PropertyValueFactory<VirtualPlayer, Ficha>(
				"nameFicha"));

		// Columna Tipo Jugador
		colTipoJugador = new TableColumn<>("Tipo Jugador");
		colTipoJugador
				.setCellValueFactory(new PropertyValueFactory<VirtualPlayer, TipoJugador>(
						"nameTipo"));

		// Columna Acciones
		colAction = new TableColumn<>("Acción");
		colAction
				.setCellValueFactory(new Callback<CellDataFeatures<VirtualPlayer, VirtualPlayer>, ObservableValue<VirtualPlayer>>() {
					@Override
					public ObservableValue<VirtualPlayer> call(
							CellDataFeatures<VirtualPlayer, VirtualPlayer> features) {
						return new ReadOnlyObjectWrapper<VirtualPlayer>(
								features.getValue());
					}
				});

		colAction
				.setCellFactory(new Callback<TableColumn<VirtualPlayer, VirtualPlayer>, TableCell<VirtualPlayer, VirtualPlayer>>() {
					@Override
					public TableCell<VirtualPlayer, VirtualPlayer> call(
							TableColumn<VirtualPlayer, VirtualPlayer> collumnEliminar) {
						return new TableCell<VirtualPlayer, VirtualPlayer>() {
							@Override
							public void updateItem(final VirtualPlayer player,
									boolean empty) {
								super.updateItem(player, empty);
								if (player != null) {
									VBox vb = new VBox();
									vb.setAlignment(Pos.CENTER);
									ImageView img = new ImageView(
											new Image(
													CrearJugadoresController.class
															.getResourceAsStream("/images/iconos/delete.png"),
													18, 18, true, true));
									Tooltip t = new Tooltip(
											"Eliminar Jugador Virtual");
									Tooltip.install(img, t);
									vb.getChildren().add(img);
									img.setCursor(Cursor.HAND);

									// Agregar jugador virtual a
									// la tabla
									img.setOnMouseClicked(new EventHandler<MouseEvent>() {
										@Override
										public void handle(MouseEvent e) {
											Action response = Dialogs.create().owner(currentStage).title("Advertencia")
											.masthead("Elimnar Jugador Virtual")
											.message("¿Está seguro que desea eliminar este jugador virtual?")
											.showConfirm();

											if (response == Dialog.ACTION_YES) {
												// ... user
												// chose YES
												jugadoresVirtualesList
														.remove(player);
												oListJugadoresVirtuales = FXCollections
														.observableArrayList(jugadoresVirtualesList);
												tblJugadoresVirtuales
														.setItems(null);
												tblJugadoresVirtuales
														.setItems(oListJugadoresVirtuales);
												player.nameFicha.get()
														.setSelected(false);
												ajustarSlider(5 - jugadoresVirtualesList
														.size());

											}
										}
									});
									setGraphic(vb);
								} else {
									setGraphic(null);
								}
							}
						};
					}
				});
	}

	private void ajustarSlider(int maxValue) {
		sldNroJugadores.setMax(maxValue);
	}

}
