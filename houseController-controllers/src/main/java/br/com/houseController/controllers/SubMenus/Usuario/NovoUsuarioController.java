package br.com.houseController.controllers.SubMenus.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class NovoUsuarioController implements Initializable {
	
	@FXML
	JFXTextField jtfNome;
	
	@FXML
	JFXTextField jtfLogin;
	
	@FXML
	JFXPasswordField jpfSenha;
	
	@FXML
	JFXTextField jtfEmail;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	
	@FXML
	public void handleSalvar(){
		UsuarioService usuarioService = new UsuarioService();
		if(!usuarioService.checaLogin(new Usuario(jtfLogin.getText(), jpfSenha.getText(), jtfNome.getText(), jtfEmail.getText(), true))){
			usuarioService.insert(new Usuario(jtfLogin.getText(), jpfSenha.getText(), jtfNome.getText(), jtfEmail.getText(), true));
		}else{
			System.out.println("Usuário já cadastrado");
		}
	}

}
