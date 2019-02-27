package br.com.houseController.model.despesas;

import java.time.LocalDateTime;

public class DespesaFixa extends DespesaVariavel {
	
	private LocalDateTime dtVencimento;

	public LocalDateTime getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(LocalDateTime dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	
	

}
