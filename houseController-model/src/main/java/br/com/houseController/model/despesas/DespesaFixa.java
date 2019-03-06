package br.com.houseController.model.despesas;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name="despesaFixa")
@Table(name="despesa_fixa")
public class DespesaFixa extends DespesaVariavel {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column
	//@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dtVencimento;
	
	
	public DespesaFixa(LocalDateTime dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
	public DespesaFixa() {}

	public LocalDateTime getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(LocalDateTime dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
	

}
