package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SubMenuMetaController extends Controller implements Initializable{
	
	@FXML
	private VBox vbMenu;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			// Criando os Blocos nos HBoxes
			List<HBox> hBoxes = criaBlocosSubMenus(
					new BlocoSubMenu("Nova Meta", null, "fxml/Meta/NovaMeta.fxml"), 
					new BlocoSubMenu("Alterar Meta", null, "fxml/Compras/NovoProduto.fxml")
					);

			// Adicionando os HBoxes prontos no VBox
			for (HBox hBox : hBoxes) {
				vbMenu.getChildren().add(hBox);
			}
		});
	}
	

}
