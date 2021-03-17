package br.edu.ifrn.marketdelivery.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifrn.marketdelivery.models.Comercio;
import br.edu.ifrn.marketdelivery.repositories.ComercioRepository;

@Controller
@RequestMapping("/comercios")
public class ComercioController {

	@Autowired
	ComercioRepository cr;

	@GetMapping("/cadastrarComercio")
	public String formMarket(Comercio comercio) {
		return "comercios/formComercio";
	}

	@PostMapping
	public String cadastrarComerio(Comercio comercio) {
		System.out.println(comercio);
		cr.save(comercio);
		return "home";
	}

}
