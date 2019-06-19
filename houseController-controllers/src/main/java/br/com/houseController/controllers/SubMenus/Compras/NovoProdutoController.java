package br.com.houseController.controllers.SubMenus.Compras;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.model.produto.Produto;
import br.com.houseController.service.Produto.IngredienteService;
import br.com.houseController.service.Produto.ProdutoService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	
	@FXML
	private Spinner<Integer> jsMes;
	
	@FXML
	private Spinner<Integer> jsAno;
	
	final static int MINANO = 2000;
	final static int MAXANO = 3000;
	final static int MINMES = 1;
	final static int MAXMES = 12;
	
	SpinnerValueFactory<Integer> mesSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINMES, MAXMES, MINMES);
	SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO, MINANO);
	
	ObservableList<String> ingredientes;
	
	ProdutoService produtoService = new ProdutoService();
	IngredienteService ingredienteService = new IngredienteService();
	
	HashMap<Integer, Produto> mapProduto = new HashMap<>();
	HashMap<String,Ingrediente> mapIngrediente = new HashMap<>();
	
	int indice = 0;
	
	
	@FXML
	public void handleSalvar(){
		for(int i = 0 ; i < indice ; i++){
			if (mapProduto.get(i) != null) {
				if(mapProduto.get(i).getIngrediente() != null && mapProduto.get(i).getQuantidade() != null && mapProduto.get(i).getValor() != null){
					mapProduto.get(i).setComprado(false);
					mapProduto.get(i).setPeriodoReferencia(LocalDate.of(jsAno.getValue(),jsMes.getValue(),1));
					produtoService.insert(mapProduto.get(i));
				}else{
					ScreenUtils.janelaInformação(spDialog, "Campos Vazios", "Há campos não preenchidos", "Tá bom!");
				}
			}
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		jsMes.setValueFactory(mesSpinner);
		jsAno.setValueFactory(anoSpinner);
		jsMes.getValueFactory().setValue(LocalDate.now().getMonthValue());
		jsAno.getValueFactory().setValue(LocalDate.now().getYear());
		
		for (Ingrediente ingrediente : ingredienteService.findAll()) {
			mapIngrediente.put(ingrediente.getDescricaoIngrediente(), ingrediente);
		}		
		
		Platform.runLater(() -> {
			if (getObjetos() != null) {
				
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

	private void criarProduto(int indice) {
		Produto produto = new Produto(); 
		mapProduto.put(indice, produto);		
		
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
		
		jcbProduto.setOnAction((event)->{
			produto.setIngrediente(mapIngrediente.get(jcbProduto.getSelectionModel().getSelectedItem()));			
		});
		
		jsQtde.valueProperty().addListener((obs, oldV, newV) -> {
			produto.setQuantidade(Integer.valueOf(newV));
		});
		
		jntfValor.setOnKeyReleased((event)->{
			produto.setValor(BigDecimal.valueOf(Float.valueOf(jntfValor.getText())));
		});
		
		jbApagar.setOnMouseClicked((event)->{
			vbProdutos.getChildren().remove(hbProduto);
			mapProduto.remove(indice);			
		});
	}
	
	@FXML
	public void handleAdicionar(){
		criarProduto(indice++);
	}

}
