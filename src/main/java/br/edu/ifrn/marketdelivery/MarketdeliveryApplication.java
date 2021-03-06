package br.edu.ifrn.marketdelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MarketdeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MarketdeliveryApplication.class, args);
		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
