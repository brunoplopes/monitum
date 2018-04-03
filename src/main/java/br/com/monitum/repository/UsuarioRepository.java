package br.com.monitum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
	Usuario findByNome(String nome);
	Usuario findByEmail(String email);
	Usuario findByToken(String token);
	Usuario findBySenha(String senha);
	Usuario findByFoto(String tokenFoto);
	Usuario findByEmailAndIdNot(String email, long id);
}
