package br.com.houseController.model.despesas;

import java.time.LocalDateTime;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class DespesaFixa extends DespesaVariavel {
	
	@Temporal(TemporalType.TIMESTAMP)
	private LocalDateTime dtVencimento;

	public LocalDateTime getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(LocalDateTime dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
	

}
