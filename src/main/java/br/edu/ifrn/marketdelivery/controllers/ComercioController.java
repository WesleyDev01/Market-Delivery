package br.edu.ifrn.marketdelivery.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifrn.marketdelivery.models.Comercio;
import br.edu.ifrn.marketdelivery.models.Produto;
import br.edu.ifrn.marketdelivery.repositories.ComercioRepository;
import br.edu.ifrn.marketdelivery.repositories.ProdutoRepository;

@Controller
@RequestMapping("/comercios")
public class ComercioController {

	@Autowired
	ComercioRepository cr;
	@Autowired
	ProdutoRepository pr;

	@GetMapping("/cadastrarComercio")
	public String formMarket(Comercio comercio) {
		return "comercios/formComercio";
	}

	@PostMapping
	public String salvar(Comercio comercio) {
		System.out.println(comercio);
		cr.save(comercio);
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
	public ModelAndView visualizarComercio(@PathVariable Long id) {
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
	public ModelAndView adicionarProduto(@PathVariable Long idComercio, Produto produto) {
		ModelAndView md = new ModelAndView();
		Optional<Comercio> opt = cr.findById(idComercio);

		if (opt.isEmpty()) {
			md.setViewName("redirect:/comercios");
			return md;
		}

		Comercio comercio = opt.get();
		produto.setComercio(comercio);

		pr.save(produto);

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

	@GetMapping("/{idComercio}/remover")
	public String removerComercio(@PathVariable Long idComercio) {
		Optional<Comercio> opt = cr.findById(idComercio);
		if (!opt.isEmpty()) {
			Comercio comercio = opt.get();
			List<Produto> produtos = pr.findByComercio(comercio);
			pr.deleteAll(produtos);
			cr.delete(comercio);
		}
		return "redirect:/comercios";
	}

	@GetMapping("/{idComercio}/produto/{idProduto}/remover")
	public String removerProduto(@PathVariable Long idComercio, @PathVariable Long idProduto) {
		Optional<Comercio> opt = cr.findById(idComercio);
		Comercio comercio = opt.get();
		List<Produto> produtos = pr.findByComercio(comercio);
		if (!produtos.isEmpty()) {
			Optional<Produto> produto = pr.findById(idProduto);
			if (!produto.isEmpty()) {
				pr.deleteById(idProduto);
			}
			return "redirect:/comercios/{idComercio}";
		}
		return "redirect:/comercios/{idComercio}";
	}

}
