package br.com.monitum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.Conteudo;
import br.com.monitum.entity.Turma;
@Repository
public interface ConteudoRepository extends CrudRepository<Conteudo, Long>{
	List<Conteudo> findByTurma(Turma turma);
	@Query( "select c from Conteudo c join c.turma t join c.tipoConteudo tc where tc.id = ?1 and t in ?2 order by c.id desc" )
	List<Conteudo> findByTipoConteudoAndTurmasIn(Long idTipoConteudo, List<Turma> turmas);
}
