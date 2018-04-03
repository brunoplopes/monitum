package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.Turma;
import br.com.monitum.entity.TurmaAluno;
import br.com.monitum.entity.Usuario;

@Repository
public interface TurmaAlunoRepository extends CrudRepository<TurmaAluno, Long>{
	List<TurmaAluno> findByAluno(Aluno aluno);
	List<TurmaAluno> findByTurma(Turma turma);
	@Query("select ta from TurmaAluno ta join ta.turma t join ta.aluno al where t.id = ? order by al.nome asc")
	List<TurmaAluno> findByIdTurmaOrderByNomeAsc(long idTurma);
	TurmaAluno findByTurmaAndAluno(Turma turma, Aluno aluno);
	List<TurmaAluno> findByTurmaIn(List<Turma> Turmas);
	@Query(value="select al from TurmaAluno ta JOIN ta.turma t join ta.aluno al where t.id = ?1 and al.id = ?2")
	Aluno findByIdTurmaAndIdAluno(long idTurma, long idAluno);
	@Query(value="select t from TurmaAluno ta JOIN ta.turma t join ta.aluno al where t.id = ?1 and al.id = ?2")
	Turma findByIdAlunoAndIdTurma(long idTurma, long idAluno);
	@Query("select ta from TurmaAluno ta where ta.turma = ?1 and ta.aluno not in ?2")
    List<TurmaAluno> findByTurmaAlunoNotIn(Turma turma, List<Aluno> aluno);
	@Query("select u from TurmaAluno ta join ta.turma t join ta.aluno al join al.usuario u where t.id = ?1")
	List<Usuario> findByIdTurma(long idTurma);
	@Query("select t from TurmaAluno ta join ta.turma t join ta.aluno al where t.turmaAtiva = 1 and  al.id = ?1")
	List<Turma> findByIdAluno(long idAluno);
}
