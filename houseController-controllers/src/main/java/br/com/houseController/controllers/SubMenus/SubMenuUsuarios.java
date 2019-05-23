package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import javafx.application.Platform;
import javafx.fxml.Initializable;

public class SubMenuUsuarios extends Controller implements Initializable{

	/*@FXML
	AnchorPane jbNovoUsuario;
	
	@FXML
	AnchorPane jbAlterarUsuario;*/
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				//Setando título
				titulo("Usuários");
				
//				Criando os VBox para os AnchorsPane
				
//				Criando os AnchorsPane
				
			}
		});
		
		/*jbNovoUsuario.setOnMouseClicked(new EventHandler<Event>() {

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
		});*/
	}

}
