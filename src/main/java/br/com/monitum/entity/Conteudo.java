package br.com.monitum.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Conteudo", catalog = "dbmonitum")
public class Conteudo implements java.io.Serializable {

	private static final long serialVersionUID = 3955299102040140951L;
	private long id;
	private Turma turma;
	private Usuario usuario;
	private TipoConteudo tipoConteudo;
	private String titulo;
	private String descricao;
	private Date dataCriacao;
	private Boolean editado;
	private Date dataEdicao;

	public Conteudo() {
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne
	@JoinColumn(name="id_turma", nullable = false)
	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}
	@ManyToOne
	@JoinColumn(name="id_usuario", nullable = false)
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	@ManyToOne
	@JoinColumn(name="id_tipo_conteudo", nullable = false)
	public TipoConteudo getTipoConteudo() {
		return this.tipoConteudo;
	}

	public void setTipoConteudo(TipoConteudo tipoConteudo) {
		this.tipoConteudo = tipoConteudo;
	}

	@Column(name = "titulo", nullable = false, length = 150)
	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Column(name = "descricao", nullable = false, length = 2000)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao", nullable = false, length = 10)
	public Date getDataCriacao() {
		return this.dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_edicao", nullable = true, length = 10)
	public Date getDataEdicao() {
		return this.dataEdicao;
	}

	public void setDataEdicao(Date dataEdicao) {
		this.dataEdicao = dataEdicao;
	}
	
	@Column(name = "editado", nullable = true)
	public Boolean getEditado() {
		return this.editado;
	}

	public void setEditado(Boolean editado) {
		this.editado = editado;
	}

}
