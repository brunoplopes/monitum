package br.com.monitum.entity;
// Generated Apr 3, 2016 11:59:31 PM by Hibernate Tools 4.3.1.Final

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "GrupoAluno", catalog = "dbmonitum")
public class GrupoAluno implements java.io.Serializable {
	private static final long serialVersionUID = -7662369298937265890L;
	private long id;
	private Aluno aluno;
	private Grupo grupo;
	private Date dataEntrada;

	public GrupoAluno() {
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
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_entrada", nullable = false, length = 19)
	public Date getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(Date dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_aluno", nullable = false)
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "id_grupo", nullable = false)
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}
