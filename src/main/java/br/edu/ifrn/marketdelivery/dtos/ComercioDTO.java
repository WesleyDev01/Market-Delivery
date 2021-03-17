package br.edu.ifrn.marketdelivery.dtos;

public class ComercioDTO {

	private Long id;
	private int cpfUsuario;
	private int taxaEntrega;
	private String nome;
	private String email;
	private String endereco;
	private String telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
