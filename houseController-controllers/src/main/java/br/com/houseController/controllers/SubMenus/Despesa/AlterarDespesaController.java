package br.com.houseController.controllers.SubMenus.Despesa;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.meta.Meta.MetaService;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.meta.Meta;
import br.com.houseController.model.meta.MetaTempo;
import br.com.houseController.service.Despesa.DespesaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javafx.scene.control.Label;

public class AlterarDespesaController implements Initializable{
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private TableView<Despesa> jtvDespesas;
	
	@FXML
	private TableColumn<Despesa, String> colDesc;
	
	@FXML
	private TableColumn<Despesa, String> colValor;
	
	@FXML
	private TableColumn<Despesa, String> colDtPagamento;
	
	@FXML
	private TableColumn<Despesa, String> colDtVencimento;
	
	@FXML
	private Spinner<Integer> jsMes;
	
	@FXML
	private Spinner<Integer> jsAno;
	
	@FXML 
	private Label jlTitulo;
	
	@FXML
	private Label jlPeriodo;
	
	@FXML
	private JFXButton jbPesquisar;
	
	@FXML
	private Label jlEditar;
	
	@FXML
	private Label jlExcluir;
	
	DespesaService despesaService = new DespesaService();
	
	//TODO Colocar estes statics numa classe fixa para acesso por todos
		final static int MINANO = 2000;
		final static int MAXANO = 3000;
		final static int MINMES = 1;
		final static int MAXMES = 12;
		
		SpinnerValueFactory<Integer> mesSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINMES, MAXMES, MINMES);
		SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO, MINANO);

		
	
	@FXML
	private void handleEditar(){
			if (colDesc.getCellData(jtvDespesas.getSelectionModel().getSelectedIndex()) != null) {
				if (jtvDespesas.getSelectionModel().getSelectedItem().isCompras()) {
					ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro4"), Internationalization.getMessage("nao_alterar_compras"), Internationalization.getMessage("erro_button3"));
					return;
				}
				Despesa despesa = jtvDespesas.getSelectionModel().getSelectedItem();
				ScreenUtils.abrirNovaJanela("fxml/Despesa/NovaDespesa.fxml", despesa);
			} else {
			} 
	}
	


	@FXML
	private void handleExcluir(){
		if(colDesc.getCellData(jtvDespesas.getSelectionModel().getSelectedIndex()) != null){
			if (jtvDespesas.getSelectionModel().getSelectedItem().isCompras()) {
				ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro4"), Internationalization.getMessage("nao_excluir_compras"), Internationalization.getMessage("erro_button3"));
//				ScreenUtils.janelaInformação(spDialog, "Oh-oh", "Não se pode excluir compras!", "Fechado");
				return;
			}
			Despesa despesa = jtvDespesas.getSelectionModel().getSelectedItem();
			despesaService.delete(despesa.getId());
			atualizaTabela();
		}else{
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
//			ScreenUtils.janelaInformação(spDialog, "Ops", "Por favor, selecione um item.", "Sem problemas");
		}
	}
	
	@FXML
	private void handlePesquisar(){
		atualizaTabela();
	}

	private void atualizaTabela() {
		jtvDespesas.getItems().removeAll();
		jtvDespesas.getItems().clear();
		preencheTabela(jsMes.getValue(), jsAno.getValue());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		internacionalizar();
		//Pega dados e coloca na tabela
		preencheTabela(LocalDate.now().getMonthValue(),LocalDate.now().getYear());	
		
		//Inicializa Listeners
		jsMes.setValueFactory(mesSpinner);
		jsAno.setValueFactory(anoSpinner);
		
		jsMes.getValueFactory().setValue(LocalDate.now().getMonthValue());
		jsAno.getValueFactory().setValue(LocalDate.now().getYear());
		
		colDesc.setCellValueFactory(new PropertyValueFactory<>("descricaoDespesa"));
		colValor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Despesa,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Despesa, String> param) {
				if(param.getValue().getValorDespesa() != null){
					return new SimpleStringProperty(param.getValue().getValorDespesa().toString());
				}else{
					return new SimpleStringProperty("");
				}
			}
		});
		colDtPagamento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Despesa,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Despesa, String> param) {
				if(param.getValue().getDtPagamento() != null){
					return new SimpleStringProperty(param.getValue().getDtPagamento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
				}else{
					return new SimpleStringProperty("");
				}					
			}
		});
		colDtVencimento.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Despesa,String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<Despesa, String> param) {
				if(param.getValue().getDtVencimento() != null){
					return new SimpleStringProperty(param.getValue().getDtVencimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString());
				}else{
					return new SimpleStringProperty("");
				}
			}
		});
	}

	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_alterar_despesa"));
		jlPeriodo.setText(Internationalization.getMessage("titulo_periodo"));
		jbPesquisar.setText(Internationalization.getMessage("botao_pesquisar"));
		colDesc.setText(Internationalization.getMessage("campo_descricao"));
		colDtPagamento.setText(Internationalization.getMessage("campo_data_pagamento"));
		colDtVencimento.setText(Internationalization.getMessage("campo_data_vencimento"));
		colValor.setText(Internationalization.getMessage("titulo_valor"));
		jlEditar.setText(Internationalization.getMessage("botao_editar"));
		jlExcluir.setText(Internationalization.getMessage("botao_excluir"));
	}



	private void preencheTabela(int mes, int ano) {
		ArrayList<Despesa> despesas = despesaService.findAllByMes(mes, ano);
		for (Despesa despesa : despesas) {
			jtvDespesas.getItems().add(despesa);
		}
	}	

}
