package application.model;

public class PessoaFisica {

	private int id;

	private String nome;

	private String cpf;

	private String estado;

	private String cidade;

	public PessoaFisica() {
	}

	public PessoaFisica(int id, String nome, String cpf, String estado, String cidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.estado = estado;
		this.cidade = cidade;
	}

	public PessoaFisica(String nome, String cpf, String cidade, String estado) {
		this.nome = nome;
		this.cpf = cpf;
		this.cidade = cidade;
		this.estado = estado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

}
