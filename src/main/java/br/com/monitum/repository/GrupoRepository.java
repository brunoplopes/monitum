package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Atividade;
import br.com.monitum.entity.Grupo;

@Repository
public interface GrupoRepository extends CrudRepository<Grupo, Long>{
	List<Grupo> findByAtividade(Atividade atividade);
	List<Grupo> findByAtividadeIn(List<Atividade> atividades);
}
