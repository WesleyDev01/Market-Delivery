package br.edu.ifrn.marketdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.marketdelivery.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
