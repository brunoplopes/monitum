package br.com.monitum.entity;
// Generated Apr 3, 2016 11:59:31 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "Usuario", catalog = "dbmonitum")
public class Usuario implements java.io.Serializable {

	private static final long serialVersionUID = 2593493911171541897L;
	private long id;
	private String nome;
	private String email;
	private String telefone;
	private String senha;
	private Date dataCriacao;
	private TipoUsuario tipoUsuario;
	private String foto;
	private Boolean ativo;
	private String token;

	public Usuario() {
	}

	public Usuario(long id, String nome, String email, String senha, Date dataCriacao,
			TipoUsuario tipoUsuario) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataCriacao = dataCriacao;
		this.tipoUsuario = tipoUsuario;
	}

	public Usuario(long id, String nome, String email, String telefone, String senha,
			Date dataCriacao, TipoUsuario tipoUsuario, String foto) {
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.senha = senha;
		this.dataCriacao = dataCriacao;
		this.tipoUsuario = tipoUsuario;
		this.foto = foto;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false, length = 150)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "email", nullable = false, length = 150, unique=true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "telefone", length = 15)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "senha", nullable = false, length = 100)
	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, length = 19)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Column(name = "tipo_usuario", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	public TipoUsuario getTipoUsuario() {
		return this.tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	@Column(name = "foto", length = 100)
	public String getFoto() {
		return this.foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	@Column(name = "ativo", nullable = false)
	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + ", senha="
				+ senha + ", dataCriacao=" + dataCriacao + ", tipoUsuario=" + tipoUsuario + ", foto=" + foto
				+ ", ativo=" + ativo + "]";
	}
	@Transient
	public String getRole(){
		if(getTipoUsuario().equals(TipoUsuario.ESTUDANTE))
			return "ROLE_ESTUDANTE";
		if(getTipoUsuario().equals(TipoUsuario.PROFESSOR))
			return "ROLE_PROFESSOR";
		if(getTipoUsuario().equals(TipoUsuario.COORDENADOR))
			return "ROLE_COORDENADOR";
		return null;
	}
	@Column(name = "token", length = 100, nullable = false)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
