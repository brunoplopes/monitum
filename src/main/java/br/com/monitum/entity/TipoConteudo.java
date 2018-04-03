package br.com.monitum.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TipoConteudo", catalog = "dbmonitum")
public class TipoConteudo implements java.io.Serializable {

	private static final long serialVersionUID = -326356432720381130L;
	private long id;
	private String nomeTipoConteudo;

	public TipoConteudo() {
	}

	public TipoConteudo(String nomeTipoConteudo) {
		this.nomeTipoConteudo = nomeTipoConteudo;
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

	@Column(name = "nome_tipo_conteudo", nullable = false, length = 50)
	public String getNomeTipoConteudo() {
		return this.nomeTipoConteudo;
	}

	public void setNomeTipoConteudo(String nomeTipoConteudo) {
		this.nomeTipoConteudo = nomeTipoConteudo;
	}

}
