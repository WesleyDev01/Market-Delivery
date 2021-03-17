package br.edu.ifrn.marketdelivery.dtos;

import java.time.LocalDate;
import java.time.LocalTime;

public class PacoteDeCompraDTO {
	private Long id;
	private int cpfUsuario;
	private int idProduto;
	private int idComercio;
	private int quantidadeProduto;
	private double precoTotal;
	private LocalTime horaEntrega;
	private LocalDate dataEntrega;
	private boolean pagamento;
	private boolean entrega;

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

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public int getIdComercio() {
		return idComercio;
	}

	public void setIdComercio(int idComercio) {
		this.idComercio = idComercio;
	}

	public int getQuantidadeProduto() {
		return quantidadeProduto;
	}

	public void setQuantidadeProduto(int quantidadeProduto) {
		this.quantidadeProduto = quantidadeProduto;
	}

	public double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public LocalTime getHoraEntrega() {
		return horaEntrega;
	}

	public void setHoraEntrega(LocalTime horaEntrega) {
		this.horaEntrega = horaEntrega;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public boolean isPagamento() {
		return pagamento;
	}

	public void setPagamento(boolean pagamento) {
		this.pagamento = pagamento;
	}

	public boolean isEntrega() {
		return entrega;
	}

	public void setEntrega(boolean entrega) {
		this.entrega = entrega;
	}

}
