package br.com.houseController.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class LoginController implements Initializable {

	@FXML
	JFXTextField jtfLogin;

	@FXML
	JFXPasswordField jpfSenha;

	@FXML
	JFXButton jbLogin;

	public void initialize(URL location, ResourceBundle resources) {
		jbLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				String encriptado = DigestUtils.md5Hex(jpfSenha.getText());
				System.out.println("Funcionou, pode comemorar que deu certo." + encriptado);
				
				Usuario usuario = new Usuario();
				usuario.setLogin(jtfLogin.getText());
				usuario.setSenha(jpfSenha.getText());
				
				UsuarioService usuarioService = new UsuarioService();
				if(usuarioService.checaLogin(usuario)){
					JOptionPane.showMessageDialog(null, "USUÁRIO ENCONTRADO");
				}else{
					JOptionPane.showMessageDialog(null, "USUÁRIO NÃO ENCONTRADO");
				}
				
			}
		});
	}
	
	@FXML
	public void sair(){
		ConnectionFactory.fecharConexao();
		System.exit(0);
	}

}
