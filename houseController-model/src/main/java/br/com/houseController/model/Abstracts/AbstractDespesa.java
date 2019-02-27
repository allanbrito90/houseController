package br.com.houseController.model.Abstracts;

import java.math.BigDecimal;

import br.com.houseController.model.Enums.EnumContaAtiva;
import br.com.houseController.model.categoria.Categoria;
import br.com.houseController.model.receita.Receita;

public abstract class AbstractDespesa {
	
	Integer id;
	Boolean pago;
	Categoria categoria;
	EnumContaAtiva contaAtiva;
	String descricaoDespesa;
	BigDecimal valorDespesa;
	Receita receitaUtilizada;
	
	
	
	public AbstractDespesa() {
	}



	public AbstractDespesa(Integer id, Boolean pago, Categoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada) {
		super();
		this.id = id;
		this.pago = pago;
		this.categoria = categoria;
		this.contaAtiva = contaAtiva;
		this.descricaoDespesa = descricaoDespesa;
		this.valorDespesa = valorDespesa;
		this.receitaUtilizada = receitaUtilizada;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Boolean getPago() {
		return pago;
	}



	public void setPago(Boolean pago) {
		this.pago = pago;
	}



	public Categoria getCategoria() {
		return categoria;
	}



	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}



	public EnumContaAtiva getContaAtiva() {
		return contaAtiva;
	}



	public void setContaAtiva(EnumContaAtiva contaAtiva) {
		this.contaAtiva = contaAtiva;
	}



	public String getDescricaoDespesa() {
		return descricaoDespesa;
	}



	public void setDescricaoDespesa(String descricaoDespesa) {
		this.descricaoDespesa = descricaoDespesa;
	}



	public BigDecimal getValorDespesa() {
		return valorDespesa;
	}



	public void setValorDespesa(BigDecimal valorDespesa) {
		this.valorDespesa = valorDespesa;
	}



	public Receita getReceitaUtilizada() {
		return receitaUtilizada;
	}



	public void setReceitaUtilizada(Receita receitaUtilizada) {
		this.receitaUtilizada = receitaUtilizada;
	}
	
	
	

}
