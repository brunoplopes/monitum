package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "CursoDisciplina", catalog = "dbmonitum")
public class CursoDisciplina implements java.io.Serializable {

	private static final long serialVersionUID = -1298150631268969201L;
	private long id;
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_curso")
	private Curso curso;
	@ManyToOne
	@NotNull
	@JoinColumn(name="id_disciplina")
	private Disciplina disciplina;


	public Curso getCurso() {
		return this.curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Disciplina getDisciplina() {
		return this.disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
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
	
}
