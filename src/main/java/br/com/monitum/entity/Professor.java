package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Professor", catalog = "dbmonitum")
public class Professor implements java.io.Serializable {

	private static final long serialVersionUID = 3474921203164078492L;
	private long id;
	private Usuario usuario;
	private String nome;
	private String email;
	private String registroProfessor;
	private boolean coordenador;

	public Professor() {
	}
	
	public Professor(String nome, String email, String registroProfessor) {
		super();
		this.nome = nome;
		this.email = email;
		this.registroProfessor = registroProfessor;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "registro_professor", nullable = false, length = 20, unique=true)
	public String getRegistroProfessor() {
		return this.registroProfessor;
	}

	public void setRegistroProfessor(String registroProfessor) {
		this.registroProfessor = registroProfessor;
	}

	@Column(name = "coordenador", nullable = false)
	public boolean isCoordenador() {
		return this.coordenador;
	}

	public void setCoordenador(boolean coordenador) {
		this.coordenador = coordenador;
	}
	@OneToOne
	@JoinColumn(name="id_usuario", nullable=true)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
	
}
