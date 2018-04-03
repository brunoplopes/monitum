package br.com.monitum.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Atividade", catalog = "dbmonitum")
public class Atividade implements java.io.Serializable {

	private static final long serialVersionUID = -8078113584974404520L;
	private long id;
	private Turma turma;
	private String tipoAtividade;
	private String titulo;
	private Date dataEntrega;
	private String descricaoAtividade;
	private Date dataCriacao;

	public Atividade() {
	}

	public Atividade(Turma turma, String tipoAtividade, Date dataEntrega, String descricaoAtividade, Date dataCriacao) {
		this.turma = turma;
		this.tipoAtividade = tipoAtividade;
		this.dataEntrega = dataEntrega;
		this.descricaoAtividade = descricaoAtividade;
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
	@ManyToOne
	@JoinColumn(name = "id_turma", nullable = false)
	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	

	@Temporal(TemporalType.DATE)
	@Column(name = "data_entrega", nullable = false, length = 19)
	public Date getDataEntrega() {
		return this.dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	
	@Column(name = "descricao_atividade", nullable = false, length = 300)
	public String getDescricaoAtividade() {
		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, length = 19)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	@Column(name = "tipo_atividade", nullable = false)
	public String getTipoAtividade() {
		return tipoAtividade;
	}

	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}

}
