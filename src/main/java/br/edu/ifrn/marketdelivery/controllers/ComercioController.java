package br.edu.ifrn.marketdelivery.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import br.edu.ifrn.marketdelivery.models.Produto;
import br.edu.ifrn.marketdelivery.models.Usuario;
import br.edu.ifrn.marketdelivery.repositories.ComercioRepository;
import br.edu.ifrn.marketdelivery.repositories.ProdutoRepository;
import br.edu.ifrn.marketdelivery.repositories.UsuarioRepository;

@Controller
@RequestMapping("/comercios")
public class ComercioController {

	private Usuario usuario;
	private PacoteDeCompra pacotedecompra;
	
	@Autowired
	ComercioRepository cr;
	@Autowired
	ProdutoRepository pr;
	@Autowired
	private UsuarioRepository ur;

	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String login = autenticado.getName();
			usuario = ur.findByLogin(login);
		}
	}
	
	@GetMapping("/cadastrarComercio")
	public String formMarket(Comercio comercio) {
		return "comercios/formComercio";
	}

	@PostMapping
	public String salvar(@Valid Comercio comercio, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return formMarket(comercio);
		}

		System.out.println(comercio);
		attributes.addFlashAttribute("mensagem", "Comércio cadastrado com sucesso!");
		return "redirect:/comercios";
	}

	@GetMapping
	public ModelAndView listarComercios() {
		List<Comercio> comercios = cr.findAll();
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("comercios", comercios);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView visualizarComercio(@PathVariable Long id, Produto produto) {
		ModelAndView md = new ModelAndView();
		Optional<Comercio> opt = cr.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		md.setViewName("comercios/produtosComercio");
		Comercio comercio = opt.get();

		md.addObject("comercio", comercio);

		List<Produto> produtos = pr.findByComercio(comercio);
		md.addObject("produtos", produtos);

		return md;

	}

	@PostMapping("/{idComercio}")
	public ModelAndView adicionarProduto(@PathVariable Long idComercio, @Valid Produto produto, BindingResult result, RedirectAttributes attributes) {
		ModelAndView md = new ModelAndView();
		Optional<Comercio> opt = cr.findById(idComercio);

		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		if (result.hasErrors()) {
			return visualizarComercio(idComercio, produto);
		}

		Comercio comercio = opt.get();
		produto.setComercio(comercio);

		pr.save(produto);
		attributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso!");

		md.setViewName("redirect:/comercios/{idComercio}");

		return md;
	}

	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarComercio(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Comercio> opt = cr.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}
		Comercio comercio = opt.get();
		md.setViewName("comercios/formComercio");
		md.addObject("comercio", comercio);
		return md;
	}

	@GetMapping("/{idComercio}/produtos/{idProduto}/selecionar")
	public ModelAndView selecionarProduto(@PathVariable Long idComercio, @PathVariable Long idProduto) {
		ModelAndView md = new ModelAndView();
		Optional<Comercio> optComercio = cr.findById(idComercio);
		Optional<Produto> optProduto = pr.findById(idProduto);

		if (optComercio.isEmpty() || optProduto.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		Comercio comercio = optComercio.get();
		Produto produto = optProduto.get();

		if (comercio.getId() != produto.getComercio().getId()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		md.setViewName("comercios/produtosComercio");
		md.addObject("produto", produto);
		md.addObject("comercio", comercio);
		md.addObject("produtos", pr.findByComercio(comercio));

		return md;

	}

	@GetMapping("/{idComercio}/remover")
	public String removerComercio(@PathVariable Long idComercio, RedirectAttributes attributes) {
		Optional<Comercio> opt = cr.findById(idComercio);
		if (!opt.isEmpty()) {
			Comercio comercio = opt.get();
			List<Produto> produtos = pr.findByComercio(comercio);
			pr.deleteAll(produtos);
			cr.delete(comercio);
			attributes.addFlashAttribute("mensagem", "Comércio removido com sucesso!");
		}
		return "redirect:/comercios";
	}

	@GetMapping("/{idComercio}/produtos/{idProduto}/remover")
	public String removerProduto(@PathVariable Long idComercio, @PathVariable Long idProduto, RedirectAttributes attributes) {
		Optional<Comercio> opt = cr.findById(idComercio);
		Comercio comercio = opt.get();
		List<Produto> produtos = pr.findByComercio(comercio);
		if (!produtos.isEmpty()) {
			Optional<Produto> produto = pr.findById(idProduto);
			if (!produto.isEmpty()) {
				pr.deleteById(idProduto);
				attributes.addFlashAttribute("mensagem", "Produto removido com sucesso!");
			}
			return "redirect:/comercios/{idComercio}";
		}
		return "redirect:/comercios/{idComercio}";
	}

}
