package br.com.monitum.dto;

import java.util.List;

public class GrupoDTO {
	private long idTurma;
	private long idAtividade;
	private int numerointegrantes;
	private String nome;
	private List<GrupoDTO> grupos;
	public GrupoDTO() {
	}
	
	public GrupoDTO(long idAtividade) {
		super();
		this.idAtividade = idAtividade;
	}

	public int getNumerointegrantes() {
		return numerointegrantes;
	}
	public void setNumerointegrantes(int numerointegrantes) {
		this.numerointegrantes = numerointegrantes;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<GrupoDTO> getGrupos() {
		return grupos;
	}
	public void setGrupos(List<GrupoDTO> grupos) {
		this.grupos = grupos;
	}
	public long getIdAtividade() {
		return idAtividade;
	}
	public void setIdAtividade(long idAtividade) {
		this.idAtividade = idAtividade;
	}
	public long getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	
}
