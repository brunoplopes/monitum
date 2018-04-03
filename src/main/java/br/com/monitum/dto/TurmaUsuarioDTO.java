package br.com.monitum.dto;

public class TurmaUsuarioDTO {
	private long idTurma;
	private String codigo;
	private String nomeDisciplina;
	private String calendario;
	private String faltas;
	
	public TurmaUsuarioDTO() {
		super();
	}
	public TurmaUsuarioDTO(long idTurma, String codigo, String nomeDisciplina, String calendario, String faltas) {
		super();
		this.idTurma = idTurma;
		this.codigo = codigo;
		this.nomeDisciplina = nomeDisciplina;
		this.calendario = calendario;
		this.faltas = faltas;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNomeDisciplina() {
		return nomeDisciplina;
	}
	public void setNomeDisciplina(String nomeDisciplina) {
		this.nomeDisciplina = nomeDisciplina;
	}
	public long getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(long idTurma) {
		this.idTurma = idTurma;
	}
	public String getCalendario() {
		return calendario;
	}
	public void setCalendario(String calendario) {
		this.calendario = calendario;
	}
	public String getFaltas() {
		return faltas;
	}
	public void setFaltas(String faltas) {
		this.faltas = faltas;
	}
	
}
