package br.com.monitum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Professor;
import br.com.monitum.entity.Usuario;
@Repository
public interface ProfessorRepository extends CrudRepository<Professor, Long>{
	Professor findByRegistroProfessor(String registroProfessor);
	Professor findByRegistroProfessorAndEmail(String registroProfessor, String email);
	Professor findByUsuario(Usuario usuario);
}
