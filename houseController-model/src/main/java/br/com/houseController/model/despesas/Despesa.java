package br.com.houseController.model.despesas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

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
	
	
	
	public Despesa(Boolean pago, Categoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada,LocalDate dtPagamento) {
		super();
		
		this.dtPagamento = dtPagamento;
	}

	

	@Column
	private LocalDate dtPagamento;
	
	@Column
	private LocalDate dtVencimento;
	
	@Column
	private Boolean isCompras;


	public LocalDate getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDate dtPagamento) {
		this.dtPagamento = dtPagamento;
	}	
	public LocalDate getDtVencimento() {
		return dtVencimento;
	}
	
	public void setDtVencimento(LocalDate dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
}
