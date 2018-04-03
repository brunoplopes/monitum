package br.com.monitum.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.monitum.entity.Professor;

public class ProfessorDTO {
	private MultipartFile arquivo;
	private List<Professor> professores;
	public MultipartFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(MultipartFile arquivo) {
		this.arquivo = arquivo;
	}
	public List<Professor> getProfessores() {
		return professores;
	}
	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}
	
}
