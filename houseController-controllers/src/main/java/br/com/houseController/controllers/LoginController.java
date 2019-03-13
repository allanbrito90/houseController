package br.com.houseController.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import javax.swing.JOptionPane;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.controllers.dialogs.Aguarde;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

	@FXML
	JFXTextField jtfLogin;

	@FXML
	JFXPasswordField jpfSenha;

	@FXML
	JFXButton jbLogin;
	
	@FXML
	BorderPane bpLogin;
	
	Stage stage;

	public void initialize(URL location, ResourceBundle resources) {
		jbLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				String encriptado = DigestUtils.md5Hex(jpfSenha.getText());
				
				Usuario usuario = new Usuario();
				usuario.setLogin(jtfLogin.getText());
				usuario.setSenha(jpfSenha.getText());
				
				Aguarde aguarde = new Aguarde();
				aguarde.mostrarJanelaAguarde();
				
				
				UsuarioService usuarioService = new UsuarioService(usuario);
				
				
				try {					
				ExecutorService e = Executors.newSingleThreadExecutor();
				
				FutureTask<Integer> tarefa = new FutureTask<Integer>(usuarioService);
				e.execute(tarefa);
				
				
					Integer teste = tarefa.get();
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}				

				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						
					}
				});
//				usuarioService.restart();
//				aguarde.finalizarJanelaAguarde();
			}
		});
	}
	
	@FXML
	public void sair(){
		ConnectionFactory.fecharConexao();
		System.exit(0);
	}

}
