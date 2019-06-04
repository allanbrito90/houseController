package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.produto.UnidadeMedida;
import br.com.houseController.service.Compras.UnidadeMedidaService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

public class NovaUnidadeMedidaController extends ParametrosObjetos implements Initializable {
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private JFXTextField jtfNome;
	
	UnidadeMedida unidadeMedida = new UnidadeMedida();
	
	@FXML
	public void handleSalvar(){
		if(ScreenUtils.checarCamposVazios(jtfNome)){
			unidadeMedida.setDescricao(jtfNome.getText());
			UnidadeMedidaService unidadeMedidaService = new UnidadeMedidaService();
			unidadeMedidaService.insert(unidadeMedida);
		}
	}

	@FXML
	public void handleLimpar(){
		ScreenUtils.limparCampos(jtfNome);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		
		
	}

}
