package br.edu.ifrn.marketdelivery.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comercio", uniqueConstraints = { @UniqueConstraint(columnNames = { "email", "telefone" }) })
public class Comercio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpfUsuario;
	@NotNull
	private int taxaEntrega;
	@NotBlank
	private String nome;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String endereco;
	@NotBlank
	private String telefone;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getCpfUsuario() {
		return cpfUsuario;
	}

	public void setCpfUsuario(String cpfUsuario) {
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

	@Override
	public String toString() {
		return "Comercio [id=" + id + ", cpfUsuario=" + cpfUsuario + ", taxaEntrega=" + taxaEntrega + ", nome=" + nome
				+ ", email=" + email + ", endereco=" + endereco + ", telefone=" + telefone + "]";
	}

}
