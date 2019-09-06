package br.com.houseController.model.despesas;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="relacao_despesa_receita")
@Table(name="relacao_despesa_receita")
public class RelacaoDespesaReceita {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private int idDespesa;
	
	@Column
	private int idReceita;
	
	@Column
	private BigDecimal valor;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdDespesa() {
		return idDespesa;
	}

	public void setIdDespesa(int idDespesa) {
		this.idDespesa = idDespesa;
	}

	public int getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(int idReceita) {
		this.idReceita = idReceita;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public RelacaoDespesaReceita(int id, int idDespesa, int idReceita, BigDecimal valor) {
		super();
		this.id = id;
		this.idDespesa = idDespesa;
		this.idReceita = idReceita;
		this.valor = valor;
	}

	public RelacaoDespesaReceita(int idDespesa, int idReceita, BigDecimal valor) {
		super();
		this.idDespesa = idDespesa;
		this.idReceita = idReceita;
		this.valor = valor;
	}

	public RelacaoDespesaReceita() {
		super();
	}
	
	
	
	
}
