package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.service.Produto.IngredienteService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
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
	
	@FXML
	private AnchorPane jbAdd;
	
	ObservableList<String> ingredientes;
	
	
	@FXML
	public void handleSalvar(){
		
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
	
	private ObservableList<String> carregaListaIngredientes(){
		IngredienteService ingredienteService = new IngredienteService();
		List<Ingrediente> listIngrediente = ingredienteService.findAll();
		ingredientes = FXCollections.observableArrayList();
		for(Ingrediente ingrediente : listIngrediente) {
			ingredientes.add(ingrediente.getDescricaoIngrediente());
		}
		return ingredientes;
	}

	private void criarProduto() {
		HBox hbProduto = new HBox();
		vbProdutos.getChildren().add(hbProduto);
		hbProduto.setSpacing(10);
		
		JFXComboBox<String> jcbProduto = new JFXComboBox<>();
		jcbProduto.setItems(carregaListaIngredientes());
		jcbProduto.setPrefWidth(400);
		hbProduto.getChildren().add(jcbProduto);
		
		Spinner<Integer> jsQtde = new Spinner<>();
		SpinnerValueFactory<Integer> valorSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0);
		jsQtde.setValueFactory(valorSpinner);
		hbProduto.getChildren().add(jsQtde);
		
		NumberTextField jntfValor = new NumberTextField();
		jntfValor.setPromptText("Valor");
		
		JFXButton jbApagar = new JFXButton("Deletar");
		hbProduto.getChildren().add(jntfValor);		
		hbProduto.getChildren().add(jbApagar);
		
		jbApagar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				System.out.println(jcbProduto.getSelectionModel().getSelectedItem());
				vbProdutos.getChildren().remove(hbProduto);
			}
		});
	}
	
	@FXML
	public void handleAdicionar(){
		criarProduto();
	}

}
