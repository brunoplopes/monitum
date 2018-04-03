package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Frequencia;

@Repository
public interface FrequenciaRepository extends CrudRepository<Frequencia, Long>{
	@Query("select f from Frequencia f join f.turma t join t.professor p where t.id = ?1 and p.id = ?2 order by data_aula desc")
	List<Frequencia> findByIdTurmaAndIdProfessorOrderByData(long idTurma, long idProfessor);
	@Query("select f from Frequencia f join f.turma t join t.professor p where f.id = ?1 and p.id = ?2")
	Frequencia findByIdFrequenciaAndIdProfessor(long idFrequencia, long idProfessor);
}
