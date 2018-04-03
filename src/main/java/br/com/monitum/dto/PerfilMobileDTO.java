package br.com.monitum.dto;

import br.com.monitum.entity.TipoUsuario;

public class PerfilMobileDTO {
	private String token;
	private String nome;
	private String email;
	private String telefone;
	private Boolean receberNotificacao;
	private TipoUsuario tipoUsuario;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
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
	public Boolean getReceberNotificacao() {
		return receberNotificacao;
	}
	public void setReceberNotificacao(Boolean receberNotificacao) {
		this.receberNotificacao = receberNotificacao;
	}
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}
	
}
