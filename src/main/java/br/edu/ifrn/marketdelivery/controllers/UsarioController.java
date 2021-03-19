package br.edu.ifrn.marketdelivery.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.marketdelivery.models.Usuario;
import br.edu.ifrn.marketdelivery.repositories.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsarioController {

	@Autowired
	private UsuarioRepository ur;

	@GetMapping("/cadastrarUsuario")
	public String formUser(Usuario usuario) {
		return "usuarios/formUsuario";
	}

	@PostMapping
	public String cadastrarUsuario(@Valid Usuario usuario, BindingResult result) {

		if (result.hasErrors()) {
			return formUser(usuario);
		}

		System.out.println(usuario);
		ur.save(usuario);
		return "redirect:/comercios";
	}

	@GetMapping("/listaUsuarios")
	public ModelAndView listarUsuarios() {
		List<Usuario> usuarios = ur.findAll();
		ModelAndView mv = new ModelAndView("usuarios/listaUsuarios");
		mv.addObject("usuarios", usuarios);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView visualizarPerfil(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Usuario> opt = ur.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		md.setViewName("usuarios/perfilUsuario");
		Usuario usuario = opt.get();

		md.addObject("usuario", usuario);

		return md;

	}

}
