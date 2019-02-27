package br.com.houseController.model.despesas;

import java.time.LocalDateTime;

import br.com.houseController.model.produto.Produto;

public class Compras extends DespesaVariavel {
	
	private LocalDateTime periodoReferencia;
	private Produto produtos;
	
	public LocalDateTime getPeriodoReferencia() {
		return periodoReferencia;
	}
	public void setPeriodoReferencia(LocalDateTime periodoReferencia) {
		this.periodoReferencia = periodoReferencia;
	}
	public Produto getProdutos() {
		return produtos;
	}
	public void setProdutos(Produto produtos) {
		this.produtos = produtos;
	}
	
	
	
}
