package br.com.houseController.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.meta.Meta.MetaService;
import br.com.houseController.model.despesas.Compras;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.meta.MetaTempo;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.UsuarioLogado;
import br.com.houseController.service.Compras.ComprasService;
import br.com.houseController.service.Despesa.DespesaService;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

public class HomeController implements Initializable{
	ReceitaService receitaService = new ReceitaService();
	DespesaService despesaService = new DespesaService();
	ComprasService comprasService = new ComprasService();	
	ArrayList<Receita> listaMesesReceita = new ArrayList<>();
	ArrayList<Despesa> listaMesesDespesa = new ArrayList<>();
	ArrayList<Compras> listaMesesCompras = new ArrayList<>();
	
	@FXML
	private TableColumn<MetaTempo, String> colMeta;
	
	@FXML
	private TableColumn<MetaTempo, String> colDias;
	
	@FXML
	private TableColumn<Despesa, String> colDespesa;
	
	@FXML
	private TableColumn<Despesa, String> colValor;
	
	@FXML
	Label jlSaudacao;
	
	@FXML
	AnchorPane apGrafico1;
	
	@FXML
	AnchorPane apGrafico2;
	
	@FXML
	AnchorPane apGrafico3;
	
	@FXML
	AnchorPane apLista1;
	
	@FXML
	AnchorPane apLista2;
	
	@FXML
	TableView<MetaTempo> jtvMetas;
	
