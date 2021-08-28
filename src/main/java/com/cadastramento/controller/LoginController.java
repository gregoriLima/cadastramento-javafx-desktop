package com.cadastramento.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.cadastramento.model.Usuario;
import com.cadastramento.widgets.DialogUtil;
import com.cadastramento.widgets.FormUtil;
import com.cadastramento.widgets.TextFieldValidator;
import com.cadastramento.Main;
import com.cadastramento.client.RESTClient;
import com.cadastramento.enumeration.Scenes;
import com.cadastramento.interfaces.OnChangeServerStatus;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class LoginController {

	@FXML
	private Button cancelButton;
	@FXML
	private Label loginMessageLabel;
	@FXML
	private TextField userNameField;
	@FXML
	private PasswordField userPasswordField;
	@FXML
	private Label statusLabel;
	@FXML
	private Pane pnlServerStatus;

	@FXML
	private void loginButtonOnAction(ActionEvent event) throws InterruptedException {

		if (FormUtil.isValid(userNameField, userPasswordField)) {

			Usuario usuario = new Usuario(userNameField.getText(), userPasswordField.getText());

			if (RESTClient.autentica(usuario)) {

				loginMessageLabel.setTextFill(Color.color(0.1, 1, 0.1));
				loginMessageLabel.setText("Usuario autenticado!");

				PauseTransition delay = new PauseTransition(Duration.seconds(1.4));
				delay.setOnFinished(e -> Main.alterScene(Scenes.MAIN));
				delay.play();

			} else {
				loginMessageLabel.setText("Usuario não cadastrado!");
			}

		} else {
			loginMessageLabel.setText("Dados de login incorretos!");
		}

	}

	@FXML
	private void cancelButtonOnAction(ActionEvent event) {

		DialogUtil.buildSimpleDialog("Agradecimento", null, "Obrigado por utilizar o software de cadastramento!",
				AlertType.INFORMATION).showAndWait();

		Stage stage = (Stage) cancelButton.getScene().getWindow();

		PauseTransition delay = new PauseTransition(Duration.seconds(0.35));
		delay.setOnFinished(e -> stage.close());
		delay.play();

	}

	public void initialize() {

		TextFieldValidator.addListener(userNameField,
				"(?:[a-z0-9!#$%&'*+=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

		TextFieldValidator.addListener(userPasswordField, "[0-9a-zA-Z]{6,}");

		Main.addOnChangeServerStatusListener(new OnChangeServerStatus() {

			@Override
			public void onChangeServerStatus(boolean serverStatus) {

				pnlServerStatus.getStyleClass().remove("status-bar-connecting");

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
