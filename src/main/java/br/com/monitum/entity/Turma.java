package br.com.monitum.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Turma", catalog = "dbmonitum")
public class Turma implements java.io.Serializable {

	private static final long serialVersionUID = -477335070063622357L;
	private long id;
	private Curso curso;
	private Disciplina disciplina;
	private Boolean turmaAtiva;
	private Date dataCriacao;
	private String codigoTurma;
	private Professor professor;
	private int aulasDia;
	private List<TurmaAluno> TurmaAlunos;
	private String calendario;
	private Date dataTurmaFinalizada;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_turma_finalizada", nullable = true)
	public Date getDataTurmaFinalizada() {
		return dataTurmaFinalizada;
	}

	public void setDataTurmaFinalizada(Date dataTurmaFinalizada) {
		this.dataTurmaFinalizada = dataTurmaFinalizada;
	}


	public Turma() {
	}

	public Turma(Boolean turmaAtiva, Date dataCriacao) {
		this.turmaAtiva = turmaAtiva;
		this.dataCriacao = dataCriacao;
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

	@Column(name = "turma_ativa")
	public Boolean getTurmaAtiva() {
		return this.turmaAtiva;
	}

	public void setTurmaAtiva(Boolean turmaAtiva) {
		this.turmaAtiva = turmaAtiva;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, length = 19)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getCodigoTurma() {
		return codigoTurma;
	}

	public void setCodigoTurma(String codigoTurma) {
		this.codigoTurma = codigoTurma;
	}
	@ManyToOne
	@NotNull
	@JoinColumn(name = "id_professor")
	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name="id_curso")
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@NotNull
	@JoinColumn(name="id_disciplina")
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
	@OneToMany(mappedBy = "turma")
	public List<TurmaAluno> getTurmaAlunos() {
		return TurmaAlunos;
	}

	public void setTurmaAlunos(List<TurmaAluno> turmaAlunos) {
		TurmaAlunos = turmaAlunos;
	}

	public int getAulasDia() {
		return aulasDia;
	}

	public void setAulasDia(int aulasDia) {
		this.aulasDia = aulasDia;
	}
	
	@Column(name = "calendario", nullable = true, length = 250)
	public String getCalendario() {
		return calendario;
	}

	public void setCalendario(String calendario) {
		this.calendario = calendario;
	}
}
