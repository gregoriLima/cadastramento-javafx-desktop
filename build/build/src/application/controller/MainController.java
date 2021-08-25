package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

import application.widgets.DialogUtil;
import application.Main;
import application.enumeration.Scenes;
import application.interfaces.OnChangeServerStatus;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class MainController {

	private boolean serverStatus;

	@FXML
	private Pane pnlServerStatus;

	@FXML
	private Label statusLabel;

	@FXML
	private Button incluirButton;

	@FXML
	private Button alterarButton;

	@FXML
	private Button excluirButton;

	@FXML
	private Button consultarButton;

	@FXML
	private Button listarButton;

	@FXML
	private Button sairButton;

	@FXML
	private void incluirButtonOnAction(ActionEvent event) {
		Main.alterScene(Scenes.INCLUDE);
	}

	@FXML
	private void alterarButtonOnAction(ActionEvent event) {
		Main.alterScene(Scenes.ALTER);
	}

	@FXML
	private void excluirButtonOnAction(ActionEvent event) {
		Main.alterScene(Scenes.EXCLUDE);
	}

	@FXML
	private void consultarButtonOnAction(ActionEvent event) {
		Main.alterScene(Scenes.CONSULT);
	}

	@FXML
	private void listarButtonOnAction(ActionEvent event) {
		Main.alterScene(Scenes.LIST);
	}

	@FXML
	private void sairButtonOnAction(ActionEvent event) {

		DialogUtil.buildSimpleDialog("Agradecimento", null, "Obrigado por utilizar o software de cadastramento!",
				AlertType.INFORMATION).showAndWait();

		Stage stage = (Stage) sairButton.getScene().getWindow();

		PauseTransition delay = new PauseTransition(Duration.seconds(0.35));
		delay.setOnFinished(e -> stage.close());
		delay.play();

	}

	public void initialize() {
		Main.addOnChangeServerStatusListener(new OnChangeServerStatus() {

			@Override
			public void onChangeServerStatus(boolean serverStatus) {

				if (serverStatus) {
					statusLabel.setText("Conectado ao servidor.");
					pnlServerStatus.getStyleClass().remove("status-bar-disconnected");
					if (!(pnlServerStatus.getStyleClass().contains("status-bar-connected")))
						pnlServerStatus.getStyleClass().add("status-bar-connected");
				} else {
					statusLabel.setText("Servidor desconectado!");
					pnlServerStatus.getStyleClass().remove("status-bar-connected");
					if (!(pnlServerStatus.getStyleClass().contains("status-bar-disconnected")))
						pnlServerStatus.getStyleClass().add("status-bar-disconnected");
				}

			}

			@Override
			public void onChangeEstadosStatus(ObservableList<String> estados) {
				// TODO Auto-generated method stub

			}
		});
	}
}
