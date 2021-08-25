package application.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PessoaFisicaTblModel {
	public final IntegerProperty id;
	public final StringProperty nome;
	public final StringProperty cpf;
	public final StringProperty cidade;
	public final StringProperty estado;

	public PessoaFisicaTblModel() {
		this.id = new SimpleIntegerProperty();
		this.nome = new SimpleStringProperty();
		this.cpf = new SimpleStringProperty();
		this.cidade = new SimpleStringProperty();
		this.estado = new SimpleStringProperty();

	}

	public PessoaFisicaTblModel(Integer id, String nome, String cpf, String cidade, String estado) {
		this.id = new SimpleIntegerProperty(id);
		this.nome = new SimpleStringProperty(nome);
		this.cpf = new SimpleStringProperty(cpf);
		this.cidade = new SimpleStringProperty(cidade);
		this.estado = new SimpleStringProperty(estado);

	}

	public PessoaFisicaTblModel(PessoaFisica pf) {
		this.id = new SimpleIntegerProperty(pf.getId());
		this.nome = new SimpleStringProperty(pf.getNome());
		this.cpf = new SimpleStringProperty(pf.getCpf());
		this.cidade = new SimpleStringProperty(pf.getCidade());
		this.estado = new SimpleStringProperty(pf.getEstado());
	}

	public final StringProperty getNomeProperty() {
		return nome;
	}

	public final String getNome() {
		return nome.get();
	}

	public final void setNome(String value) {
		nome.set(value);
	}

	public Integer getId() {
		return id.get();
	}

	public IntegerProperty getIdProperty() {
		return id;
	}

	public void setId(Integer id) {
		this.id.set(id);
	}

	public String getCpf() {
		return cpf.get();
	}

	public StringProperty getCpfProperty() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf.set(cpf);
	}

	public String getCidade() {
		return cidade.get();
	}

	public void setCidade(String cidade) {
		this.cidade.set(cidade);
	}

	public StringProperty getCidadeProperty() {
		return cidade;
	}

	public String getEstado() {
		return estado.get();
	}

	public void setEstado(String estado) {
		this.estado.set(estado);
	}

	public StringProperty getEstadoProperty() {
		return estado;
	}

}