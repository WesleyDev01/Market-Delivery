package br.edu.ifrn.marketdelivery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.marketdelivery.models.Comercio;

public interface ComercioRepository extends JpaRepository<Comercio, Long> {

	List<Comercio> findByCpfUsuario (String cpfUsuario);
	
}
