package br.com.houseController.controllers.SubMenus.Compras;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.produto.Produto;
import br.com.houseController.model.produto.RelatorioCompras;
import br.com.houseController.service.Produto.ProdutoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class RelatorioComprasController implements Initializable{
	@FXML
	private StackPane spDialog;
	@FXML
	private Label jlTitulo;
	@FXML
	private Label jlTotal;
	@FXML
	private Label jlSelecioneAno;
	@FXML
	private Spinner<Integer> jsAno;
	@FXML
	private VBox vbProdutos;
	@FXML
	private TableView<RelatorioCompras> jtvTotalCompras;
	@FXML
	private TableColumn<RelatorioCompras, String> colMes;
	@FXML
	private TableColumn<RelatorioCompras, String> colValor;
	
	final static int MINANO = 2000;
	final static int MAXANO = 3000;
	
	SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO, MINANO);
	
	List<Produto> listaProdutos = new ArrayList<>();
	
	ObservableList<RelatorioCompras> produtos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		internacionalizar();
		jsAno.setValueFactory(anoSpinner);
		jsAno.getValueFactory().setValue(LocalDate.now().getYear());
		jtvTotalCompras.setItems(preencheTabela());
		
		colMes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RelatorioCompras,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<RelatorioCompras, String> param) {
				return new SimpleStringProperty(param.getValue().getDtReferencia().getMonth().name());
			}
		});
		colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		
		jsAno.valueProperty().addListener(new ChangeListener<Integer>() {

			@Override
			public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
				jtvTotalCompras.getItems().clear();
				jtvTotalCompras.setItems(preencheTabela());				
			}
		});
	}
	
	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_relatorio_compras"));
		colMes.setText(Internationalization.getMessage("titulo_mes"));
		colValor.setText(Internationalization.getMessage("titulo_valor"));
		jlSelecioneAno.setText(Internationalization.getMessage("selecione_ano"));
	}

	public ObservableList<RelatorioCompras> preencheTabela(){
		ProdutoService produtoService = new ProdutoService();
		listaProdutos = produtoService.produtosPorAno(jsAno.getValue());
		produtos = FXCollections.observableArrayList();
		BigDecimal total = BigDecimal.ZERO;
		for(int i=1; i<=12; i++){
			BigDecimal totalMes = BigDecimal.ZERO;
			BigDecimal quantidade = BigDecimal.ZERO;
			for(Produto produto : listaProdutos){
				if(produto.getPeriodoReferencia().getMonthValue() == i){
					quantidade = new BigDecimal(produto.getQuantidade());
					totalMes = totalMes.add(produto.getValor().multiply(quantidade));
					total = total.add(produto.getValor().multiply(quantidade));
				}
			}
			produtos.add(new RelatorioCompras(LocalDate.of(jsAno.getValue(), i, 1),totalMes.setScale(2, RoundingMode.HALF_EVEN)));
		}
		jlTotal.setText("$" + total.setScale(2,RoundingMode.HALF_EVEN).toString());
		return produtos;
	}
	
}
