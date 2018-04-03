package br.com.monitum.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioContext {
	public static UsuarioDetalhes getUsuarioLogado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = null;
		if (authentication != null) {
			principal = authentication.getPrincipal();
		}
		return principal instanceof UsuarioDetalhes ? (UsuarioDetalhes) principal : null;
	}
}
