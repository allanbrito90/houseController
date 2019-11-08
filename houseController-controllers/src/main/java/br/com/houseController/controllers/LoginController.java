package br.com.houseController.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.model.usuario.UsuarioLogado;
import br.com.houseController.persistence.ConnectionFactory;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController extends ParametrosObjetos implements Initializable {

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
	StackPane spStatus;
	
	@FXML
	Label jlBemVindo;
		
	Stage stage;
	
	Boolean retornoLogin = false;

	public void initialize(URL location, ResourceBundle resources) {
		//Font.loadFont(LoginController.class.getClassLoader().getResource("fonts/Lato-Bold.ttf").toExternalForm(), 10);
		//jtfLogin.setStyle("-fx-font-family: 'Lato';");
		//Fonts.addFont(new Fonts("Lato-Bold.ttf"));
		//jbLogin.setFont(new Font("Lato", 15));
		
		jbLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {

				String encriptado = DigestUtils.md5Hex(jpfSenha.getText());
				
				Usuario usuario = new Usuario();
				usuario.setLogin(jtfLogin.getText());
				usuario.setSenha(jpfSenha.getText());
				
				UsuarioService usuarioService = new UsuarioService(usuario);
				
				
//				try {					
//				ExecutorService e = Executors.newSingleThreadExecutor();
//				
//				FutureTask<Boolean> tarefa = new FutureTask<Boolean>(usuarioService);
//				e.execute(tarefa);
//				
//				retornoLogin = tarefa.get();
//				
//				
//				} catch (InterruptedException | ExecutionException e1) {
//					e1.printStackTrace();
//				}		
								
				Task<Void> task = new Task<Void>() {
				    @Override 
				    public Void call() throws Exception {			
				    		Boolean retorno = usuarioService.checaLogin(usuario);
				    		if(!retorno){
				    			throw new Exception();
				    		}
							return null;
				    }
				};

				task.setOnRunning((e) -> Aguarde2.mostrarJanelaAguarde());
				task.setOnSucceeded((e) -> {
				    Aguarde2.finalizarJanelaAguarde();				    
				    fechaTelaLogin();
					UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
					usuarioLogado.setUsuario(usuarioService.findOneByLoginAndSenha(usuario));
					usuarioLogado.setDtLogin(LocalDate.now());
					abreTelaPrincipal();
				   
				});
				task.setOnFailed((e) -> {
					Aguarde2.finalizarJanelaAguarde();
					if(ScreenUtils.checarCamposVazios(jtfLogin,jpfSenha)){
						ScreenUtils.janelaInformação(spStatus, "Erro", "Usuário e/ou Senha Incorreta", "Entendi");
					}else{
						ScreenUtils.janelaInformação(spStatus, "Erro", "Há campos não preenchidos", "Entendi");
					}
				});
				new Thread(task).start();				
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
		
		Platform.runLater(()->{
			jtfLogin.requestFocus();			
		});
	}
	
	
	@FXML
	public void sair(){
		ConnectionFactory.fecharConexao();
		System.exit(0);
	}

}
