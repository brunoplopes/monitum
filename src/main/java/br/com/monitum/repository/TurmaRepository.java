package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Professor;
import br.com.monitum.entity.Turma;

@Repository
public interface TurmaRepository  extends CrudRepository<Turma, Long>{
	@Query("select t from Turma t join t.professor p where t.turmaAtiva = 1 and  p = ?1")
	List<Turma> findByProfessor(Professor professor);
	Turma findByIdAndProfessor(long id, Professor professor);
	@Query(value="select t from TurmaAluno ta JOIN ta.turma t join ta.aluno a where t.id = ?1 and a.id = ?2")
	Turma findByIdTurmaAndIdAluno(long idTurma, long idAluno);
	@Query("select t from Turma t join t.professor p where t.turmaAtiva != 1 and  p = ?1")
	List<Turma> findByProfessorDesativada(Professor professor);
	@Query("select t from Turma t join t.professor p where t.dataTurmaFinalizada != null and  p = ?1")
	List<Turma> findByProfessorFinalizada(Professor professor);
}
