package br.com.houseController.controllers.SubMenus.Receita;

import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

public class AlterarReceitaController extends ParametrosObjetos implements Initializable{
	
	@FXML
	StackPane spDialog;
	
	@FXML
	TableView<Receita> jtvReceitaTable;
	
	@FXML
	TableColumn<Receita, String> colData;
	
	@FXML
	TableColumn<Receita, BigDecimal> colValor;
	
	@FXML
	TableColumn<Receita, String> colDescricao;
	
	ReceitaService receitaService = new ReceitaService();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Receita,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Receita, String> param) {
				return new SimpleStringProperty(param.getValue().getDtPagamento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			}
		});
		colValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
		colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoPagamento"));
		preencheTabela();
	}

	private void preencheTabela() {
		ReceitaService receitaService = new ReceitaService();
		List<Receita> listaReceitas = receitaService.findAll();
		for(Receita receita : listaReceitas){
			jtvReceitaTable.getItems().add(receita);
		}
	}
	
	@FXML
	public void handleEditar(){
		try{
			if(colDescricao.getCellData(jtvReceitaTable.getSelectionModel().getSelectedIndex()) != null){
				Receita receita = receitaService.findOne(jtvReceitaTable.getSelectionModel().getSelectedItem());
				ScreenUtils.abrirNovaJanela("fxml/Receita/NovaReceita.fxml", receita);
			}else{
				ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleExcluir(){
		if(colDescricao.getCellData(jtvReceitaTable.getSelectionModel().getSelectedIndex()) != null){
			Receita receita = receitaService.findOne(jtvReceitaTable.getSelectionModel().getSelectedItem());
			receitaService.delete(receita.getId());
			atualizarTable();
		}else{
			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}

	private void atualizarTable() {
		jtvReceitaTable.getItems().clear();
		preencheTabela();
	}
	

}
