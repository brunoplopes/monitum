package br.com.monitum.dto;

import org.hibernate.validator.constraints.NotEmpty;

public class UsuarioDTO implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@NotEmpty
	private String nome;
	@NotEmpty
	private String email;
	private String telefone;
	@NotEmpty
	private String senha;
	private String foto;
	@NotEmpty
	private String registro;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getRegistro() {
		return registro;
	}
	public void setRegistro(String registro) {
		this.registro = registro;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
