package br.com.monitum.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.monitum.entity.Usuario;
import br.com.monitum.repository.UsuarioRepository;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {
		
	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
		
		Usuario usuario = usuarioRepository.findByEmail(email);
		List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
		authorityList = AuthorityUtils.createAuthorityList(usuario.getTipoUsuario().name(), usuario.getRole(), AuthorityConstant.ROLE_USUARIO.toString());
		User user = buildUserForAuthentication(usuario, authorityList);
		return user;
	}
	private User buildUserForAuthentication(Usuario usuario, List<GrantedAuthority> authorities) {
		return new UsuarioDetalhes(usuario, true, true, true, true, authorities);
	}

}