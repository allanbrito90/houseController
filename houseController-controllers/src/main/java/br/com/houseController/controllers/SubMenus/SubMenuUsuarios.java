package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.houseController.controllers.utils.ScreenUtils;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class SubMenuUsuarios implements Initializable {

	@FXML
	AnchorPane jbNovoUsuario;
	
	@FXML
	AnchorPane jbAlterarUsuario;
	
	@FXML
	Label jlTitulo;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				jlTitulo.setText("Usuários");
			}
		});
		
		jbNovoUsuario.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils.abrirNovaJanela("fxml/Usuario/NovoUsuario.fxml");
			}
		});
		
		jbAlterarUsuario.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils.abrirNovaJanela("fxml/Usuario/AlterarUsuario.fxml");
			}
		});
	}

}
