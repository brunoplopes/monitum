package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Disciplina", catalog = "dbmonitum")
public class Disciplina implements java.io.Serializable {

	private static final long serialVersionUID = 839789858489530803L;
	private long id;
	private String nomeDisciplina;
	private String codDisciplina;
	private Boolean ativo;

	public Disciplina() {
	}

	public Disciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	public Disciplina(String nomeDisciplina, String codDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
		this.codDisciplina = codDisciplina;
		this.ativo = true;
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

	@Column(name = "nome_disciplina", nullable = false, length = 150)
	public String getNomeDisciplina() {
		return this.nomeDisciplina;
	}

	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}

	@Column(name = "cod_disciplina", length = 10)
	public String getCodDisciplina() {
		return this.codDisciplina;
	}

	public void setCodDisciplina(String codDisciplina) {
		this.codDisciplina = codDisciplina;
	}

	@Override
	public String toString() {
		return "Disciplina [id=" + id + ", nomeDisciplina=" + nomeDisciplina + ", codDisciplina=" + codDisciplina + "]";
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

}
