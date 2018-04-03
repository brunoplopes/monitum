package br.com.monitum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Curso;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long>{

}
