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
	public String cadastrarComerio(Comercio comercio) {
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

}
