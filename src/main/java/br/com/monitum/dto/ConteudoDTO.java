package br.com.monitum.dto;

import java.util.Date;

public class ConteudoDTO {
	private long idTurma;
	private long idTipoConteudo;
	private long idUsuario;
	private long idConteudo;
	private String titulo;
	private String descricao;
	private String nome;
	private Date dataPostagem;
	private String tipoConteudo;
	private String urlFoto;
	private Boolean editado;
	private Date dataEdicao;
	
	public long getIdConteudo() {
		return idConteudo;
	}
	public void setIdConteudo(long idConteudo) {
		this.idConteudo = idConteudo;
	}
	public long getIdTipoConteudo() {
		return idTipoConteudo;
	}
	public void setIdTipoConteudo(long idTipoConteudo) {
		this.idTipoConteudo = idTipoConteudo;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataPostagem() {
		return dataPostagem;
	}
	public void setDataPostagem(Date dataPostagem) {
		this.dataPostagem = dataPostagem;
	}
	public String getTipoConteudo() {
		return tipoConteudo;
	}
	public void setTipoConteudo(String tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public Boolean getEditado() {
		return editado;
	}
	public void setEditado(Boolean editado) {
		this.editado = editado;
	}
	public Date getDataEdicao() {
		return dataEdicao;
	}
	public void setDataEdicao(Date dataEdicao) {
		this.dataEdicao = dataEdicao;
	}
	
}
