package br.com.houseController.model.despesas;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.houseController.model.produto.Produto;

@Entity(name="compras")
@Table(name="compras")
public class Compras extends DespesaVariavel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	private LocalDateTime periodoReferencia;
	
	@OneToMany
	private List<Produto> produtos;
	
	public Compras(LocalDateTime periodoReferencia, List<Produto> produtos) {
		super();
		this.periodoReferencia = periodoReferencia;
		this.produtos = produtos;
	}



	public Compras() {}



	public LocalDateTime getPeriodoReferencia() {
		return periodoReferencia;
	}
	public void setPeriodoReferencia(LocalDateTime periodoReferencia) {
		this.periodoReferencia = periodoReferencia;
	}



	public List<Produto> getProdutos() {
		return produtos;
	}



	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	
	
	
}
