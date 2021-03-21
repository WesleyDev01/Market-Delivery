package br.edu.ifrn.marketdelivery.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.marketdelivery.models.PacoteDeCompra;
import br.edu.ifrn.marketdelivery.models.Usuario;

public interface PacoteDeCompraRepository extends JpaRepository<PacoteDeCompra, Long> {

	List<PacoteDeCompra> findByUsuario(Usuario usuario);

}
