package com.cadastramento.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import com.cadastramento.model.PessoaFisica;
import com.cadastramento.model.PessoaFisicaTblModel;
import com.cadastramento.widgets.DialogUtil;
import com.cadastramento.widgets.FormUtil;
import com.cadastramento.widgets.MaskFormatter;
import com.cadastramento.widgets.TextFieldValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cadastramento.Main;
import com.cadastramento.client.RESTClient;
import com.cadastramento.enumeration.Scenes;
import com.cadastramento.interfaces.OnChangeServerStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class ListController {

	private ObservableList<PessoaFisicaTblModel> pfList = FXCollections.observableArrayList();

	@FXML
	private Pane pnlServerStatus;

	@FXML
	private Label statusLabel;

	@FXML
	private Label numRegistrosLabel;

	@FXML
	private Pane pnlStatus;

	@FXML
	private Label incluirStatusLabel;

	@FXML
	private Button voltarButton;

	@FXML
	private Button listarButton;

	@FXML
	private TextField nomeFilterTextField;

	@FXML
	private Button buscarButton;

	@FXML
	private TextField cpfFilterTextField;

	@FXML
	private TableView<PessoaFisicaTblModel> pessoasFisicaTable;

	@FXML
	private TableColumn<PessoaFisicaTblModel, String> id = new TableColumn<PessoaFisicaTblModel, String>("Id");
	@FXML
	private TableColumn<PessoaFisicaTblModel, String> nome = new TableColumn<PessoaFisicaTblModel, String>("Nome");
	@FXML
	private TableColumn<PessoaFisicaTblModel, String> cpf = new TableColumn<PessoaFisicaTblModel, String>("Cpf");
	@FXML
	private TableColumn<PessoaFisicaTblModel, String> cidade = new TableColumn<PessoaFisicaTblModel, String>("Cidade");
	@FXML
	private TableColumn<PessoaFisicaTblModel, String> estado = new TableColumn<PessoaFisicaTblModel, String>("Estado");

	@FXML
	void buscarButtonOnAction(ActionEvent event) {

		FormUtil.changeStatus(pnlStatus, "");

		pessoasFisicaTable.getItems().clear();
		numRegistrosLabel.setText("0");

		Object pf = null;

		if (nomeFilterTextField.getText().isEmpty() && cpfFilterTextField.getText().isEmpty()
				|| !FormUtil.isValid(nomeFilterTextField) && !FormUtil.isValid(cpfFilterTextField)) {

			FormUtil.changeStatus(pnlStatus, "advrt");

			incluirStatusLabel.setText("Inclua pelo menos um dos dados corretamente para pesquisa . . .");
		}

		if (FormUtil.isValid(nomeFilterTextField)) {
			pf = RESTClient.buscarPorNome(nomeFilterTextField.getText());
		}

		if (FormUtil.isValid(cpfFilterTextField) && !(pf instanceof ArrayList)) {
			pf = RESTClient.buscarPorCPF(cpfFilterTextField.getText());
		}

		if (pf instanceof ArrayList && !((ArrayList) pf).isEmpty()) {

			popularLista((ArrayList<PessoaFisica>) pf);

		} else if (pf != null) {
			FormUtil.changeStatus(pnlStatus, "error");
			incluirStatusLabel.setText(pf.toString());
		}

	}

	@FXML
	void listarButtonOnAction(ActionEvent event) {
		FormUtil.changeStatus(pnlStatus, "");
		buscarTodosRegistros();
	}

	@FXML
	void voltarButtonOnAction(ActionEvent event) {
		numRegistrosLabel.setText("0");
		FormUtil.changeStatus(pnlStatus, "");
		pessoasFisicaTable.getItems().clear();
		Main.alterScene(Scenes.MAIN);
	}

	public void initialize() {

		MaskFormatter formatter = new MaskFormatter(cpfFilterTextField);
		formatter.setMask(MaskFormatter.CPF);
		formatter.showMask();
		TextFieldValidator.addListener(cpfFilterTextField, "^([0-9]){3}\\.([0-9]){3}\\.([0-9]){3}-([0-9]){2}$");

		MaskFormatter formatterNome = new MaskFormatter(nomeFilterTextField);
		formatterNome.setMask(MaskFormatter.GENERIC);
		formatterNome.setGenericLength(100);
		TextFieldValidator.addListener(nomeFilterTextField, "^[A-z àáâãéêìíóôõúçÀÁÂÃÉÊÍÒÓÔÇ]+$");

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

		initializeTable();

	}

	private void buscarTodosRegistros() {
		try {

			pessoasFisicaTable.getItems().clear();
			numRegistrosLabel.setText("0");

			popularLista(RESTClient.buscarTodosRegistros());

		} catch (RuntimeException e) {
			e.printStackTrace();
			DialogUtil.buildExceptionDialog("Ocorreu um erro", "Erro ao conectar ao serviço", e).showAndWait();
			System.exit(-1);
		}
	}

	private void popularLista(ArrayList<PessoaFisica> registros) {

		List<PessoaFisicaTblModel> pft = registros.stream().map(pf -> new PessoaFisicaTblModel(pf))
				.collect(Collectors.toList());

		pfList = FXCollections.observableArrayList(pft);

		if(pfList.size() == 0) {
			FormUtil.changeStatus(pnlStatus, "advrt");
			incluirStatusLabel.setText("Não há registros . . .");
		}


		pessoasFisicaTable.setItems(pfList);
		numRegistrosLabel.setText("" + pessoasFisicaTable.getItems().size());

	}

	private void initializeTable() {

		id.setCellValueFactory(new PropertyValueFactory<PessoaFisicaTblModel, String>("id"));
		nome.setCellValueFactory(new PropertyValueFactory<PessoaFisicaTblModel, String>("nome"));
		cpf.setCellValueFactory(new PropertyValueFactory<PessoaFisicaTblModel, String>("cpf"));
		cidade.setCellValueFactory(new PropertyValueFactory<PessoaFisicaTblModel, String>("cidade"));
		estado.setCellValueFactory(new PropertyValueFactory<PessoaFisicaTblModel, String>("estado"));

	}
}
