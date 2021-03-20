package br.edu.ifrn.marketdelivery.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifrn.marketdelivery.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByLogin(String login);

}
