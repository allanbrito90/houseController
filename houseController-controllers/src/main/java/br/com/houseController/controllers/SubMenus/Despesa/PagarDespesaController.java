package br.com.houseController.controllers.SubMenus.Despesa;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.despesas.RelacaoDespesaReceita;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.service.Despesa.RelacaoDespesaReceitaService;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class PagarDespesaController extends ParametrosObjetos implements Initializable{
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private Spinner<Integer> jsMes;
	
	@FXML
	private Spinner<Integer> jsAno;
	
	@FXML
	private JFXComboBox<Despesa> jcbDespesa;
	
	@FXML
	private AnchorPane jbAdd;
	
	@FXML
	private VBox vbReceitas;
	
	@FXML
	private Label jlValorRestante;
	
	@FXML 
	private Label jlTitulo;
	
	RelacaoDespesaReceitaService relacaoDespesaReceitaService = new RelacaoDespesaReceitaService();
	ReceitaService receitaService = new ReceitaService();
	
	HashMap<Integer, RelacaoDespesaReceita> mapRelacaoDespesaReceita = new HashMap<>();
	int indice = -1;
	
	//TODO Colocar estes statics numa classe fixa para acesso por todos
	final static int MINANO = 2000;
	final static int MAXANO = 3000;
	final static int MINMES = 1;
	final static int MAXMES = 12;
	
	SpinnerValueFactory<Integer> mesSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINMES, MAXMES, MINMES);
	SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO, MINANO);

	
	BigDecimal total = BigDecimal.ZERO;
	
	
	@FXML
	private void handlePesquisar(){
		pesquisarMes();
	}

	private void pesquisarMes() {
		jcbDespesa.getItems().clear();
		List<Despesa> listRelacaoDespesaReceita = relacaoDespesaReceitaService.findDespesaByPeriodo(jsMes.getValue(), jsAno.getValue());
		for(Despesa despesa : listRelacaoDespesaReceita){
			jcbDespesa.getItems().add(despesa);
		}
	}
	
	@FXML
	private void handleSalvar(){
		Despesa despesa = jcbDespesa.getSelectionModel().getSelectedItem();
		
		for(RelacaoDespesaReceita relacaoDespesaReceita : mapRelacaoDespesaReceita.values()){
			relacaoDespesaReceita.setIdReceita(jcbDespesa.getSelectionModel().getSelectedItem().getId());
			
			relacaoDespesaReceitaService.insert(relacaoDespesaReceita);
		}
		
	}
	
	@FXML
	private void handleLimpar(){
		
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		inicializaCampos();
		inicializaListeners();
		
	}
	
	@FXML
	private void handleAdicionar(){
		criaReceita(++indice, new RelacaoDespesaReceita());
	}

	private void criaReceita(int indice, RelacaoDespesaReceita relacaoDespesaReceita) {
		// Adiciona a nova Receita na lista
		mapRelacaoDespesaReceita.put(indice, relacaoDespesaReceita);
		
		//Cria o HBox para inserção dos campos
		HBox hBox = new HBox();
		
		//Adiciona o HBox no VBox vbReceitas
		vbReceitas.getChildren().add(hBox);
		
		//Cria o ComboBox que ficará com a lista de receitas
		JFXComboBox<Receita> jcbReceita = new JFXComboBox<>();
		jcbReceita.setCellFactory(new Callback<ListView<Receita>, ListCell<Receita>>() {

			@Override
			public ListCell<Receita> call(ListView<Receita> param) {
				final ListCell<Receita> cell = new ListCell<Receita>(){
					
					@Override
					public void updateItem(Receita item, boolean empty){
						super.updateItem(item, empty);
						
						if(item != null){
							setText(item.getDescricaoPagamento() + " - " + item.getValor().toString());
						}else{
							setText("");
						}
						
					}
				};
				return cell;
			}
		});
		
		//Consulta as receitas 
		ReceitaService receitaService = new ReceitaService();
		
		//Adiciona as receitas no ComboBox
		
		for(Receita rct : receitaService.findAll()){
			jcbReceita.getItems().add(rct);
		}		
		
		//Adiciona o ComboBox no HBox
		hBox.getChildren().add(jcbReceita);
		
		//Cria o campo para o valor e adiciona no HBox (Label)
		Label jlValor = new Label();
		jlValor.setPrefWidth(200);
		hBox.getChildren().add(jlValor);
		
		//Cria o Botão para deleção e adiciona no HBox
		JFXButton jbDeletar = new JFXButton("Deletar");
		hBox.getChildren().add(jbDeletar);
		
		//Evento para, quando clicar no botão de deleção, apagar o respectivo pagamento
		jbDeletar.setOnAction((event) -> {
			vbReceitas.getChildren().remove(hBox);
			mapRelacaoDespesaReceita.remove(indice);	
			total = total.subtract(new BigDecimal(jlValor.getText()));
			atualizaValorRestante();
		});
		
		//Adiciona o Listener para que, sempre que selecionado uma nova receita, abra-se uma janela dizendo o quanto há daquela receita disponível
		
		jcbReceita.valueProperty().addListener((obs,oldV,newV) -> {
			
			Task<BigDecimal> task = new Task<BigDecimal>(){

				@Override
				protected BigDecimal call() throws Exception {
					
					//Procura por todas as evidencias da utilização da receita
					ArrayList<RelacaoDespesaReceita> relacaoDespesaReceitaList = relacaoDespesaReceitaService.findAllByReceita(newV.getId());
					BigDecimal receitaRestante = BigDecimal.ZERO;
					for(RelacaoDespesaReceita relacaoDespesaReceita : relacaoDespesaReceitaList){
						receitaRestante = receitaRestante.add(relacaoDespesaReceita.getValor());
					}
					
					//Subtrai o valor da seguinte maneira: TotalReceita - ReceitaBanco( - Receita na Tela)
					receitaRestante = jcbReceita.getSelectionModel().getSelectedItem().getValor().subtract(receitaRestante);
					
					
					return ScreenUtils.janelaEntradaValor(spDialog, receitaRestante, jcbDespesa.getSelectionModel().getSelectedItem().getValorDespesa().subtract(total));
				}
				
			};
			
			//O retorno dessa janela irá adicionar o valor digitado no label campo
			task.setOnSucceeded((e)->{
				 try {
					if(task.get() != BigDecimal.ZERO){
						//Caso o valor seja 0, retorna a seleção da receita para 0
						jlValor.setText(task.get().toString());
						total = total.add(task.get());
						atualizaValorRestante();
					 }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
			task.run();
		});
		
		
	}

	private void inicializaCampos() {
		
		jcbDespesa.setCellFactory(new Callback<ListView<Despesa>, ListCell<Despesa>>() {
			
			@Override
			public ListCell<Despesa> call(ListView<Despesa> param) {
				final ListCell<Despesa> cell = new ListCell<Despesa>(){
					
					@Override
					public void updateItem(Despesa item, boolean empty){
						super.updateItem(item, empty);
						
						if(item != null){
							setText(item.getDescricaoDespesa() + " - " + item.getValorDespesa().toString());
						}else{
							setText("");
						}
						
					}
				};
				return cell;
			}
		});
				
		Platform.runLater(()->{
			if(getObjetos() != null){
				jlTitulo.setText("Editar Despesa");
//				despesa = (Despesa) getObjetos().get(0);
				
			}else{
				
			}
		});
		
			
	}
	
	private void inicializaListeners(){
		//Inicializa Combobox da Despesa para que quando seja clicado carregue todas as receitas atribuidas.
		jcbDespesa.valueProperty().addListener((obs,oldV,newV)->{
			total = BigDecimal.ZERO;
			for(RelacaoDespesaReceita relacaoDespesaReceita : relacaoDespesaReceitaService.findReceitaByDespesa(newV.getId())){
				Receita receita = new Receita();
				receita.setId(relacaoDespesaReceita.getIdReceita());
				receita = receitaService.findReceitaById(receita);
				total = total.add(receita.getValor());
				criaReceita(++indice, relacaoDespesaReceita);
			}
			jlValorRestante.setText(newV.getValorDespesa().subtract(total).toString());
		});
		
		//Inicializa Listeners do Spinner
		jsMes.setValueFactory(mesSpinner);
		jsAno.setValueFactory(anoSpinner);
		
		jsMes.getValueFactory().setValue(LocalDate.now().getMonthValue());
		jsAno.getValueFactory().setValue(LocalDate.now().getYear());
		
		//Carrega despesas do mês vigente
		pesquisarMes();
	}
	
	public void atualizaValorRestante(){
		jlValorRestante.setText(jcbDespesa.getSelectionModel().getSelectedItem().getValorDespesa().subtract(total).toString());
	}
	
}
