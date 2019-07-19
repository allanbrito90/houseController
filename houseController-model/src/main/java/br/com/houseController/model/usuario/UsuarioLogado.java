package br.com.houseController.model.usuario;

import java.time.LocalDate;

public class UsuarioLogado{
	
	private static UsuarioLogado instance;
	
	private Usuario usuario;
	private LocalDate dtLogin;
	
	private UsuarioLogado(){
		
	}
	
	public static UsuarioLogado getInstance(){
		if(instance == null){
			instance = new UsuarioLogado();
		}
		return instance;
	}
	
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public LocalDate getDtLogin() {
		return dtLogin;
	}

	public void setDtLogin(LocalDate dtLogin) {
		this.dtLogin = dtLogin;
	}
	
	
}
