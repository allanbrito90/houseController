package br.com.houseController.model.receita;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="receita")
@Table(name="receita")
public class Receita {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	
	@Column
	//@Temporal(TemporalType.TIMESTAMP)
	LocalDateTime dtPagamento;
	
	@Column
	String descricaoPagamento;
	
	public Receita(int id, LocalDateTime dtPagamento, String descricaoPagamento) {
		super();
		this.id = id;
		this.dtPagamento = dtPagamento;
		this.descricaoPagamento = descricaoPagamento;
	}
	
	public Receita(LocalDateTime dtPagamento, String descricaoPagamento) {
		super();
		this.dtPagamento = dtPagamento;
		this.descricaoPagamento = descricaoPagamento;
	}
	
	public Receita() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDateTime getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(LocalDateTime dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public String getDescricaoPagamento() {
		return descricaoPagamento;
	}
	public void setDescricaoPagamento(String descricaoPagamento) {
		this.descricaoPagamento = descricaoPagamento;
	}
	
	
}
