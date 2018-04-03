package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Grupo;
import br.com.monitum.entity.GrupoAluno;

@Repository
public interface GrupoAlunoRepository extends CrudRepository<GrupoAluno, Long>{
	GrupoAluno findByAlunoAndGrupo(Aluno aluno, Grupo grupo);
	@Query("select g from GrupoAluno ga join ga.aluno al join ga.grupo g join g.atividade at where al.id = ?1 and at.id =?2")
	Grupo findByIdAlunoAndIdAtividade(long idAluno, long idAtividade);
	@Query("Select g from GrupoAluno ga join ga.aluno a join ga.grupo g join g.atividade at join at.turma tu where a.id = ?1 and tu.id = ?2")
	List<Grupo> findByIdAlunoAndIdTurma(long idAluno, long idTurma);
	@Query("Select ga from GrupoAluno ga join ga.grupo g join g.atividade atv join atv.turma tu join tu.professor p where g.id = ?1 and p.id = ?2")
	List<GrupoAluno> findByIdGrupoAndIdProfessor(long idGrupo, long idProfessor);
	@Query("Select ga from GrupoAluno ga join ga.grupo g join g.atividade atv join atv.turma tu join tu.professor p where ga.id = ?1 and p.id = ?2")
	GrupoAluno findByIdGrupoAlunoAndIdProfessor(long idGrupoAluno, long idProfessor);
	GrupoAluno findByGrupo(Grupo grupo);
	@Query("select ga from GrupoAluno ga join ga.grupo g join g.atividade atv where atv.id = ?1")
	List<GrupoAluno> findByIdAtividade(long idAtividade);
}
