package br.edu.ifrn.marketdelivery.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.ifrn.marketdelivery.models.Usuario;
import br.edu.ifrn.marketdelivery.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository ur;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = ur.findByLogin(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não encontrado!");
		}

		return usuario;
	}

}
