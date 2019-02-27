package br.com.houseController.model.despesas;

import java.time.LocalDateTime;

import br.com.houseController.model.Abstracts.AbstractDespesa;

public class DespesaVariavel extends AbstractDespesa{
	
	private LocalDateTime dtPagamento;

	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	
	
}
