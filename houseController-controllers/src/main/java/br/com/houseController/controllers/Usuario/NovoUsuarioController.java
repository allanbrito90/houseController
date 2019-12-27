package br.com.houseController.controllers.Usuario;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.Exceptions.UsuarioException;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.model.usuario.UsuarioLogado;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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

	@FXML
	StackPane spDialog;
	
	@FXML
	Label jlSubTitulo;
	
	@FXML
	Label jlNome;
	
	@FXML
	Label jlLogin;
	
	@FXML
	Label jlSenha;
	
	@FXML
	Label jlEmail;
	
	@FXML
	Label jlSalvar;
	
	@FXML
	Label jlLimpar;

	Usuario usuario = new Usuario();
	Boolean editar = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciaNomesCampos();
		listenersCampos();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (getObjetos() != null) {
					usuario = (Usuario) getObjetos().get(0);
					jlTitulo.setText(Internationalization.getMessage("titulo_editar"));
					jtfNome.setText(usuario.getNome());
					jtfLogin.setText(usuario.getLogin());
					jpfSenha.setText("");
					jtfEmail.setText(usuario.getEmail());
					editar = true;
				}
			}
		});

	}

	private void iniciaNomesCampos() {
		jlTitulo.setText(Internationalization.getMessage("titulo_novo"));
		jlSubTitulo.setText(Internationalization.getMessage("msg_preencha_campos"));
		jlNome.setText(Internationalization.getMessage("campo_nome"));
		jlLogin.setText(Internationalization.getMessage("campo_login"));
		jlSenha.setText(Internationalization.getMessage("campo_senha"));
		jlEmail.setText(Internationalization.getMessage("campo_email"));
		
	}

	private void listenersCampos() {
		jtfLogin.textProperty().addListener((obs, oldT, newT) -> {
			usuario.setLogin(newT);
		});

		jtfNome.textProperty().addListener((obs, oldT, newT) -> {
			usuario.setNome(newT);
		});

		jtfEmail.textProperty().addListener((obs, oldT, newT) -> {
			usuario.setEmail(newT);
		});

		jpfSenha.textProperty().addListener((obs, oldT, newT) -> {
			usuario.setSenha(DigestUtils.md5Hex(newT));
		});
	}

	@FXML
	public void handleSalvar() {
		
		Task<Void> task = new Task<Void>() {
		    @Override 
		    public Void call() throws Exception {		
		    	ScreenUtils.checarCamposVazios(jtfLogin, jpfSenha, jtfNome, jtfEmail);
				usuario.setAtivo(true);
				UsuarioService usuarioService = new UsuarioService();
				if (!editar) {
					if (!usuarioService.checaLogin(usuario)) {
						usuarioService.insert(usuario);
					} else {
						throw new UsuarioException(Internationalization.getMessage("erro_usuario_cadastrado"));
					} 
				}else{
					usuarioService.insert(usuario);
				}
				return null;
		    }
		};

		task.setOnRunning((e) -> Aguarde2.mostrarJanelaAguarde());
		task.setOnSucceeded((e) -> {
		    Aguarde2.finalizarJanelaAguarde();				    
		    if (!editar) {
		    	ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_sucesso1"), Internationalization.getMessage("msg_cadastro_sucesso"), Internationalization.getMessage("certo_button2"));
		    }else{
		    	ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_sucesso1"), Internationalization.getMessage("msg_alterado_sucesso"), Internationalization.getMessage("certo_button2"));
		    }
		});
		task.setOnFailed((e) -> {
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro2"), e.getSource().getException().getMessage(),  Internationalization.getMessage("erro_button1"));
		});
		new Thread(task).start();		
		
	}

	@FXML
	public void handleLimpar() {
		ScreenUtils.limparCampos(jtfLogin, jpfSenha, jtfNome, jtfEmail);
	}


}
