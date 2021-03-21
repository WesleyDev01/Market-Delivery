package br.edu.ifrn.marketdelivery.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifrn.marketdelivery.models.Comercio;
import br.edu.ifrn.marketdelivery.models.PacoteDeCompra;
import br.edu.ifrn.marketdelivery.models.Papel;
import br.edu.ifrn.marketdelivery.models.Usuario;
import br.edu.ifrn.marketdelivery.repositories.ComercioRepository;
import br.edu.ifrn.marketdelivery.repositories.PacoteDeCompraRepository;
import br.edu.ifrn.marketdelivery.repositories.PapelRepository;
import br.edu.ifrn.marketdelivery.repositories.UsuarioRepository;

@Controller
@RequestMapping("/usuarios")
public class UsarioController {

	private Usuario usuario;

	@Autowired
	private UsuarioRepository ur;
	@Autowired
	private PapelRepository papr;
	@Autowired
	private PacoteDeCompraRepository pacr;
	@Autowired
	private ComercioRepository cr;

	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = ur.findByLogin(login);
		}
	}

	@GetMapping("/cadastrarUsuario")
	public String formUser(Usuario usuario) {
		return "usuarios/formUsuario";
	}

	@PostMapping
	public String cadastrarUsuario(@Valid Usuario usuario, Papel papel, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return formUser(usuario);
		}

		String senhaUsuario = usuario.getSenha();
		usuario.setSenha(new BCryptPasswordEncoder().encode(senhaUsuario));

		ur.save(usuario);

		System.out.println(usuario);

		Long usuario_id = usuario.getId();
		Long papeis_id = (long) usuario.getTipo();

		papr.inserirPapel(usuario_id, papeis_id);

		attributes.addFlashAttribute("mensagem", "Usuário cadastrado com sucesso!");
		return "redirect:/comercios";
	}

	@GetMapping("/listaUsuarios")
	public ModelAndView listarUsuarios() {
		List<Usuario> usuarios = ur.findAll();
		ModelAndView mv = new ModelAndView("usuarios/listaUsuarios");
		mv.addObject("usuarios", usuarios);
		return mv;
	}
	
	@GetMapping("/listaUsuarios/{idUsuario}")
	public String removerUsuario(@PathVariable Long idUsuario, RedirectAttributes attributes) {
		buscarUsuarioLogado();
		Optional<Usuario> opt = ur.findById(idUsuario);
		if (!opt.isEmpty()) {
			Usuario usuario = opt.get();
			String cpfUsuario = usuario.getCpf();
			List<PacoteDeCompra> compras = pacr.findByUsuario(usuario);
			List<Comercio> comercios = cr.findByCpfUsuario(cpfUsuario);
			pacr.deleteAll(compras);
			cr.deleteAll(comercios);
			ur.delete(usuario);
			attributes.addFlashAttribute("mensagem", "Usuário removido!");
		}
		return "redirect:/usuarios/listaUsuarios";
	}

	@GetMapping("/listaCompras")
	public ModelAndView listarCompras() {
		buscarUsuarioLogado();
		// List<PacoteDeCompra> pacotes = pacr.findAll();
		List<PacoteDeCompra> pacotes = pacr.findByUsuario(usuario);
		ModelAndView mv = new ModelAndView("usuarios/listaCompras");
		mv.addObject("pacotes", pacotes);
		return mv;
	}

	@GetMapping("/listaCompras/{idCompra}")
	public String removerCompra(@PathVariable Long idCompra, RedirectAttributes attributes) {
		Optional<PacoteDeCompra> opt = pacr.findById(idCompra);
		if (!opt.isEmpty()) {
			PacoteDeCompra pacote = opt.get();
			pacr.delete(pacote);
			attributes.addFlashAttribute("mensagem", "Compra cancelada com sucesso!");
		}
		return "redirect:/usuarios/listaCompras";
	}

}
