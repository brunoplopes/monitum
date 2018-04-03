package br.com.monitum.dto;

import java.util.List;

public class AtividadeAlunoDTO {
	private List<AlunoNotaDTO> alunosNota;
	private long idAtividade;
	private String titulo;
	
	public AtividadeAlunoDTO() {
		super();
	}
	public List<AlunoNotaDTO> getAlunosNota() {
		return alunosNota;
	}
	public void setAlunosNota(List<AlunoNotaDTO> alunosNota) {
		this.alunosNota = alunosNota;
	}
	public long getIdAtividade() {
		return idAtividade;
	}
	public void setIdAtividade(long idAtividade) {
		this.idAtividade = idAtividade;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
}
