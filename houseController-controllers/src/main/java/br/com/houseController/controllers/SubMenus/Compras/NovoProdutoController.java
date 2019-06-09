package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.service.Produto.IngredienteService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class NovoProdutoController extends ParametrosObjetos implements Initializable {
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private Label jlTitulo;
	
	@FXML
	private VBox vbProdutos;
	
	ObservableList<Ingrediente> ingredientes;
	
	
	@FXML
	public void handleSalvar(){
		
	}

	@FXML
	public void handleLimpar(){
//		ScreenUtils.limparCampos(jtfNome, jcbUnidadeMedida);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		criarProduto();
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (getObjetos() != null) {
					
				}
			}
		});		
	}
	
	private ObservableList<Ingrediente> carregaListaIngredientes(){
		IngredienteService ingredienteService = new IngredienteService();
		List<Ingrediente> listIngrediente = ingredienteService.findAll();
		ingredientes = FXCollections.observableArrayList();
		for(Ingrediente ingrediente : listIngrediente) {
			ingredientes.add(ingrediente);
		}
		return ingredientes;
	}

	private void criarProduto() {
		HBox hbProduto = new HBox();
		JFXComboBox<Ingrediente> jcbProduto = new JFXComboBox<>();
		jcbProduto.setItems(carregaListaIngredientes());
		hbProduto.getChildren().add(jcbProduto);
		vbProdutos.getChildren().add(hbProduto);
	}

}
