package br.com.houseController.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {

	@FXML
	JFXTextField jtfLogin;

	@FXML
	JFXPasswordField jpfSenha;

	@FXML
	JFXButton jbLogin;
	
	@FXML
	BorderPane bpLogin;
	
	@FXML
	AnchorPane apTelaCentro;
	
	@FXML
	Label jlBemVindo;
		
	Stage stage;
	
	Boolean retornoLogin;

	public void initialize(URL location, ResourceBundle resources) {
		Font.loadFont(LoginController.class.getClassLoader().getResource("fonts/Lato-Bold.ttf").toExternalForm(), 10);
		//jtfLogin.setStyle("-fx-font-family: 'Lato';");
		
		jbLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String encriptado = DigestUtils.md5Hex(jpfSenha.getText());
				
				Usuario usuario = new Usuario();
				usuario.setLogin(jtfLogin.getText());
				usuario.setSenha(jpfSenha.getText());
				
//				new Bounce(tstLabel).play();
//				new FadeIn(jlBemVindo).setCycleCount(5).setCycleDuration(2).play();;
				
				UsuarioService usuarioService = new UsuarioService(usuario);				
				
				try {					
				ExecutorService e = Executors.newSingleThreadExecutor();
				
				FutureTask<Boolean> tarefa = new FutureTask<Boolean>(usuarioService);
				e.execute(tarefa);
				
				retornoLogin = tarefa.get();
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}		
				
				System.out.println("Retorno: " + retornoLogin);	
				
				if(retornoLogin){
					fechaTelaLogin();					
					abreTelaPrincipal();					
				}
				
			}

			private void fechaTelaLogin() {
				Stage stage = (Stage) jtfLogin.getScene().getWindow();
				stage.close();
			}

			private void abreTelaPrincipal() {
				try {
					Stage stage2 = new Stage();
					stage2.initStyle(StageStyle.TRANSPARENT);
					Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/principal.fxml"));
					Scene scene = new Scene(root,1000,600);
					scene.setFill(Color.TRANSPARENT);
					stage2.setScene(scene);
					stage2.setWidth(1000);
					stage2.setHeight(600);
					stage2.show();
				} catch (IOException e) {
					e.printStackTrace();
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
