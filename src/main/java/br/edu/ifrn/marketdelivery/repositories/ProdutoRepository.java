package br.edu.ifrn.marketdelivery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.marketdelivery.models.Comercio;
import br.edu.ifrn.marketdelivery.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByComercio(Comercio comercio);
	
	List<Produto> findByComercioId (Long comercioId);
	
}
