package br.com.houseController.Exceptions;

public class CamposNaoPreenchidosException extends Exception {

	private static final String defaultMessage = "Há campos não preenchidos";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CamposNaoPreenchidosException(){
		super(defaultMessage);
	}
	
	public CamposNaoPreenchidosException(String message){
		super(message);
	}
}
