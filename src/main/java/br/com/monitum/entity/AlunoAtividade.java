package br.com.monitum.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AlunoAtividade", catalog = "dbmonitum")
public class AlunoAtividade implements java.io.Serializable {

	private static final long serialVersionUID = 1763738962217718560L;
	private long id;
	private Atividade atividade;
	private double nota;
	private Aluno aluno;

	public AlunoAtividade() {
	}
	
	public AlunoAtividade(Atividade atividade, double nota, Aluno aluno) {
		super();
		this.atividade = atividade;
		this.nota = nota;
		this.aluno = aluno;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@OneToOne
	@JoinColumn(name = "id_atividade", nullable = false)
	public Atividade getAtividade() {
		return atividade;
	}
	
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public double getNota() {
		return nota;
	}

	public void setNota(double nota) {
		this.nota = nota;
	}

	@ManyToOne
	@JoinColumn(name = "id_aluno", nullable = false)
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	
}
