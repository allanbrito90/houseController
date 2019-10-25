package br.com.houseController.controllers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.ResourceBundle;

import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.UsuarioLogado;
import br.com.houseController.service.Despesa.DespesaService;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class HomeController implements Initializable{
	ReceitaService receitaService = new ReceitaService();
	DespesaService despesaService = new DespesaService();
	ArrayList<Receita> listaMesesReceita = new ArrayList<>();
	ArrayList<Despesa> listaMesesDespesa = new ArrayList<>();
	
	@FXML
	Label jlSaudacao;
	
	@FXML
	AnchorPane apGrafico1;
	
	@FXML
	AnchorPane apGrafico2;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		carregaSaudacao();
		primeiroGrafico();
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
			saudacao = "Bom Dia, ";
		}else if(calendar.get(Calendar.HOUR_OF_DAY) < 18){
			saudacao = "Boa Tarde, ";		
		}else{
			saudacao = "Boa Noite, ";
		}
		
		jlSaudacao.setText(saudacao + usuarioLogado.getUsuario().getNome());
	}
	
	//1º Gráfico - Receitas e despesas do mês
	@SuppressWarnings("restriction")
	public void primeiroGrafico(){
		//Loop dos últimos 5 meses
		for(int i = 4; i >= 0; i--){
		//Obtém a receita do mês
			LocalDate localDate = LocalDate.now();
			int mes = localDate.getMonthValue() - i;
			int ano = localDate.getYear();
			//Verifica se o valor de mês não será negativo, caso seja ele volta para o Dezembro do ano passado
			if(mes < 0){
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
		
		xAxis.setLabel("Mês");
		yAxis.setLabel("Valor");
		final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		
		lineChart.setTitle("Últimas contas (GERAL)");
		
		XYChart.Series series1 = new XYChart.Series();
		XYChart.Series series2 = new XYChart.Series();
		series1.setName("Receitas");
		series2.setName("Despesas");
		
		for(int i = 0; i < listaMesesReceita.size(); i++){
			series1.getData().add(new XYChart.Data<>(listaMesesReceita.get(i).getDtPagamento().getMonth().toString(),listaMesesReceita.get(i).getValor().setScale(2,RoundingMode.HALF_EVEN)));
			series2.getData().add(new XYChart.Data<>(listaMesesDespesa.get(i).getDtPagamento().getMonth().toString(),listaMesesDespesa.get(i).getValorDespesa().setScale(2,RoundingMode.HALF_EVEN)));
		}
		
		
		lineChart.getData().add(series1);
		lineChart.getData().add(series2);
		apGrafico1.getChildren().add(lineChart);
		apGrafico2.getChildren().add(lineChart);
	}
	
	//2º Gráfico - Receitas e Despesas do usuário
	public void segundoGrafico(){
		//
		for(int i=4 ; i>=0; i--){
			
		}
	}
	
	
	
	

}
