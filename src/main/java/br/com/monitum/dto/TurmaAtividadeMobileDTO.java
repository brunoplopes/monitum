package br.com.monitum.dto;

import java.util.List;

public class TurmaAtividadeMobileDTO {
	private String faltas;
	private List<AtividadeMobileDTO> atividades;
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
}
