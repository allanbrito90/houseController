package br.com.houseController.model.receita;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="receita_utilizada")
@Entity(name="receita_utilizada")
public class ReceitaUtilizada {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column
	private int receitaUtilizada;
	
	@Column
	private int despesaApontada;
	
	@Column
	private BigDecimal valor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReceitaUtilizada() {
		return receitaUtilizada;
	}

	public void setReceitaUtilizada(int receitaUtilizada) {
		this.receitaUtilizada = receitaUtilizada;
	}

	public int getDespesaApontada() {
		return despesaApontada;
	}

	public void setDespesaApontada(int despesaApontada) {
		this.despesaApontada = despesaApontada;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public ReceitaUtilizada(int id, int receitaUtilizada, int despesaApontada, BigDecimal valor) {
		super();
		this.id = id;
		this.receitaUtilizada = receitaUtilizada;
		this.despesaApontada = despesaApontada;
		this.valor = valor;
	}

	
	
	public ReceitaUtilizada(int receitaUtilizada, int despesaApontada, BigDecimal valor) {
		super();
		this.receitaUtilizada = receitaUtilizada;
		this.despesaApontada = despesaApontada;
		this.valor = valor;
	}

	public ReceitaUtilizada() {	}
	
	
	
}
