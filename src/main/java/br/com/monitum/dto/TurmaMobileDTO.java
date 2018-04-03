package br.com.monitum.dto;

import java.util.List;

public class TurmaMobileDTO {
	private long idTurma;
	private String disciplina;
	private String faltas;
	private String calendario;
	private List<AtividadeMobileDTO> atividades;
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public String getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	public String getFaltas() {
		return faltas;
	}
	public void setFaltas(String faltas) {
		this.faltas = faltas;
	}
	public List<AtividadeMobileDTO> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<AtividadeMobileDTO> atividades) {
		this.atividades = atividades;
	}
	public String getCalendario() {
		return calendario;
	}
	public void setCalendario(String calendario) {
		this.calendario = calendario;
	}
	
}
