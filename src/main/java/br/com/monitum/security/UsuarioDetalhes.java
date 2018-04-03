package br.com.monitum.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import br.com.monitum.entity.TipoUsuario;
import br.com.monitum.entity.Usuario;

public class UsuarioDetalhes extends User{
	private Long id;
	private String nome;
	private TipoUsuario tipoUsuario;
	private Boolean ativo;
	private String foto;

	private static final long serialVersionUID = -2037296986496733918L;

	public UsuarioDetalhes(Usuario usuario, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(usuario.getEmail(), usuario.getSenha(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.tipoUsuario = usuario.getTipoUsuario();
		this.ativo = usuario.getAtivo();
		this.foto = usuario.getFoto();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}
	
}
