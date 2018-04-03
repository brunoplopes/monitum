package br.com.monitum.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.monitum.entity.TipoConteudo;

@Repository
public interface TipoConteudoRepository extends CrudRepository<TipoConteudo, Long>{

}
