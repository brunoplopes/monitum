package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Curso", catalog = "dbmonitum")
public class Curso implements java.io.Serializable {
	private static final long serialVersionUID = -7684915772604622596L;
	private long id;
	private String nomeCurso;
	private String codCurso;
	private Boolean ativo;

	public Curso() {
	}

	public Curso(String nomeCurso, String codCurso) {
		this.nomeCurso = nomeCurso;
		this.codCurso = codCurso;
		this.ativo = true;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_curso", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "nome_curso", nullable = false, length = 150)
	public String getNomeCurso() {
		return this.nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}
	
	@Column(name = "cod_curso", nullable = true, length = 15)
	public String getCodCurso() {
		return this.codCurso;
	}

	public void setCodCurso(String codCurso) {
		this.codCurso = codCurso;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nomeCurso=" + nomeCurso + "]";
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	
}
