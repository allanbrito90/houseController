package br.com.houseController.controllers;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.codec.Decoder;
import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.Exceptions.CamposNaoPreenchidosException;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.Enums.EnumIdioma;
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
	
	@FXML
	JFXButton jbSair;
	
	@FXML
	JFXButton jbLanguage;
		
	Stage stage;
	
	Boolean retornoLogin = false;

	public void initialize(URL location, ResourceBundle resources) {
		//Font.loadFont(LoginController.class.getClassLoader().getResource("fonts/Lato-Bold.ttf").toExternalForm(), 10);
		//jtfLogin.setStyle("-fx-font-family: 'Lato';");
		//Fonts.addFont(new Fonts("Lato-Bold.ttf"));
		//jbLogin.setFont(new Font("Lato", 15));
		
		jbLogin.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
//				Internationalization.setLocale(new Locale("pt","BR"));
//				Internationalization.setResourceBundle(ResourceBundle.getBundle("internationalization/messages", Internationalization.getInstance()));
//				System.out.println(Internationalization.getMessage("bemvindo"));
				
				Usuario usuario = new Usuario();
				usuario.setLogin(jtfLogin.getText());
				usuario.setSenha(DigestUtils.md5Hex(jpfSenha.getText()));
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
					if(!(e.getSource().getException() instanceof CamposNaoPreenchidosException)){
						ScreenUtils.janelaInformação(spStatus, Internationalization.getMessage("header_erro1"), Internationalization.getMessage("login_incorreto"), Internationalization.getMessage("certo_button1"));
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
		
		jbLanguage.setOnAction((e)->{
			//Verifica se está em português
			if(Internationalization.getLocale().getLanguage().equals("pt")){
				Internationalization.setLocale(new Locale(EnumIdioma.INGLES.getLinguagem(),EnumIdioma.INGLES.getPais()));
			}else{
				Internationalization.setLocale(new Locale(EnumIdioma.PORTUGUES.getLinguagem(),EnumIdioma.PORTUGUES.getPais()));
			}
			Internationalization.setResourceBundle(ResourceBundle.getBundle("internationalization/messages",Internationalization.getLocale()));
			internacionalizar();
		});
		
		Platform.runLater(()->{
			internacionalizar();
			jtfLogin.requestFocus();			
		});
	}
	
	
	@FXML
	public void sair(){
		ConnectionFactory.fecharConexao();
		System.exit(0);
	}
	
	public void internacionalizar(){
		jlBemVindo.setText(Internationalization.getMessage("bemvindo"));
		jtfLogin.setPromptText(Internationalization.getMessage("campoLogin"));
		jpfSenha.setPromptText(Internationalization.getMessage("campoSenha"));
		jbSair.setText(Internationalization.getMessage("campoSair"));
		jbLanguage.setText(Internationalization.getMessage("campoLanguage"));
	}

}
