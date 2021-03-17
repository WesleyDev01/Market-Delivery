package br.edu.ifrn.marketdelivery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifrn.marketdelivery.models.Usuario;
import br.edu.ifrn.marketdelivery.repositories.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsarioController {

	UsuarioRepository ur;

	@PostMapping
	public String cadastrarUsuario(Usuario usuario) {
		ur.save(usuario);
		return "redirect:/usuarios";
	}

}
