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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import jfx.messagebox.MessageBox;
import monopoly.controller.FichasController;
import monopoly.model.Ficha;
import monopoly.model.Juego;
import monopoly.model.Jugador;
import monopoly.model.JugadorHumano;
import monopoly.model.JugadorVirtual;
import monopoly.model.JugadorVirtual.TipoJugador;

/**
 * @author Bostico Alejandro
 * @author Moreno Pablo
 *
 */
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

	private int indexPlayer;

	private int indexPlayerVirtual;

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
		indexPlayer = 0;
		indexPlayerVirtual = 0;

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
		Ficha ficha = fichaList.get(indexPlayer);
		Jugador playerOwner = new JugadorHumano(nombre, ficha, juego,
				juego.getOwner());
		juego.addJugador(playerOwner);
		int cantSldJugadores = !txtNroJugadores.getText().isEmpty() ? Integer
				.parseInt(txtNroJugadores.getText()) : 0;
		juego.setCantJugadores(1 + cantSldJugadores
				+ jugadoresVirtualesList.size());
		for (VirtualPlayer j : jugadoresVirtualesList) {
			juego.addJugador(new JugadorVirtual(j.name.get().getNombre(),
					j.nameFicha.get(), juego, j.nameTipo.get()));
		}
		
	}

	public static CrearJugadoresController getInstance() {
		return instance;
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
	 * @return the juego
	 */
	public Juego getJuego() {
		return juego;
	}

	/**
	 * @param juego
	 *            the juego to set
	 */
	public void setJuego(Juego juego) {
		this.juego = juego;
	}

	public void inicializarVariables() {

		if (juego != null) {
			fichaList = FichasController.getFichas();
			juego.setFichasPlayerList(fichaList);

			vIndexFichas = new int[fichaList.size()];
			int i = 0;
			for (Ficha ficha : fichaList) {
				vIndexFichas[i] = ficha.getIdFicha();
				i++;
			}
			indexPlayer = 0;
			indexPlayerVirtual = 0;

			Ficha fichaPlayer = fichaList.get(indexPlayer);
			Ficha fichaPlayerVirtual = fichaList.get(indexPlayerVirtual);

			fichaPlayer.setSelected(true);
			fichaPlayerVirtual.setSelected(true);

			Image img = new Image(
					CrearJugadoresController.class.getResourceAsStream(fichaPlayer
							.getPathImgBig()), 90, 87, true, true);
			imgFicha.setImage(img);
			imgFicha.setId("ficha_" + fichaPlayer.getIdFicha());

			imgFichaPc.setImage(img);
			imgFicha.setId("ficha_" + fichaPlayerVirtual.getIdFicha());
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
				System.out.println("processNextFichaPlayer");
				if (indexPlayer < vIndexFichas.length) {
					Ficha fichaPlayer = fichaList.get(indexPlayer);
					fichaPlayer.setSelected(false);

					indexPlayer++;
					fichaPlayer = fichaList.get(indexPlayer);
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
				System.out.println("processBackFichaPlayer");
				if (indexPlayer != 0) {
					Ficha fichaPlayer = fichaList.get(indexPlayer);
					fichaPlayer.setSelected(false);

					indexPlayer--;
					fichaPlayer = fichaList.get(indexPlayer);
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
				System.out.println("processNextFichaPlayerVirtual");
				for (int i = indexPlayerVirtual + 1; i < vIndexFichas.length; i++) {
					Ficha fichaPlayerVirtual = fichaList.get(i);
					if (!fichaPlayerVirtual.isSelected()) {
						indexPlayerVirtual = i;
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
				System.out.println("processBackFichaPlayerVirtual");
				for (int i = indexPlayerVirtual - 1; i >= 0; i--) {
					Ficha fichaPlayerVirtual = fichaList.get(i);
					if (!fichaPlayerVirtual.isSelected()) {
						indexPlayerVirtual = i;
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

						Ficha fichaSelected = fichaList.get(indexPlayerVirtual);
						if (!fichaSelected.isSelected()) {
							Jugador jv = new JugadorVirtual(txtNombreVirtual
									.getText(), fichaSelected, juego,
									tipoJugador);
							VirtualPlayer vp = new VirtualPlayer(jv);
							jugadoresVirtualesList.add(vp);
							fichaSelected.setSelected(true);
							ajustarSlider(5 - jugadoresVirtualesList.size());
						} else {
							MessageBox.show(
									currentStage,
									"¡La Ficha "
											+ fichaSelected.getNombre()
											+ " ya está seleccionada, por favor seleccione otra!",
									"Advertencia", MessageBox.ICON_WARNING
											| MessageBox.OK);
						}

						// Columna Jugador
						TableColumn<VirtualPlayer, Jugador> colNombreJugador = new TableColumn<>(
								"Jugador");
						colNombreJugador.setMinWidth(100);
						colNombreJugador
								.setCellValueFactory(new PropertyValueFactory<VirtualPlayer, Jugador>(
										"name"));

						// Columna Ficha
						TableColumn<VirtualPlayer, Ficha> colFicha = new TableColumn<>(
								"Ficha");
						colFicha.setMinWidth(100);
						colFicha.setCellValueFactory(new PropertyValueFactory<VirtualPlayer, Ficha>(
								"nameFicha"));

						// Columna Tipo Jugador
						TableColumn<VirtualPlayer, TipoJugador> colTipoJugador = new TableColumn<>(
								"Tipo Jugador");
						colTipoJugador.setMinWidth(150);
						colTipoJugador
								.setCellValueFactory(new PropertyValueFactory<VirtualPlayer, TipoJugador>(
										"nameTipo"));

						// Columna Acciones
						TableColumn<VirtualPlayer, VirtualPlayer> colAction = new TableColumn<>(
								"Acción");
						colAction.setMinWidth(100);
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
											public void updateItem(
													final VirtualPlayer player,
													boolean empty) {
												super.updateItem(player, empty);
												if (player != null) {
													VBox vb = new VBox();
													vb.setAlignment(Pos.CENTER);
													ImageView img = new ImageView(
															new Image(
																	CrearJugadoresController.class
																			.getResourceAsStream("/images/iconos/delete.png"),
																	18, 18,
																	true, true));
													Tooltip t = new Tooltip(
															"Eliminar Jugador Virtual");
													Tooltip.install(img, t);
													vb.getChildren().add(img);
													img.setCursor(Cursor.HAND);

													// Agregar jugador virtual a
													// la tabla
													img.setOnMouseClicked(new EventHandler<MouseEvent>() {
														@Override
														public void handle(
																MouseEvent e) {
															int response = MessageBox
																	.show(currentStage,
																			"Elimnar Jugador Virtual",
																			"¿Está seguro que desea eliminar este jugador virtual?",
																			MessageBox.ICON_QUESTION
																					| MessageBox.YES
																					| MessageBox.NO
																					| MessageBox.CANCEL);

															if (response == MessageBox.YES) {
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
																player.nameFicha
																		.get()
																		.setSelected(
																				false);
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

						oListJugadoresVirtuales = FXCollections
								.observableArrayList(jugadoresVirtualesList);
						tblJugadoresVirtuales.setItems(oListJugadoresVirtuales);
						tblJugadoresVirtuales.getColumns().setAll(
								colNombreJugador, colFicha, colTipoJugador,
								colAction);
					} else {
						MessageBox
								.show(currentStage,
										"¡El juego permite un máximo de 6 jugadores en total!",
										"Advertencia", MessageBox.ICON_WARNING
												| MessageBox.OK);
					}
				} else {
					MessageBox
							.show(currentStage,
									"¡El juego permite un máximo de 6 jugadores en total!",
									"Advertencia", MessageBox.ICON_WARNING
											| MessageBox.OK);
				}
			}
		});
	}

	private void ajustarSlider(int maxValue) {
		sldNroJugadores.setMax(maxValue);
	}

}
