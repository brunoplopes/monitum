package br.com.monitum.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.monitum.entity.Periodo;

public class FrequenciaDTO {
	private long idTurma;
	private String codigoTurma;
	private String codigoDisciplina;
	private List<AlunoFrequenciaDTO> alunosFrequencia;
	private int quantidadeAula;
	private Periodo periodo;
	private Date dataAula;
	private long idFrequencia;
	public String getCodigoTurma() {
		return codigoTurma;
	}
	public void setCodigoTurma(String codigoTurma) {
		this.codigoTurma = codigoTurma;
	}
	public String getCodigoDisciplina() {
		return codigoDisciplina;
	}
	public void setCodigoDisciplina(String codigoDisciplina) {
		this.codigoDisciplina = codigoDisciplina;
	}
	public List<AlunoFrequenciaDTO> getAlunosFrequencia() {
		return alunosFrequencia;
	}
	public void setAlunosFrequencia(List<AlunoFrequenciaDTO> alunosFrequencia) {
		this.alunosFrequencia = alunosFrequencia;
	}
	public int getQuantidadeAula() {
		return quantidadeAula;
	}
	public void setQuantidadeAula(int quantidadeAula) {
		this.quantidadeAula = quantidadeAula;
	}
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	@DateTimeFormat(pattern="dd/MM/yyyy")
	public Date getDataAula() {
		return dataAula;
	}
	public void setDataAula(Date dataAula) {
		this.dataAula = dataAula;
	}
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public long getIdFrequencia() {
		return idFrequencia;
	}
	public void setIdFrequencia(long idFrequencia) {
		this.idFrequencia = idFrequencia;
	}
}
