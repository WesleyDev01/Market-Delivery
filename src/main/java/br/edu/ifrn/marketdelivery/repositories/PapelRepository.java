package br.edu.ifrn.marketdelivery.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.ifrn.marketdelivery.models.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {

	@Transactional
	@Modifying
	@Query(value = "insert into usuario_papeis (usuario_id, papeis_id) values (:usuario_id, :papeis_id)", nativeQuery = true)
	void inserirPapel(@Param("usuario_id") Long usuario_id, @Param("papeis_id") Long papeis_id);

}
