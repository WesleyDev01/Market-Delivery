package br.edu.ifrn.marketdelivery.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "comercio")
public class Comercio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private int cpfUsuario;
	@NotBlank
	private int taxaEntrega;
	@NotBlank
	private String nome;
	@NotBlank
	private String email;
	@NotBlank
	private String endereco;
	@NotBlank
	private String telefone;

	public Long getId() {
		return id;
	}

	public int getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(int cpfUsuario) {
		this.cpfUsuario = cpfUsuario;
	}

	public int getTaxaEntrega() {
		return taxaEntrega;
	}

	public void setTaxaEntrega(int taxaEntrega) {
		this.taxaEntrega = taxaEntrega;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
