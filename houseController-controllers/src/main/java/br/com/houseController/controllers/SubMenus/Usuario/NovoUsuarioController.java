package br.com.houseController.controllers.SubMenus.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class NovoUsuarioController extends ParametrosObjetos implements Initializable {
	
	@FXML
	JFXTextField jtfNome;
	
	@FXML
	JFXTextField jtfLogin;
	
	@FXML
	JFXPasswordField jpfSenha;
	
	@FXML
	JFXTextField jtfEmail;
	
	@FXML
	Label jlTitulo;
	
	Usuario usuario = new Usuario();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		listenersCampos();
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				if(getObjetos()!=null){
					usuario = (Usuario)getObjetos().get(0);
					jlTitulo.setText("Editar Usuário");
					jtfNome.setText(usuario.getNome());
					jtfLogin.setText(usuario.getLogin());
					jpfSenha.setText(usuario.getSenha());
					jtfEmail.setText(usuario.getEmail());
					
					
				}
			}
		});
		
	}

	private void listenersCampos() {
		jtfLogin.textProperty().addListener((obs,oldT,newT) -> {
			usuario.setLogin(newT);
		});
		
		jtfNome.textProperty().addListener((obs,oldT,newT) -> {
			usuario.setNome(newT);
		});
		
		jtfEmail.textProperty().addListener((obs,oldT,newT) -> {
			usuario.setEmail(newT);
		});
		
		jpfSenha.textProperty().addListener((obs,oldT,newT) -> {
			usuario.setSenha(newT);
		});
	}
	
	@FXML
	public void handleSalvar(){
		usuario.setAtivo(true);
		UsuarioService usuarioService = new UsuarioService();
		if(!usuarioService.checaLogin(usuario)){
			usuarioService.insert(usuario);
		}else{
			System.out.println("Usuário já cadastrado");
		}
	}
	
	@FXML
	public void handleLimpar(){
		ScreenUtils.limparCampos(jtfLogin,jpfSenha,jtfNome,jtfEmail);
	}
	
	

}
