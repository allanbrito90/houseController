package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.service.Produto.IngredienteService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.scene.control.Label;

public class AlterarIngredienteController implements Initializable{
	
	ObservableList<Ingrediente> ingredientes;
	
	IngredienteService ingredienteService = new IngredienteService();
	
	@FXML
	private StackPane spDialog;
	@FXML
	private TableView<Ingrediente> jtvIngredienteTable;
	@FXML
	private TableColumn<Ingrediente, String> colNome;
	@FXML
	private TableColumn<Ingrediente, String> colUnidadeMedida;
	
	@FXML
	private Label jlTitulo;
	
	@FXML
	private Label jlEditar;
	
	@FXML
	private Label jlExcluir;

	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void Alterar(MouseEvent event) {
	}
	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void handleEditar(MouseEvent event) {
		if(colNome.getCellData(jtvIngredienteTable.getSelectionModel().getSelectedIndex()) != null){
			ingredienteService = new IngredienteService();
			Ingrediente ingrediente = jtvIngredienteTable.getSelectionModel().getSelectedItem();
			ScreenUtils.abrirNovaJanela("fxml/Compras/NovoIngrediente.fxml", ingrediente);
		}else{
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
//			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}
	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void handleExcluir(MouseEvent event) {
		if(colNome.getCellData(jtvIngredienteTable.getSelectionModel().getSelectedIndex()) != null){
			ingredienteService = new IngredienteService();
			Ingrediente ingrediente = jtvIngredienteTable.getSelectionModel().getSelectedItem();
			ingredienteService.delete(ingrediente.getId());
			atualizarTable();
		}else{
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
//			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		internacionalizar();
		colNome.setCellValueFactory(new PropertyValueFactory<>("descricaoIngrediente"));
		colUnidadeMedida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ingrediente,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Ingrediente, String> param) {
				return new SimpleStringProperty(param.getValue().getUnidadeMedida().getDescricao());
			}
		});
		jtvIngredienteTable.setItems(preencheTabela());
	}
	
	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_alterar_ingrediente"));
		colNome.setText(Internationalization.getMessage("campo_nome"));
		colUnidadeMedida.setText(Internationalization.getMessage("campo_unidade_medida"));
		jlEditar.setText(Internationalization.getMessage("botao_editar"));
		jlExcluir.setText(Internationalization.getMessage("botao_excluir"));
	}
	private ObservableList<Ingrediente> preencheTabela() {
		IngredienteService ingredienteService = new IngredienteService();
		List<Ingrediente> listIngrediente = ingredienteService.findAll();
		ingredientes = FXCollections.observableArrayList();
		for(Ingrediente ingrediente : listIngrediente){
			ingredientes.add(ingrediente);
		}
		return ingredientes;
	}
	
	private void atualizarTable() {
		ingredientes.clear();
		jtvIngredienteTable.setItems(preencheTabela());
	}
}
