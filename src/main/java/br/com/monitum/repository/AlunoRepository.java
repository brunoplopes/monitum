package br.com.monitum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Usuario;
@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long>{
	Aluno findByProntuario(String prontuario);
	Aluno findByUsuario(Usuario usuario);
}
