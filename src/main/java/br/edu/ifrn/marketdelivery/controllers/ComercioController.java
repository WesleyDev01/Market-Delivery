package br.edu.ifrn.marketdelivery.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifrn.marketdelivery.models.Comercio;
import br.edu.ifrn.marketdelivery.repositories.ComercioRepository;

@Controller
@RequestMapping("/comercios")
public class ComercioController {

	ComercioRepository cr;

	@PostMapping
	public String cadastrarComerio(Comercio comercio) {
		cr.save(comercio);
		return "redirect:/comercios";
	}

}
