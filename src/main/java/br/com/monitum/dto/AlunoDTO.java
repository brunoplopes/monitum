package br.com.monitum.dto;

public class AlunoDTO {
	private long id;
	private String prontuario;
	private String nome;
	private String codigo;
	
	public AlunoDTO(){
	}
	
	public long getId(){
		return id;
	}	
	public void setId(long id){
		this.id = id;
	}
	public AlunoDTO(String prontuario, String nome, String codigo) {
		super();
		this.prontuario = prontuario;
		this.nome = nome;
		this.codigo = codigo;
	}
	public String getProntuario() {
		return prontuario;
	}
	public void setProntuario(String prontuario) {
		this.prontuario = prontuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
