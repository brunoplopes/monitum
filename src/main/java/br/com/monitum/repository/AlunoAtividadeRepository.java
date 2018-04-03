package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.monitum.entity.Aluno;
import br.com.monitum.entity.AlunoAtividade;
import br.com.monitum.entity.Atividade;

public interface AlunoAtividadeRepository extends CrudRepository<AlunoAtividade, Long>{
	List<AlunoAtividade> findByAluno(Aluno aluno);
	List<AlunoAtividade> findByAtividade(Atividade atividade);
	AlunoAtividade findByAtividadeAndAluno(Atividade atividade, Aluno aluno);
}
