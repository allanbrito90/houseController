package br.com.houseController.controllers.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
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

	Usuario usuario = new Usuario();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		listenersCampos();

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (getObjetos() != null) {
					usuario = (Usuario) getObjetos().get(0);
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
			usuario.setSenha(newT);
		});
	}

	@FXML
	public void handleSalvar() {
		if (ScreenUtils.checarCamposVazios(jtfLogin, jpfSenha, jtfNome, jtfEmail)) {
			usuario.setAtivo(true);
			UsuarioService usuarioService = new UsuarioService();
			if (!jlTitulo.getText().contains("Editar")) {
				if (!usuarioService.checaLogin(usuario)) {
					usuarioService.insert(usuario);
				} else {
					ScreenUtils.janelaInformação(spDialog, "Atenção", "Usuário já cadastrado", "Ok");
				} 
			}else{
				usuarioService.insert(usuario);
				ScreenUtils.janelaInformação(spDialog, "Informação", "Usuário alterado com sucesso", "Obrigado!");
//				ScreenUtils.abrirNovaJanela("fxml/Usuario/AlterarUsuario.fxml");
			}
		}else{
			ScreenUtils.janelaInformação(spDialog, "Atenção", "Há campos não preenchidos", "Entendi");
		}
	}

	@FXML
	public void handleLimpar() {
		ScreenUtils.limparCampos(jtfLogin, jpfSenha, jtfNome, jtfEmail);
		
		
	}


}
