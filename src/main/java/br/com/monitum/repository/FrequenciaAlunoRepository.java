package br.com.monitum.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Frequencia;
import br.com.monitum.entity.FrequenciaAluno;

@Repository
public interface FrequenciaAlunoRepository extends CrudRepository<FrequenciaAluno, Long>{
	FrequenciaAluno findByFrequenciaAndAluno(Frequencia frequencia, Aluno aluno);
	@Query("select sum(fa.faltas) from FrequenciaAluno fa join fa.frequencia f join f.turma t join fa.aluno a where a.id =?1 and t.id = ?2")
	Integer findByIdAlunoAndIdTurma(long idAluno, long idTurma);
	
}
