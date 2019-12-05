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
				titulo("Compras");

				// Criando os Blocos nos HBoxes
				List<HBox> hBoxes = criaBlocosSubMenus(
						new BlocoSubMenu("Relatório de Compras", "images/relatorio_compras.png", "fxml/Compras/RelatorioCompras.fxml"), 
						new BlocoSubMenu("Compras", "images/compras.png", "fxml/Compras/NovoProduto.fxml"),
						new BlocoSubMenu("Novo Ingrediente", "images/novo_ingrediente.png", "fxml/Compras/NovoIngrediente.fxml"),
						new BlocoSubMenu("Alterar Ingrediente", "images/alterar_ingrediente.png", "fxml/Compras/AlterarIngrediente.fxml"),
						new BlocoSubMenu("Nova Unidade de Medida", "images/nova_unidade_medida.png", "fxml/Compras/NovaUnidadeMedida.fxml"),
						new BlocoSubMenu("Alterar Unidade de Medida", "images/alterar_unidade_medida.png", "fxml/Compras/AlterarUnidadeMedida.fxml")
						);

				// Adicionando os HBoxes prontos no VBox
				for (HBox hBox : hBoxes) {
					vbMenu.getChildren().add(hBox);
				}
			}
		});

	}

}
