package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class SubMenuReceitas extends Controller implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// Setando título
				titulo(Internationalization.getMessage("botao_receitas"));

				// Criando os Blocos nos HBoxes
				List<HBox> hBoxes = criaBlocosSubMenus(
						new BlocoSubMenu(Internationalization.getMessage("botao_nova_receita"), "images/nova.png", "fxml/Receita/NovaReceita.fxml"), 
						new BlocoSubMenu(Internationalization.getMessage("botao_alterar_receita"), "images/alterar_receita.png", "fxml/Receita/AlterarReceita.fxml")
						);

				// Adicionando os HBoxes prontos no VBox
				for (HBox hBox : hBoxes) {
					vbMenu.getChildren().add(hBox);
				}
			}
		});

	}

}
