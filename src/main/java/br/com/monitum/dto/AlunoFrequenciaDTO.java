package br.com.monitum.dto;

import java.util.List;

public class AlunoFrequenciaDTO {
	private long idAluno;
	private String codigo;
	private String urlFoto;
	private String nome;
	private List<Boolean> frequencia;
	private String grupo;

	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Boolean> getFrequencia() {
		return frequencia;
	}
	public void setFrequencia(List<Boolean> frequencia) {
		this.frequencia = frequencia;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public long getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(long idAluno) {
		this.idAluno = idAluno;
	}
}
