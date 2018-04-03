package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Aluno", catalog = "dbmonitum")
public class Aluno implements java.io.Serializable {

	private static final long serialVersionUID = 542639126006215078L;
	private long id;
	private Usuario usuario;
	private String prontuario;
	private String nome;
	//private List<Turma> turmas;
	private List<TurmaAluno> TurmasAluno;

	public Aluno(){
	}
	
	public Aluno(Usuario usuario, String prontuario, String nome) {
		super();
		this.usuario = usuario;
		this.prontuario = prontuario;
		this.nome = nome;
	}

	public Aluno(String nome ,String prontuario) {
		super();
		this.prontuario = prontuario;
		this.nome = nome;
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

	@Column(name = "prontuario", nullable = false, length = 20, unique=true)
	public String getProntuario() {
		return this.prontuario;
	}

	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	@OneToOne
	@JoinColumn(name="id_usuario", nullable = true)
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	/*@ManyToMany(fetch = FetchType.LAZY, mappedBy = "alunos")
	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}*/
	@OneToMany(mappedBy = "aluno")
	public List<TurmaAluno> getTurmasAluno() {
		return TurmasAluno;
	}
	public void setTurmasAluno(List<TurmaAluno> turmasAluno) {
		TurmasAluno = turmasAluno;
	}

}
