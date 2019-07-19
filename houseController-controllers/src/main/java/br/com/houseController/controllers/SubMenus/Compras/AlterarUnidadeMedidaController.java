package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.produto.UnidadeMedida;
import br.com.houseController.service.Compras.UnidadeMedidaService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class AlterarUnidadeMedidaController implements Initializable{
	
	ObservableList<UnidadeMedida> unidadesMedida;
	
	UnidadeMedidaService unidadeMedidaService;
	
	@FXML
	private StackPane spDialog;
	@FXML
	private TableView<UnidadeMedida> jtvUnidadeMedidaTable;
	@FXML
	private TableColumn<UnidadeMedida, String> colNome;

	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void Alterar(MouseEvent event) {
	}
	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void handleEditar(MouseEvent event) {
		if(colNome.getCellData(jtvUnidadeMedidaTable.getSelectionModel().getSelectedIndex()) != null){
			unidadeMedidaService = new UnidadeMedidaService();
			UnidadeMedida unidadeMedida = unidadeMedidaService.findByNome(new UnidadeMedida(colNome.getCellData(jtvUnidadeMedidaTable.getSelectionModel().getSelectedIndex())));
			ScreenUtils.abrirNovaJanela("fxml/Compras/NovaUnidadeMedida.fxml", unidadeMedida);
		}else{
			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}
	// Event Listener on AnchorPane.onMouseClicked
	@FXML
	public void handleExcluir(MouseEvent event) {
		if(colNome.getCellData(jtvUnidadeMedidaTable.getSelectionModel().getSelectedIndex()) != null){
			unidadeMedidaService = new UnidadeMedidaService();
			UnidadeMedida unidadeMedida = unidadeMedidaService.findByNome(new UnidadeMedida(colNome.getCellData(jtvUnidadeMedidaTable.getSelectionModel().getSelectedIndex())));
			unidadeMedidaService.delete(unidadeMedida.getId());
			atualizarTable();
		}else{
			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colNome.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		jtvUnidadeMedidaTable.setItems(preencheTabela());
	}
	
	private ObservableList<UnidadeMedida> preencheTabela() {
		UnidadeMedidaService unidadeMedidaService = new UnidadeMedidaService();
		List<UnidadeMedida> listUnidadeMedida = unidadeMedidaService.findAll();
		unidadesMedida = FXCollections.observableArrayList();
		for(UnidadeMedida unidadeMedida : listUnidadeMedida){
			unidadesMedida.add(unidadeMedida);
		}
		return unidadesMedida;
	}
	
	private void atualizarTable() {
		unidadesMedida.clear();
		jtvUnidadeMedidaTable.setItems(preencheTabela());
	}
}
