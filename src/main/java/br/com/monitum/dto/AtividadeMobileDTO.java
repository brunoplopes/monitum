package br.com.monitum.dto;

import java.sql.Timestamp;

public class AtividadeMobileDTO {
	private String titulo;
	private Timestamp dataEntrega;
	private String descricao;
	private String nota;
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Timestamp getDataEntrega() {
		return dataEntrega;
	}
	public void setDataEntrega(Timestamp dataEntrega) {
		this.dataEntrega = dataEntrega;
	}
}
