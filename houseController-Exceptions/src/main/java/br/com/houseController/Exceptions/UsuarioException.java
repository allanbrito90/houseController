package br.com.houseController.Exceptions;

public class UsuarioException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsuarioException(){}
	
	public UsuarioException(String message){
		super(message);
	}
	
	public UsuarioException(String message, Exception cause){
		super(message, cause);
	}

}
