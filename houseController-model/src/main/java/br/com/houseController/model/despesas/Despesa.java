package br.com.houseController.model.despesas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import br.com.houseController.model.Abstracts.AbstractDespesa;
import br.com.houseController.model.Enums.EnumCategoria;
import br.com.houseController.model.Enums.EnumContaAtiva;
import br.com.houseController.model.categoria.Categoria;
import br.com.houseController.model.receita.Receita;

@Entity(name="despesa")
@Table(name="despesa")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Despesa extends AbstractDespesa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public Despesa() {
		super();
	}

	public Despesa(Integer id, Boolean pago, EnumCategoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada) {
		super(id, pago, categoria, contaAtiva, descricaoDespesa, valorDespesa, receitaUtilizada);
	}
	
	
	
	public Despesa(Boolean pago, Categoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada,LocalDateTime dtPagamento) {
		super();
		
		this.dtPagamento = dtPagamento;
	}



	@Column
	private LocalDateTime dtPagamento;
	
	@Column
	private LocalDateTime dtVencimento;


	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}	
	public LocalDateTime getDtVencimento() {
		return dtVencimento;
	}
	
	public void setDtVencimento(LocalDateTime dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
}
