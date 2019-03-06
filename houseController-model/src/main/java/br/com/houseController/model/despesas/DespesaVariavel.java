package br.com.houseController.model.despesas;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

import br.com.houseController.model.Abstracts.AbstractDespesa;

@Entity(name="despesaVariavel")
@Table(name="despesa_variavel")
public class DespesaVariavel extends AbstractDespesa{
	
	@Column
	private LocalDateTime dtPagamento;

	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	
	
}
