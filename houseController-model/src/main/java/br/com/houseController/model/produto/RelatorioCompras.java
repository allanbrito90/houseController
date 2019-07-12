package br.com.houseController.model.produto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioCompras {

	private int id;
	private LocalDate dtReferencia;
	private BigDecimal valor;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDtReferencia() {
		return dtReferencia;
	}
	public void setDtReferencia(LocalDate dtReferencia) {
		this.dtReferencia = dtReferencia;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
		
	public RelatorioCompras() {
		super();
	}
	
	public RelatorioCompras(LocalDate dtReferencia, BigDecimal valor) {
		super();
		this.dtReferencia = dtReferencia;
		this.valor = valor;
	}
	
	
}
