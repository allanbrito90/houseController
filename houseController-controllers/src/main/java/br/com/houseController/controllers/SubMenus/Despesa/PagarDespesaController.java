package br.com.houseController.controllers.SubMenus.Despesa;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.PrincipalController;
import br.com.houseController.controllers.dialogs.AdicionarPagamentoController;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.despesas.RelacaoDespesaReceita;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.service.Despesa.RelacaoDespesaReceitaService;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;

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
	
	@FXML
	private VBox vbAddReceitas;
	
	RelacaoDespesaReceitaService relacaoDespesaReceitaService = new RelacaoDespesaReceitaService();
	ReceitaService receitaService = new ReceitaService();
	
	HashMap<Integer, RelacaoDespesaReceita> mapRelacaoDespesaReceita = new HashMap<>();
	HashMap<Integer, RelacaoDespesaReceita> mapRelacaoDespesaReceitaFixa = new HashMap<>();
	int indice = 0;
	
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
		
		if(mapRelacaoDespesaReceita.size()==0){
			relacaoDespesaReceitaService.deleteReceitaByIdDespesa(jcbDespesa.getSelectionModel().getSelectedItem().getId());
			return;
		}
		
		
		for(RelacaoDespesaReceita relacaoDespesaReceita : mapRelacaoDespesaReceita.values()){
			
			relacaoDespesaReceitaService.insert(relacaoDespesaReceita);
		}
		
		if (mapRelacaoDespesaReceitaFixa.size()>0) {
			for (int i = 0; i <= indice - 1; i++) {
				if (mapRelacaoDespesaReceita.get(i) == null) {
					relacaoDespesaReceitaService.delete(mapRelacaoDespesaReceitaFixa.get(i).getId());
				}
			} 
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
		if(jlValorRestante.getText().equals("0.00")){
			ScreenUtils.janelaInformação(spDialog, "Conta paga", "Não é possível adicionar mais pagamentos a esta conta pois ela já está paga", "Nem tinha visto");
			return;
		}else{
			Task<RelacaoDespesaReceita> taskRelacaoDespesaReceita = new Task<RelacaoDespesaReceita>(){
	
				@Override
				protected RelacaoDespesaReceita call() throws Exception {
					return ScreenUtils.abrirJanelaPopUp(getClass(),jcbDespesa.getSelectionModel().getSelectedItem(), new BigDecimal(jlValorRestante.getText()));
				}
				
			};
			
			taskRelacaoDespesaReceita.setOnSucceeded((e)->{
				try {
						RelacaoDespesaReceita relacaoDespesaReceita = taskRelacaoDespesaReceita.get();
						if(relacaoDespesaReceita.getIdReceita() != 0){
							System.out.println("Colocou receita");
							criaReceita(++indice, relacaoDespesaReceita);
							
						}else{
							System.out.println("Não colocou receita");
						}
						System.out.println("ID DESPESA:" + relacaoDespesaReceita.getIdDespesa());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
			taskRelacaoDespesaReceita.run();
		}
	}

	private void criaReceita(int indice, RelacaoDespesaReceita relacaoDespesaReceita) {
		// Adiciona a nova Receita na lista
		mapRelacaoDespesaReceita.put(indice, relacaoDespesaReceita);
		
		//Cria o HBox para inserção dos campos
		HBox hBox = new HBox();
		
		//Adiciona o HBox no VBox vbReceitas
		vbReceitas.getChildren().add(hBox);
		
		Label nomeReceita = new Label();
		Receita receita = receitaService.findReceitaById(relacaoDespesaReceita.getIdReceita());
		nomeReceita.setText(receita.getDescricaoPagamento());
		hBox.getChildren().add(nomeReceita);
		
		//Consulta as receitas 
		ReceitaService receitaService = new ReceitaService();
		
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
			if(!jlValor.getText().isEmpty()){
				total = total.subtract(new BigDecimal(jlValor.getText()));
			}
			atualizaValorRestante();
		});
		
		jlValor.setText(relacaoDespesaReceita.getValor().setScale(2, RoundingMode.HALF_EVEN).toString());
		total = total.add(relacaoDespesaReceita.getValor().setScale(2, RoundingMode.HALF_EVEN));
		atualizaValorRestante();
		mapRelacaoDespesaReceita.put(indice, relacaoDespesaReceita);
		
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
		
		jcbDespesa.setConverter(new StringConverter<Despesa>() {			
			
			@Override
			public String toString(Despesa object) {
				if(object == null) {
					return null;
				}else {
					return object.getDescricaoDespesa() + " - " + object.getValorDespesa().toString();
				}
			}
			
			@Override
			public Despesa fromString(String string) {
				return null;
			}
		});
		
			
	}
	
	private void inicializaListeners(){
		//Inicializa Combobox da Despesa para que quando seja clicado carregue todas as receitas atribuidas.
		jcbDespesa.valueProperty().addListener((obs,oldV,newV)->{
			if(jcbDespesa.getSelectionModel().getSelectedItem() == null){
				vbAddReceitas.setVisible(false);
				return;
			}
			vbAddReceitas.setVisible(true);
			vbReceitas.getChildren().clear();
			mapRelacaoDespesaReceitaFixa.clear();
			indice=-1;
			total = BigDecimal.ZERO;
			for(RelacaoDespesaReceita relacaoDespesaReceita : relacaoDespesaReceitaService.findReceitaByDespesa(newV.getId())){
				Receita receita = new Receita();
				receita.setId(relacaoDespesaReceita.getIdReceita());
				receita = receitaService.findReceitaById(receita);
//				total = total.add(relacaoDespesaReceita.getValor());
				mapRelacaoDespesaReceitaFixa.put(++indice, relacaoDespesaReceita);
				criaReceita(indice, relacaoDespesaReceita);
			}
			jlValorRestante.setText(newV.getValorDespesa().subtract(total).setScale(2,RoundingMode.HALF_EVEN).toString());
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
