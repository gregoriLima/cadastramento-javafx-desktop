package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import application.model.PessoaFisica;
import application.widgets.FormUtil;
import application.widgets.MaskFormatter;
import application.widgets.TextFieldValidator;
import application.Main;
import application.client.RESTClient;
import application.enumeration.Scenes;
import application.interfaces.OnChangeServerStatus;
import javafx.animation.PauseTransition;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;

import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;

public class IncludeController {

	@FXML
	private Pane pnlServerStatus;

	@FXML
	private Pane pnlStatus;

	@FXML
	private Label statusLabel;

	@FXML
	private Button voltarButton;

	@FXML
	private Button salvarButton;

	@FXML
	private Label incluirStatusLabel;

	@FXML
	private TextField nomeTextField;

	@FXML
	private TextField cpfTextField;

	@FXML
	private TextField cidadeTextField;

	@FXML
	private ChoiceBox<String> estadoChoiceBox;

	@FXML
	void salvarButtonOnAction(ActionEvent event) {

		FormUtil.changeStatus(pnlStatus, "");

		if (FormUtil.isValid(nomeTextField, cpfTextField, cidadeTextField, estadoChoiceBox)) {

			incluirStatusLabel.setText("Salvando pessoa fÌsica . . . ");

			FormUtil.changeStatus(pnlStatus, "ok");

			PessoaFisica pf = new PessoaFisica();
			pf.setNome(nomeTextField.getText());
			pf.setCpf(cpfTextField.getText());
			pf.setCidade(cidadeTextField.getText());
			pf.setEstado(estadoChoiceBox.getValue());

			var result = RESTClient.salvaPessoaFisica(pf);

			if (result instanceof PessoaFisica) {
				FormUtil.changeStatus(pnlStatus, "ok");

				incluirStatusLabel
						.setText("Pessoa Fisica salva com sucesso com ID = " + ((PessoaFisica) result).getId());

				FormUtil.clear(nomeTextField, cpfTextField, cidadeTextField, estadoChoiceBox);

				PauseTransition delay = new PauseTransition(Duration.seconds(2.1));
				delay.setOnFinished(e -> {
					Main.alterScene(Scenes.MAIN);
					pnlStatus.pseudoClassStateChanged(PseudoClass.getPseudoClass("ok"), false);
					incluirStatusLabel.setText("");
				});
				delay.play();

			} else {
				FormUtil.changeStatus(pnlStatus, "error");

				incluirStatusLabel.setText(((String) result));
			}

		} else {
			FormUtil.changeStatus(pnlStatus, "advrt");
			incluirStatusLabel.setText("Por favor, inclua os dados corretamente.");
		}

	}

	@FXML
	void voltarButtonOnAction(ActionEvent event) {
		FormUtil.changeStatus(pnlStatus, "");
		Main.alterScene(Scenes.MAIN);
	}

	@FXML
	void nomeTextFieldOnKeyReleased(KeyEvent event) {

	}

	PseudoClass errorClass = PseudoClass.getPseudoClass("error");

	public void initialize() {
		MaskFormatter formatter = new MaskFormatter(cpfTextField);
		formatter.setMask(MaskFormatter.CPF);
		formatter.showMask();
		TextFieldValidator.addListener(cpfTextField, "^([0-9]){3}\\.([0-9]){3}\\.([0-9]){3}-([0-9]){2}$");

		MaskFormatter formatterNome = new MaskFormatter(nomeTextField);
		formatterNome.setMask(MaskFormatter.GENERIC);
		formatterNome.setGenericLength(100);
		TextFieldValidator.addListener(nomeTextField, "^[A-z ‡·‚„ÈÍÏÌÛÙı˙Á¿¡¬√… Õ“”‘«]{5,}$");

		MaskFormatter formatterCidade = new MaskFormatter(cidadeTextField);
		formatterCidade.setMask(MaskFormatter.GENERIC);
		formatterCidade.setGenericLength(50);
		TextFieldValidator.addListener(cidadeTextField, "^[A-z ‡·‚„ÈÍÏÌÛÙı˙Á¿¡¬√… Õ“”‘«]+$");

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

				estadoChoiceBox.setItems(estados);

			}
		});

	}

}
