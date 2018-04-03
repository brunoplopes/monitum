package br.com.monitum.dto;

import java.util.List;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;


public class TurmaDTO {
	private long cursoId;
	private long disciplinaId;
	@NotEmpty(message="O código da turma é obrigatório")
	private String codigoTurma;
	@Min(1)
	private int aulasDia;
	private List<AlunoDTO> alunos;
	private MultipartFile arquivo;
	private String calendario;

	public String getCodigoTurma() {
		return codigoTurma;
	}
	public void setCodigoTurma(String codigoTurma) {
		this.codigoTurma = codigoTurma;
	}
	public MultipartFile getArquivo() {
		return arquivo;
	}
	public void setArquivo(MultipartFile arquivo) {
		this.arquivo = arquivo;
	}
	public long getCursoId() {
		return cursoId;
	}
	public void setCursoId(long cursoId) {
		this.cursoId = cursoId;
	}
	public long getDisciplinaId() {
		return disciplinaId;
	}
	public void setDisciplinaId(long disciplinaId) {
		this.disciplinaId = disciplinaId;
	}
	public int getAulasDia() {
		return aulasDia;
	}
	public void setAulasDia(int aulasDia) {
		this.aulasDia = aulasDia;
	}
	public List<AlunoDTO> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<AlunoDTO> alunos) {
		this.alunos = alunos;
	}
	public String getCalendario() {
		return calendario;
	}
	public void setCalendario(String calendario) {
		this.calendario = calendario;
	}
	@Override
	public String toString() {
		return "TurmaDTO [cursoId=" + cursoId + ", disciplinaId=" + disciplinaId + ", codigoTurma=" + codigoTurma
				+ ", aulasDia=" + aulasDia + ", alunos=" + alunos + ", arquivo=" + arquivo + "]";
	}
	
}
