package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Atividade;
import br.com.monitum.entity.Turma;

@Repository
public interface AtividadeRepository extends CrudRepository<Atividade, Long>{
	
	List<Atividade> findByTurma(Turma turma);
	@Query(value="select a from Atividade a JOIN a.turma t join t.professor p where a.id = ?1 and p.id = ?2")
	Atividade findByIdAtividadeAndIdProfessor(long idAtividade, long idProfessor);
	@Query(value="select atv.* from TurmaAluno ta join Turma t join Aluno al join Atividade atv join Grupo g where ta.id_turma = t.id and ta.id_aluno = al.id and g.id_atividade = atv.id and atv.id_turma = t.id and g.id = ?1 and al.id = ?2", nativeQuery = true)
	Atividade findByIdGrupoIdAluno(long idGrupo, long idAluno);
	@Query("select atv from Grupo g join g.atividade atv join atv.turma t where t.turmaAtiva = 1 and t.id = ?1 group by atv.titulo")
	List<Atividade> findAtividadeGrupoByIdTurma(long idTurma);
}
