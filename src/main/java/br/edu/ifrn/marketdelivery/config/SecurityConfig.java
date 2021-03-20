package br.edu.ifrn.marketdelivery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.edu.ifrn.marketdelivery.services.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService detailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(detailsService).passwordEncoder(new BCryptPasswordEncoder());

	}

	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
