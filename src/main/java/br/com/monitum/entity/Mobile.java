package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Mobile {
	private long id;
	private Usuario usuario;
	private String accessToken;
	private String gcmToken;
	private Boolean receberNotivicacao;
	public Mobile() {
		super();
	}
	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@Column(name = "access_token", nullable = false, length = 100)
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	@Column(name = "gcm_token", nullable = false, length = 200)
	public String getGcmToken() {
		return gcmToken;
	}
	public void setGcmToken(String gcmToken) {
		this.gcmToken = gcmToken;
	}
	@Column(name = "receber_notificacao", nullable = false)
	public Boolean getReceberNotivicacao() {
		return receberNotivicacao;
	}
	public void setReceberNotivicacao(Boolean receberNotivicacao) {
		this.receberNotivicacao = receberNotivicacao;
	}
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
