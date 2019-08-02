package br.com.houseController.model.despesas;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="compras")
@Table(name="compras")
public class Compras extends DespesaVariavel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private LocalDate periodoReferencia;
	
	@Column
	private BigDecimal totalProdutos;
	
//	@OneToMany
//	private List<Produto> produtos;
	
	public Compras(LocalDate periodoReferencia, BigDecimal valor/* List<Produto> produtos*/) {
		super();
		this.periodoReferencia = periodoReferencia;
		this.totalProdutos = valor;
//		this.produtos = produtos;
	}



	public Compras() {}



	public LocalDate getPeriodoReferencia() {
		return periodoReferencia;
	}
	public void setPeriodoReferencia(LocalDate periodoReferencia) {
		this.periodoReferencia = periodoReferencia;
	}
	public BigDecimal getValor() {
		return totalProdutos;
	}
	public void setValor(BigDecimal valor) {
		this.totalProdutos = valor;
	}
	


//	public List<Produto> getProdutos() {
//		return produtos;
//	}
//
//
//
//	public void setProdutos(List<Produto> produtos) {
//		this.produtos = produtos;
//	}

	
	
	
}
