package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.houseController.controllers.utils.ScreenUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class SubMenuUsuarios implements Initializable {

	@FXML
	AnchorPane jbNovoUsuario;
	
	@FXML
	AnchorPane jbAlterarUsuario;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
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
