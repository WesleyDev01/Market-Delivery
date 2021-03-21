package br.edu.ifrn.marketdelivery.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/usuarios").permitAll()
		.antMatchers("/usuarios/cadastrarUsuario").permitAll()
		.antMatchers(HttpMethod.GET, "/usuarios/listaCompras").hasRole("CLIENTE")
		.antMatchers(HttpMethod.GET, "/listaCompras/{idCompra}").hasRole("CLIENTE")
		.antMatchers("/usuarios/listaUsuarios").hasRole("ADMINISTRADOR")
		.antMatchers(HttpMethod.GET, "/listaUsuarios/{idUsuario}").hasRole("ADMINISTRADOR")
		.antMatchers("/comercios").permitAll()
		.antMatchers(HttpMethod.POST, "/comercios").hasRole("PROPRIETARIO")
		.antMatchers(HttpMethod.POST,"/comercios/cadastrarComercio").hasRole("PROPRIETARIO")
		.antMatchers(HttpMethod.GET, "/comercios/{idComercio}/produtos/{idProduto}/selecionar").hasRole("PROPRIETARIO")
		.antMatchers(HttpMethod.GET, "/comercios/{idComercio}/produtos/{idProduto}/adicionar").hasRole("CLIENTE")
		.antMatchers(HttpMethod.POST, "/comercios/{idComercio}/produtos/{idProduto}/adicionar").hasRole("CLIENTE")
		.antMatchers(HttpMethod.GET,"/comercios/cadastrarComercio").hasRole("PROPRIETARIO")
		.antMatchers(HttpMethod.GET, "comercios/{idComercio}/remover").hasRole("ADMINISTRADOR")
		.antMatchers(HttpMethod.GET, "comercios/{idComercio}/produtos/{idProduto}/remover").hasRole("PROPRIETARIO")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		;
	}
	
}
