package br.com.monitum.entity;
// Generated Apr 3, 2016 11:59:31 PM by Hibernate Tools 4.3.1.Final

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
@Table(name = "Grupo", catalog = "dbmonitum")
public class Grupo implements java.io.Serializable {

	private static final long serialVersionUID = -2846815953860259695L;
	private long id;
	private Atividade atividade;
	private String nomeGrupo;
	private Date dataCriacao;
	private int numeroIntegrates;

	public Grupo() {
	}
	
	public Grupo(Atividade atividade, String nomeGrupo, Date dataCriacao, int numeroIntegrates) {
		super();
		this.atividade = atividade;
		this.nomeGrupo = nomeGrupo;
		this.dataCriacao = dataCriacao;
		this.numeroIntegrates = numeroIntegrates;
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
	@JoinColumn(name="id_atividade", nullable=false)
	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	@Column(name = "nome_grupo", nullable = false, length = 100)
	public String getNomeGrupo() {
		return this.nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, length = 10)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	@Column(nullable = false)
	public int getNumeroIntegrates() {
		return numeroIntegrates;
	}

	public void setNumeroIntegrates(int numeroIntegrates) {
		this.numeroIntegrates = numeroIntegrates;
	}

}