	@FXML
	TableView<Despesa> jtvDespesas;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregaSaudacao();
		primeiroGrafico();
		segundoGrafico();
		terceiroGrafico();
		primeiraLista();
		segundaLista();
	}


	/**
	 * Carrega a saudação que fica no topo da página
	 * 
	 */
	private void carregaSaudacao() {
		String saudacao = "";
		Calendar calendar = Calendar.getInstance();
		UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();
		if(calendar.get(Calendar.HOUR_OF_DAY) < 12){
			saudacao = Internationalization.getMessage("bom_dia");
		}else if(calendar.get(Calendar.HOUR_OF_DAY) < 18){
			saudacao = Internationalization.getMessage("boa_tarde");		
		}else{
			saudacao = Internationalization.getMessage("boa_noite");
		}
		saudacao += ", ";
		jlSaudacao.setText(saudacao + usuarioLogado.getUsuario().getNome());
	}
	
	//1º Gráfico - Receitas e despesas do mês
	@SuppressWarnings("restriction")
	public void primeiroGrafico(){
		//Limpa o array com os valores
		listaMesesDespesa.clear();
		listaMesesReceita.clear();
		//Loop dos últimos 5 meses
		for(int i = 4; i >= 0; i--){
		//Obtém a receita do mês
			LocalDate localDate = LocalDate.now();
			int mes = localDate.getMonthValue() - i;
			int ano = localDate.getYear();
			//Verifica se o valor de mês não será negativo, caso seja ele volta para o Dezembro do ano passado
			if(mes <= 0){
				mes = 12 + mes;
				ano--;
			}
		//Obtém a receita do mês
			ArrayList<Receita> listReceita = receitaService.findAllByMes(mes, ano);
		//Obtém a despesa do mês
			ArrayList<Despesa> listDespesa = despesaService.findAllByMes(mes, ano);
			
		//Soma todos os valores de receita para um valor único
			Receita receitaTotal = new Receita();
			receitaTotal.setDtPagamento(LocalDate.of(ano, mes, 1));
			receitaTotal.setValor(BigDecimal.ZERO);
			for(Receita receita : listReceita){
				receitaTotal.setValor(receitaTotal.getValor().add(receita.getValor()).setScale(2,RoundingMode.HALF_EVEN));
			}
			
		//Soma todos os valores de despesa para um valor único
			Despesa despesaTotal = new Despesa();
			despesaTotal.setDtPagamento(LocalDate.of(ano, mes, 1));
			despesaTotal.setValorDespesa(BigDecimal.ZERO);
			for(Despesa despesa : listDespesa){
				despesaTotal.setValorDespesa(despesaTotal.getValorDespesa().add(despesa.getValorDespesa()).setScale(2,RoundingMode.HALF_EVEN));
			}
			
		//Adiciona dados a lista
			listaMesesReceita.add(receitaTotal);
			listaMesesDespesa.add(despesaTotal);
			
		//Finaliza loop
		}
		//Com os dados obtidos, começa a montagem do gráfico
		
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		
		xAxis.setLabel(Internationalization.getMessage("titulo_mes"));
		yAxis.setLabel(Internationalization.getMessage("titulo_valor"));
		final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		
		lineChart.setTitle(Internationalization.getMessage("ultimas_contas_geral"));
		
		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		series1.setName(Internationalization.getMessage("titulo_receitas"));
		series2.setName(Internationalization.getMessage("titulo_despesas"));
		
		for(int i = 0; i < listaMesesReceita.size(); i++){
			series1.getData().add(new XYChart.Data<>(listaMesesReceita.get(i).getDtPagamento().getMonth().toString(),listaMesesReceita.get(i).getValor().setScale(2,RoundingMode.HALF_EVEN)));
			series2.getData().add(new XYChart.Data<>(listaMesesDespesa.get(i).getDtPagamento().getMonth().toString(),listaMesesDespesa.get(i).getValorDespesa().setScale(2,RoundingMode.HALF_EVEN)));
		}
		
		
		lineChart.getData().add(series1);
		lineChart.getData().add(series2);
		lineChart.setMaxWidth(400);
		apGrafico2.getChildren().add(lineChart);
	}
	
	//2º Gráfico - Receitas e Despesas do usuário
	public void segundoGrafico(){
		//
		listaMesesDespesa.clear();
		listaMesesReceita.clear();
		for(int i=4 ; i>=0; i--){
			LocalDate localDate = LocalDate.now();
			int mes = localDate.getMonthValue() - i;
			int ano = localDate.getYear();
			//Verifica se o valor de mês não será negativo, caso seja ele volta para o Dezembro do ano passado
			if(mes <= 0){
				mes = 12 + mes;
				ano--;
			}
			
			//Obtém a receita do mês
			ArrayList<Receita> listReceita = receitaService.findAllByMesAndUsuario(mes, ano, UsuarioLogado.getInstance().getUsuario());
			//Obtém a despesa do mês
			ArrayList<Despesa> listDespesa = despesaService.findAllByMesAndUsuario(mes, ano, UsuarioLogado.getInstance().getUsuario());
			
			//Soma todos os valores de receita para um valor único
			Receita receitaTotal = new Receita();
			receitaTotal.setDtPagamento(LocalDate.of(ano, mes, 1));
			receitaTotal.setValor(BigDecimal.ZERO);
			for(Receita receita : listReceita){
				receitaTotal.setValor(receitaTotal.getValor().add(receita.getValor()).setScale(2,RoundingMode.HALF_EVEN));
			}
			
		//Soma todos os valores de despesa para um valor único
			Despesa despesaTotal = new Despesa();
			despesaTotal.setDtPagamento(LocalDate.of(ano, mes, 1));
			despesaTotal.setValorDespesa(BigDecimal.ZERO);
			for(Despesa despesa : listDespesa){
				despesaTotal.setValorDespesa(despesaTotal.getValorDespesa().add(despesa.getValorDespesa()).setScale(2,RoundingMode.HALF_EVEN));
			}
			
		//Adiciona dados a lista
			listaMesesReceita.add(receitaTotal);
			listaMesesDespesa.add(despesaTotal);
			
		//Finaliza loop			
		}				
		
		//Com os dados obtidos, começa a montagem do gráfico
		
				final CategoryAxis xAxis = new CategoryAxis();
				final NumberAxis yAxis = new NumberAxis();
				
				xAxis.setLabel(Internationalization.getMessage("titulo_mes"));
				yAxis.setLabel(Internationalization.getMessage("titulo_valor"));
				final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
				
				lineChart.setTitle(Internationalization.getMessage("suas_ultimas_contas"));
				
				XYChart.Series series1 = new XYChart.Series();
				XYChart.Series series2 = new XYChart.Series();
				series1.setName(Internationalization.getMessage("titulo_receitas"));
				series2.setName(Internationalization.getMessage("titulo_despesas"));
				
				for(int i = 0; i < listaMesesReceita.size(); i++){
					series1.getData().add(new XYChart.Data<>(listaMesesReceita.get(i).getDtPagamento().getMonth().toString(),listaMesesReceita.get(i).getValor().setScale(2,RoundingMode.HALF_EVEN)));
					series2.getData().add(new XYChart.Data<>(listaMesesDespesa.get(i).getDtPagamento().getMonth().toString(),listaMesesDespesa.get(i).getValorDespesa().setScale(2,RoundingMode.HALF_EVEN)));
				}
				
				
				lineChart.getData().add(series1);
				lineChart.getData().add(series2);
				lineChart.setMaxWidth(400);
				apGrafico1.getChildren().add(lineChart);
		
	}
	
	private void terceiroGrafico() {
		//
		listaMesesDespesa.clear();
		listaMesesReceita.clear();
		for(int i=4 ; i>=0; i--){
			LocalDate localDate = LocalDate.now();
			int mes = localDate.getMonthValue() - i;
			int ano = localDate.getYear();
			//Verifica se o valor de mês não será negativo, caso seja ele volta para o Dezembro do ano passado
			if(mes <= 0){
				mes = 12 + mes;
				ano--;
			}
			
		//Pesquisa a Compra do Mês
			Compras compras = comprasService.findOneByMonthAndYear(mes, ano);
			if(compras==null){
				compras = new Compras();
				compras.setPeriodoReferencia(localDate.of(ano, mes, 1));
				compras.setValorDespesa(BigDecimal.ZERO);
			}
		//Adiciona dados a lista
			
			listaMesesCompras.add(compras);
			
		//Finaliza loop			
		}				
		
		//Com os dados obtidos, começa a montagem do gráfico 
		
				final CategoryAxis xAxis = new CategoryAxis();
				final NumberAxis yAxis = new NumberAxis();
				
				xAxis.setLabel(Internationalization.getMessage("titulo_mes"));
				yAxis.setLabel(Internationalization.getMessage("titulo_valor"));
				final BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
				
				barChart.setTitle(Internationalization.getMessage("ultimas_compras"));
				
				XYChart.Series series1 = new XYChart.Series();
				series1.setName(Internationalization.getMessage("ultimas_compras"));
				
				for(int i = 0; i < listaMesesCompras.size(); i++){
					series1.getData().add(new XYChart.Data<>(listaMesesCompras.get(i).getPeriodoReferencia().getMonth().toString(),listaMesesCompras.get(i).getValorDespesa().setScale(2,RoundingMode.HALF_EVEN)));
				}
				
				
				barChart.getData().add(series1);
				barChart.setMaxWidth(400);
				apGrafico3.getChildren().add(barChart);
		
	
		
	}
	
	//1ª Lista - Últimas Metas
	public void primeiraLista(){
		//Carrega Lista de Metas do Usuário
		MetaService metaService = new MetaService();
		ArrayList<MetaTempo> listMeta = metaService.findAllMetaTempoByUsuario(UsuarioLogado.getInstance().getUsuario());
		
		//Se não houverem metas cadastradas, não mostra nada (do AnchorPane)
		if(listMeta != null){
		//Itera a lista e adiciona na tabela
			for(MetaTempo metaTempo: listMeta){
				jtvMetas.getItems().add(metaTempo);
			}
		}
		
		colMeta.setText(Internationalization.getMessage("botao_metas"));
		colDias.setText(Internationalization.getMessage("titulo_col_dias_restantes"));
		colMeta.setCellValueFactory(new PropertyValueFactory<>("titulo"));
		colDias.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<MetaTempo,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<MetaTempo, String> param) {
				if(param.getValue().getTempo() < 0){
						return new SimpleStringProperty(Internationalization.getMessage("meta_restante", String.valueOf(param.getValue().getTempo()*-1)));
//						return new SimpleStringProperty("Faltam " + String.valueOf(param.getValue().getTempo()*-1) + " dias");
				}else if(param.getValue().getTempo() > 0){
					return new SimpleStringProperty(Internationalization.getMessage("meta_vencida", String.valueOf(param.getValue().getTempo())));
//					return new SimpleStringProperty("Vencido à " + String.valueOf(param.getValue().getTempo()) + " dias");
				}else{					
					return new SimpleStringProperty(Internationalization.getMessage("meta_vence_hoje"));
//					return new SimpleStringProperty("Vence hoje");
				}
			}
		});
	}
	
	public void segundaLista(){
		//Carrega Data
		LocalDate localDate = LocalDate.now();
		//Carrega Lista de Contas do Usuário
		DespesaService despesaService = new DespesaService();
		ArrayList<Despesa> listDespesa = despesaService.findAllByMesAndUsuario(localDate.getMonthValue(), localDate.getYear(), UsuarioLogado.getInstance().getUsuario());
		//Se não houverem contas cadastradas, não mostra nada
		if(listDespesa != null){
			//Itera a lista e adiciona na tabela
			for(Despesa despesa : listDespesa){
				jtvDespesas.getItems().add(despesa);
			}
		}
		
		colDespesa.setText(Internationalization.getMessage("botao_despesas"));
		colValor.setText(Internationalization.getMessage("titulo_valor"));
		colDespesa.setCellValueFactory(new PropertyValueFactory<>("descricaoDespesa"));
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
	}

}
