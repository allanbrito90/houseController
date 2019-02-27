package br.com.houseController.model.receita;

import java.time.LocalDateTime;

public class Receita {
	
	int id;
	LocalDateTime dtPagamento;
	String descricaoPagamento;
	
	public Receita(int id, LocalDateTime dtPagamento, String descricaoPagamento) {
		super();
		this.id = id;
		this.dtPagamento = dtPagamento;
		this.descricaoPagamento = descricaoPagamento;
	}
	
	public Receita(LocalDateTime dtPagamento, String descricaoPagamento) {
		super();
		this.dtPagamento = dtPagamento;
		this.descricaoPagamento = descricaoPagamento;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getDescricaoPagamento() {
		return descricaoPagamento;
	}
	public void setDescricaoPagamento(String descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}
	
	
}
