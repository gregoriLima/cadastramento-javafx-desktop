package application.model;

import com.google.gson.Gson;

public class Usuario {

	private String email;

	private String senha;

	public Usuario(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}

}
