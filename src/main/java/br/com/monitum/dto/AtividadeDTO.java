package br.com.monitum.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AtividadeDTO implements java.io.Serializable{
	

	private static final long serialVersionUID = -7384406888241307420L;
	private long idTurma;
	private String tipoAtividade;
	private Date dataEntrega;
	private String descricaoAtividade;
	private Date dataCriacao;
	private String titulo;
	
	public AtividadeDTO() {
	}
	public AtividadeDTO(long idTurma) {
		this.idTurma = idTurma;
	}
	
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public String getTipoAtividade() {
		return tipoAtividade;
	}
	public void setTipoAtividade(String tipoAtividade) {
		this.tipoAtividade = tipoAtividade;
	}
	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
	public Date getDataCriacao() {
		return dataCriacao;
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
	public String getDescricaoAtividade() {
		return descricaoAtividade;
	}
	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade;
	}
}
