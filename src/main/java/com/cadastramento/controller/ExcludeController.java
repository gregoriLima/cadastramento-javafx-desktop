package com.cadastramento.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import com.cadastramento.Main;
import com.cadastramento.client.RESTClient;
import com.cadastramento.enumeration.Scenes;
import com.cadastramento.interfaces.OnChangeServerStatus;
import com.cadastramento.model.PessoaFisica;
import com.cadastramento.widgets.DialogUtil;
import com.cadastramento.widgets.FormUtil;
import com.cadastramento.widgets.MaskFormatter;
import com.cadastramento.widgets.TextFieldValidator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class ExcludeController {

	@FXML
	private Pane pnlStatus;

	@FXML
	private Label incluirStatusLabel;

	@FXML
	private Pane pnlServerStatus;

	@FXML
	private Label statusLabel;

	@FXML
	private Button voltarButton;

	@FXML
	private Button excluirButtonOnAction;

	@FXML
	private Button buscarButton;

	@FXML
	private TextField cpfSearchTextField;

	@FXML
	private TextField cpfTextField;

	@FXML
	private TextField cidadeTextField;

	@FXML
	private TextField nomeTextField;

	@FXML
	private TextField estadoTextField;

	private static Object pf = null;

	@FXML
	void buscarButtonOnAction(ActionEvent event) {

		FormUtil.changeStatus(pnlStatus, "");

		incluirStatusLabel.setText("");

		FormUtil.clear(nomeTextField, cpfTextField, cidadeTextField, estadoTextField);

		pf = null;

		if (FormUtil.isValid(cpfSearchTextField)) {
			pf = RESTClient.buscarPorCPF(cpfSearchTextField.getText());
		} else {
			FormUtil.changeStatus(pnlStatus, "advrt");
			incluirStatusLabel.setText("Preencha corretamente o campo.");
		}

		if (pf instanceof ArrayList && !((ArrayList) pf).isEmpty()) {

			pf = ((ArrayList)pf).get(0);

			nomeTextField.setText(((PessoaFisica) pf).getNome());
			cpfTextField.setText(((PessoaFisica) pf).getCpf());
			cidadeTextField.setText(((PessoaFisica) pf).getCidade());
			estadoTextField.setText(((PessoaFisica) pf).getEstado());

		} else if (pf != null ) {
			FormUtil.changeStatus(pnlStatus, "error");
			incluirStatusLabel.setText(pf.toString());
		}

	}

	@FXML
	void excluirButtonOnAction(ActionEvent event) {

		if (pf instanceof PessoaFisica) {
			var x = DialogUtil.buildSimpleDialog("Confirmação", null, "Você realmente quer excluir este registro?",
					AlertType.CONFIRMATION).showAndWait();

			if (x.isPresent() && x.get().getText().equals("OK")) {
				if (RESTClient.excluirPorId(((PessoaFisica) pf).getId())) {

					FormUtil.clear(nomeTextField, cpfTextField, cidadeTextField, estadoTextField, cpfSearchTextField);

					DialogUtil
							.buildSimpleDialog("Sucesso", null, "Registro excluido com sucesso!", AlertType.INFORMATION)
							.showAndWait();

					voltarButtonOnAction(new ActionEvent());
				}
				;
			}
			;
		} else {
			FormUtil.changeStatus(pnlStatus, "advrt");
			incluirStatusLabel.setText("Busque um registro antes de tentar excluir.");
		}

	}

	@FXML
	void voltarButtonOnAction(ActionEvent event) {
		pf = null;
		FormUtil.changeStatus(pnlStatus, "");
		incluirStatusLabel.setText("");
		Main.alterScene(Scenes.MAIN);
	}

	public void initialize() {

		MaskFormatter formatter = new MaskFormatter(cpfSearchTextField);
		formatter.setMask(MaskFormatter.CPF);
		formatter.showMask();
		TextFieldValidator.addListener(cpfSearchTextField, "^([0-9]){3}\\.([0-9]){3}\\.([0-9]){3}-([0-9]){2}$");

		nomeTextField.setEditable(false);
		cpfTextField.setEditable(false);
		cidadeTextField.setEditable(false);
		estadoTextField.setEditable(false);

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
