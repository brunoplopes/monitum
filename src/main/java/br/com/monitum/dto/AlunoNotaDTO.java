package br.com.monitum.dto;

public class AlunoNotaDTO {
	private long idAluno;
	private double nota;
	private String nome;
	
	public AlunoNotaDTO() {
	}
	public AlunoNotaDTO(long idAluno,String nome, double nota) {
		super();
		this.idAluno = idAluno;
		this.nota = nota;
		this.nome = nome;
	}
	public long getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(long idAluno) {
		this.idAluno = idAluno;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
