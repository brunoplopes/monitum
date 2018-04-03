package br.com.monitum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Disciplina;

@Repository
public interface DisciplinaRepository extends CrudRepository<Disciplina, Long>{

}
