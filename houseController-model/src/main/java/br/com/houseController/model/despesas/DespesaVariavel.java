package br.com.houseController.model.despesas;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.houseController.model.Abstracts.AbstractDespesa;

@Entity(name="despesaVariavel")
@Table(name="despesa_variavel")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DespesaVariavel extends AbstractDespesa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private LocalDateTime dtPagamento;

	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}	
	
}
