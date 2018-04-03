package br.com.monitum.repository;

import java.util.List;

import br.com.monitum.entity.Mobile;
import br.com.monitum.entity.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MobileRepository extends CrudRepository<Mobile, Long>{
	Mobile findByAccessToken(String token);
	List<Mobile> findByUsuarioIn(List<Usuario> usuarios);
	Mobile findByGcmToken(String gcmTokem);
}
