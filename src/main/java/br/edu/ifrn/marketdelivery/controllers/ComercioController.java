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
import br.edu.ifrn.marketdelivery.repositories.PacoteDeCompraRepository;
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
	@Autowired
	private PacoteDeCompraRepository pacr;

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
		buscarUsuarioLogado();
		if (result.hasErrors()) {
			return formMarket(comercio);
		}
		String cpfUsuario = usuario.getCpf();
		comercio.setCpfUsuario(cpfUsuario);
		System.out.println(comercio);
		cr.save(comercio);
		attributes.addFlashAttribute("mensagem", "Comércio cadastrado com sucesso!");
		return "redirect:/comercios";
	}

	@GetMapping
	public ModelAndView listarComercios() {
		buscarUsuarioLogado();
		List<Comercio> comercios = cr.findAll();
		ModelAndView mv = new ModelAndView("home");
		mv.addObject("usuario", usuario);
		mv.addObject("comercios", comercios);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView visualizarComercio(@PathVariable Long id, Produto produto) {
		buscarUsuarioLogado();
		ModelAndView md = new ModelAndView();
		Optional<Comercio> opt = cr.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		md.setViewName("comercios/produtosComercio");
		Comercio comercio = opt.get();

		md.addObject("comercio", comercio);
		md.addObject("usuario", usuario);

		List<Produto> produtos = pr.findByComercio(comercio);
		md.addObject("produtos", produtos);

		return md;

	}

	@PostMapping("/{codigoComercio}")
	public ModelAndView adicionarProduto(@PathVariable Long codigoComercio, @Valid Produto produto,
		BindingResult result, RedirectAttributes attributes) {
		ModelAndView md = new ModelAndView();
		Optional<Comercio> opt = cr.findById(codigoComercio);

		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		if (result.hasErrors()) {
			return visualizarComercio(codigoComercio, produto);
		}

		Long idDoProduto = produto.getId();
		Comercio comercio = opt.get();
		produto.setComercio(comercio);

		System.out.println("Id do produto: " + produto.getId());

		attributes.addFlashAttribute("mensagem", "Produto adicionado com sucesso!");
		produto.setId(idDoProduto);
		pr.save(produto);

		System.out.println("Id novo do produto: " + produto.getId());

		md.addObject("produto", produto);
		md.setViewName("redirect:/comercios/{codigoComercio}");

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
		buscarUsuarioLogado();
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
		md.addObject("usuario", usuario);
		md.addObject("produtos", pr.findByComercio(comercio));

		return md;

	}

	@GetMapping("/{idComercio}/produtos/{idProduto}/adicionar")
	public ModelAndView comprarProduto(@PathVariable Long idComercio, @PathVariable Long idProduto) {
		ModelAndView md = new ModelAndView();
		buscarUsuarioLogado();
		
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

		md.setViewName("comercios/comprarProduto");
		md.addObject("produto", produto);
		md.addObject("comercio", comercio);
		md.addObject("usuario", usuario);
		md.addObject("produtos", pr.findByComercio(comercio));

		// System.out.println(produto.getId());
		// System.out.println(usuario.getId());
		// System.out.println(pacotedecompra.isPagamento());

		return md;

	}

	@PostMapping("/{idComercio}/produtos/{idProduto}/adicionar")
	public ModelAndView confirmarCompraProduto(@PathVariable Long idComercio, @PathVariable Long idProduto) {
		buscarUsuarioLogado();
		ModelAndView md = new ModelAndView();
		PacoteDeCompra pacote = new PacoteDeCompra();
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

		md.setViewName("redirect:/comercios/{idComercio}");
		md.addObject("produto", produto);
		md.addObject("comercio", comercio);
		md.addObject("usuario", usuario);
		md.addObject("produtos", pr.findByComercio(comercio));
		
		pacote.setPagamento(false);
		pacote.setProduto(produto);
		pacote.setUsuario(usuario);
		
		pacr.save(pacote);

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
	public String removerProduto(@PathVariable Long idComercio, @PathVariable Long idProduto,
		RedirectAttributes attributes) {
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
