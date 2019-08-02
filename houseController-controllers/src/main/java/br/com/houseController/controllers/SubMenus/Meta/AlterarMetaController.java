package br.com.houseController.controllers.SubMenus.Meta;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.meta.Meta.MetaService;
import br.com.houseController.model.meta.Meta;
import br.com.houseController.model.meta.MetaTempo;
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

public class AlterarMetaController implements Initializable{
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private TableView<MetaTempo> jtvMetas;
	
	@FXML
	private TableColumn<MetaTempo, String> colMeta;
	
	@FXML
	private TableColumn<MetaTempo, String> colUsuario;
	
	@FXML
	private TableColumn<MetaTempo, String> colData;
	
	@FXML
	private TableColumn<MetaTempo, String> colDias;
	
	MetaService metaService = new MetaService();
	
	
	@FXML
	private void handleEditar(){
		if(colMeta.getCellData(jtvMetas.getSelectionModel().getSelectedIndex()) != null){
			MetaTempo metaTempo = jtvMetas.getSelectionModel().getSelectedItem();
			ScreenUtils.abrirNovaJanela("fxml/Meta/NovaMeta.fxml", converteMeta(metaTempo));
		}else{
			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}
	
	private Meta converteMeta(MetaTempo metaTempo) {
		Meta meta = new Meta();
		meta.setCumprido(metaTempo.isCumprido());
		meta.setDescMeta(metaTempo.getDescMeta());
		meta.setDtConclusao(metaTempo.getDtConclusao());
		meta.setDtInicial(metaTempo.getDtInicial());
		meta.setId(metaTempo.getId());
		meta.setTitulo(metaTempo.getTitulo());
		meta.setUsuario(metaTempo.getUsuario());
		return meta;
	}

	@FXML
	private void handleExcluir(){
		if(colMeta.getCellData(jtvMetas.getSelectionModel().getSelectedIndex()) != null){
			MetaTempo metaTempo = jtvMetas.getSelectionModel().getSelectedItem();
			metaService.delete(metaTempo.getId());
			atualizaTabela();
		}else{
			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}

	private void atualizaTabela() {
		jtvMetas.getItems().removeAll();
		preencheTabela();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		preencheTabela();	
		colMeta.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		colUsuario.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetaTempo,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<MetaTempo, String> param) {
				return new SimpleStringProperty(param.getValue().getUsuario().getNome());
			}
		});
		colData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetaTempo,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<MetaTempo, String> param) {
				return new SimpleStringProperty(param.getValue().getDtConclusao().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
			}
		});
		colDias.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetaTempo,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<MetaTempo, String> param) {
				if(param.getValue().getTempo() < 0){
					return new SimpleStringProperty("Faltam " + String.valueOf(param.getValue().getTempo()*-1) + " dias");
				}else if(param.getValue().getTempo() > 0){
					return new SimpleStringProperty("Vencido à " + String.valueOf(param.getValue().getTempo()) + " dias");
				}else{					
					return new SimpleStringProperty("Vence hoje");
				}
			}
		});
		
	}

	private void preencheTabela() {
		ArrayList<MetaTempo> metas = metaService.findAllMetaTempo();
		for (MetaTempo metaTempo : metas) {
			jtvMetas.getItems().add(metaTempo);
		}
	}	

}
