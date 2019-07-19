package br.com.houseController.controllers;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import br.com.houseController.model.usuario.UsuarioLogado;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class HomeController implements Initializable{
	
	@FXML
	Label jlSaudacao;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregaSaudacao();
		
	}

	/**
	 * Carrega a saudação que fica no topo da página
	 * 
	 */
	private void carregaSaudacao() {
		String saudacao = "";
		Calendar calendar = Calendar.getInstance();
		UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
		if(calendar.get(Calendar.HOUR_OF_DAY) < 12){
			saudacao = "Bom Dia, ";
		}else if(calendar.get(Calendar.HOUR_OF_DAY) < 18){
			saudacao = "Boa Tarde, ";		
		}else{
			saudacao = "Boa Noite, ";
		}
		
		jlSaudacao.setText(saudacao + usuarioLogado.getUsuario().getNome());
	}

}
