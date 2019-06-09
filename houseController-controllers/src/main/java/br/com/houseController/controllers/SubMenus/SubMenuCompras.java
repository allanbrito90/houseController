package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class SubMenuCompras extends Controller implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// Setando título
				titulo("Usuários");

				// Criando os Blocos nos HBoxes
				List<HBox> hBoxes = criaBlocosSubMenus(
						new BlocoSubMenu("Nova Compra", null, "fxml/Usuario/NovoUsuario.fxml"), 
						new BlocoSubMenu("Alterar Compra", null, "fxml/Usuario/AlterarUsuario.fxml"),
						new BlocoSubMenu("Novo Produto", null, "fxml/Compras/NovoProduto.fxml"),
						new BlocoSubMenu("Alterar Produto", null, "fxml/Usuario/NovoUsuario.fxml"),
						new BlocoSubMenu("Novo Ingrediente", null, "fxml/Compras/NovoIngrediente.fxml"),
						new BlocoSubMenu("Alterar Ingrediente", null, "fxml/Compras/AlterarIngrediente.fxml"),
						new BlocoSubMenu("Nova Unidade de Medida", null, "fxml/Compras/NovaUnidadeMedida.fxml"),
						new BlocoSubMenu("Alterar Unidade de Medida", null, "fxml/Compras/AlterarUnidadeMedida.fxml")
						);

				// Adicionando os HBoxes prontos no VBox
				for (HBox hBox : hBoxes) {
					vbMenu.getChildren().add(hBox);
				}
			}
		});

	}

}
