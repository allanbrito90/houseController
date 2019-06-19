package br.com.houseController.model.produto;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="produto")
@Table(name="produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Ingrediente ingrediente;
	
	@Column
	private BigDecimal valor;
	
	@Column
	private Integer quantidade;
	
	@Column
	private LocalDate periodoReferencia;
	
	@Column
	private Boolean comprado;

	
	
	public LocalDate getPeriodoReferencia() {
		return periodoReferencia;
	}
	public void setPeriodoReferencia(LocalDate periodoReferencia) {
		this.periodoReferencia = periodoReferencia;
	}
	public Boolean getComprado() {
		return comprado;
	}
	public void setComprado(Boolean comprado) {
		this.comprado = comprado;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Ingrediente getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(Ingrediente ingrediente) {
		this.ingrediente = ingrediente;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}	
	
	
}
