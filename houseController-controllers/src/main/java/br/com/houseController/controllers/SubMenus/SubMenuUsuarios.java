package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SubMenuUsuarios extends Controller implements Initializable{

	/*@FXML
	AnchorPane jbNovoUsuario;
	
	@FXML
	AnchorPane jbAlterarUsuario;*/
	
	@FXML
	VBox vbMenu;
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				//Setando título
				titulo(Internationalization.getMessage("botao_usuarios"));
				
//				Criando os Blocos nos HBoxes
				List<HBox> hBoxes = criaBlocosSubMenus(
						new BlocoSubMenu(Internationalization.getMessage("botao_novo_usuario"), "images/novo_usuario.png", "fxml/Usuario/NovoUsuario.fxml"),
						new BlocoSubMenu(Internationalization.getMessage("botao_alterar_usuario"), "images/alterar_usuario.png", "fxml/Usuario/AlterarUsuario.fxml"));
				
//				Adicionando os HBoxes prontos no VBox				
				for(HBox hBox : hBoxes){
					vbMenu.getChildren().add(hBox);
				}
			}
		});
		
	}

}
