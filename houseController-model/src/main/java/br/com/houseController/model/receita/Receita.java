package br.com.houseController.model.receita;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sun.istack.NotNull;

import br.com.houseController.model.usuario.Usuario;

@Entity(name="receita")
@Table(name="receita")
public class Receita {
	
	@Id
	@NotNull
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private LocalDate dtPagamento;
	
	@Column
	private String descricaoPagamento;
	
	@Column
	private BigDecimal valor;
	
	@OneToOne
	private Usuario usuario;

	public Receita(LocalDate dtPagamento, String descricaoPagamento, BigDecimal valor, Usuario usuario) {
		super();
		this.dtPagamento = dtPagamento;
		this.descricaoPagamento = descricaoPagamento;
		this.valor = valor;
		this.usuario = usuario;
	}

	public Receita(int id, LocalDate dtPagamento, String descricaoPagamento, BigDecimal valor, Usuario usuario) {
		super();
		this.id = id;
		this.dtPagamento = dtPagamento;
		this.descricaoPagamento = descricaoPagamento;
		this.valor = valor;
		this.usuario = usuario;
	}

	public Receita() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(LocalDate dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getDescricaoPagamento() {
		return descricaoPagamento;
	}
	public void setDescricaoPagamento(String descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
