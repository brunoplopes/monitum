package br.com.monitum.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Frequencia", catalog = "dbmonitum")
public class Frequencia implements java.io.Serializable {

	private static final long serialVersionUID = 6540184475979101404L;
	private long id;
	private Turma turma;
	private Date dataAula;
	private int quantidadeAula;
	private Date dataRegistro;
	private Periodo periodo;

	public Frequencia() {
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

	@Temporal(TemporalType.DATE)
	@Column(name = "data_aula", nullable = false, length = 19)
	public Date getDataAula() {
		return this.dataAula;
	}

	public void setDataAula(Date dataAula) {
		this.dataAula = dataAula;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDataRegistro() {
		return dataRegistro;
	}

	public void setDataRegistro(Date dataRegistro) {
		this.dataRegistro = dataRegistro;
	}

	@Column(name = "quantidade_aula", nullable = false)
	public int getQuantidadeAula() {
		return this.quantidadeAula;
	}

	public void setQuantidadeAula(int quantidadeAula) {
		this.quantidadeAula = quantidadeAula;
	}
	@Column(name = "periodo", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	public Periodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	@ManyToOne
	@JoinColumn(name="id_turma", nullable = false)
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
}
