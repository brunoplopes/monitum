package br.com.monitum.dto;

import java.util.List;

public class AtividadeGrupoDTO {
	private long idTurma;
	private long idAtividade;
	private long idGrupo;
	private String atividadeTitulo;
	List<String> nomegrupos;
	
	public AtividadeGrupoDTO() {
		super();
	}
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public long getIdAtividade() {
		return idAtividade;
	}
	public void setIdAtividade(long idAtividade) {
		this.idAtividade = idAtividade;
	}
	public String getAtividadeTitulo() {
		return atividadeTitulo;
	}
	public void setAtividadeTitulo(String atividadeTitulo) {
		this.atividadeTitulo = atividadeTitulo;
	}
	public List<String> getNomegrupos() {
		return nomegrupos;
	}
	public void setNomegrupos(List<String> nomegrupos) {
		this.nomegrupos = nomegrupos;
	}
	public long getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(long idGrupo) {
		this.idGrupo = idGrupo;
	}
}
