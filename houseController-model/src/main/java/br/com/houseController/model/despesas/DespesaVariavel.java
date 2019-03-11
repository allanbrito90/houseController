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
import br.com.houseController.model.Enums.EnumContaAtiva;
import br.com.houseController.model.categoria.Categoria;
import br.com.houseController.model.receita.Receita;

@Entity(name="despesaVariavel")
@Table(name="despesa_variavel")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class DespesaVariavel extends AbstractDespesa implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public DespesaVariavel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DespesaVariavel(Integer id, Boolean pago, Categoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada) {
		super(id, pago, categoria, contaAtiva, descricaoDespesa, valorDespesa, receitaUtilizada);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public DespesaVariavel(Boolean pago, Categoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada,LocalDateTime dtPagamento) {
		super();
		
		this.dtPagamento = dtPagamento;
	}



	@Column
	private LocalDateTime dtPagamento;

	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}	
	
}
