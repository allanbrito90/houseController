package br.com.houseController.controllers.SubMenus.Meta;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
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
import javafx.scene.control.Label;

public class AlterarMetaController implements Initializable{
	
	@FXML
	private Label jlTitulo;
	
	@FXML
	private Label jlEditar;
	
	@FXML
	private Label jlExcluir;
	
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
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
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
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
		}
	}

	private void atualizaTabela() {
		jtvMetas.getItems().removeAll();
		preencheTabela();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		internacionalizar();
		
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
//					return new SimpleStringProperty("Faltam " + String.valueOf(param.getValue().getTempo()*-1) + " dias");
					return new SimpleStringProperty(Internationalization.getMessage("meta_restante", String.valueOf(param.getValue().getTempo()*-1)));
				}else if(param.getValue().getTempo() > 0){
//					return new SimpleStringProperty("Vencido à " + String.valueOf(param.getValue().getTempo()) + " dias");
					return new SimpleStringProperty(Internationalization.getMessage("meta_vencida", String.valueOf(param.getValue().getTempo())));
				}else{					
//					return new SimpleStringProperty("Vence hoje");
					return new SimpleStringProperty(Internationalization.getMessage("meta_vence_hoje"));
				}
			}
		});
		
	}

	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_alterar_meta"));
		colMeta.setText(Internationalization.getMessage("coluna_meta"));
		colData.setText(Internationalization.getMessage("coluna_data"));
		colUsuario.setText(Internationalization.getMessage("campo_atribuido_a"));
		colDias.setText(Internationalization.getMessage("titulo_col_dias_restantes"));
		jlEditar.setText(Internationalization.getMessage("botao_editar"));
		jlExcluir.setText(Internationalization.getMessage("botao_excluir"));
	}

	private void preencheTabela() {
		ArrayList<MetaTempo> metas = metaService.findAllMetaTempo();
		for (MetaTempo metaTempo : metas) {
			jtvMetas.getItems().add(metaTempo);
		}
	}	

}
