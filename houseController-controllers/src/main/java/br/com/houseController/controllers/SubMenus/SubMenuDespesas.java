package br.com.houseController.controllers.SubMenus;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.Controller;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class SubMenuDespesas extends Controller implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// Setando título
				titulo("Despesas");

				// Criando os Blocos nos HBoxes
				List<HBox> hBoxes = criaBlocosSubMenus(
						new BlocoSubMenu("Pagar Despesa", "images/pagar_despesa.png", "fxml/Despesa/PagarDespesa.fxml"),
						new BlocoSubMenu("Nova Despesa", "images/nova.png", "fxml/Despesa/NovaDespesa.fxml"), 
						new BlocoSubMenu("Alterar Despesa", "images/alterar_despesa.png", "fxml/Despesa/AlterarDespesa.fxml")
						);

				// Adicionando os HBoxes prontos no VBox
				for (HBox hBox : hBoxes) {
					vbMenu.getChildren().add(hBox);
				}
			}
		});

	}

}
