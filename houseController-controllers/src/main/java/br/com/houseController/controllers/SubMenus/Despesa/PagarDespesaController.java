package br.com.houseController.controllers.SubMenus.Despesa;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.service.Receita.ReceitaService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
	
	HashMap<Integer, Receita> mapReceita = new HashMap<>();
	int indice = -1;
	
	//TODO Colocar estes statics numa classe fixa para acesso por todos
	final static int MINANO = 2000;
	final static int MAXANO = 3000;
	final static int MINMES = 1;
	final static int MAXMES = 12;
	
	SpinnerValueFactory<Integer> mesSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINMES, MAXMES, MINMES);
	SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO, MINANO);

	Despesa despesa;
	
	
	@FXML
	private void handlePesquisar(){
		
	}
	
	@FXML
	private void handleSalvar(){
		
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
		criaReceita(++indice, new Receita());
	}

	private void criaReceita(int indice, Receita receita) {
		// Adiciona a nova Receita na lista
		mapReceita.put(indice, receita);
		
		//Cria o HBox para inserção dos campos
		HBox hBox = new HBox();
		
		//Adiciona o HBox no VBox vbReceitas
		vbReceitas.getChildren().add(hBox);
		
		//Cria o ComboBox que ficará com a lista de receitas
		JFXComboBox<Receita> jcbReceita = new JFXComboBox<>();
		
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
//			ScreenUtils.janelaInformação(spDialog, "Janela para deleção", "Deletar?", "É isso aí");
			vbReceitas.getChildren().remove(hBox);
			mapReceita.remove(indice);	
		});
		
		//Adiciona o Listener para que, sempre que selecionado uma nova receita, abra-se uma janela dizendo o quanto há daquela receita disponível
		jcbReceita.valueProperty().addListener((obs,oldV,newV) -> {
			Task<BigDecimal> task = new Task<BigDecimal>(){

				@Override
				protected BigDecimal call() throws Exception {
					//Chama a janela para inserção do texto
					return null;
				}
				
			};
			
			task.setOnRunning((e)->{
				 try {
					if(task.get() != BigDecimal.ZERO){
						 
					 }
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			});
			
		});
		
		//O retorno dessa janela irá adicionar o valor digitado no label campo
		
		//Caso o valor seja 0, retorna a seleção da receita para 0
	}

	private void inicializaCampos() {
				
		Platform.runLater(()->{
			if(getObjetos() != null){
				jlTitulo.setText("Editar Despesa");
				despesa = (Despesa) getObjetos().get(0);
				
			}else{
				
			}
		});
		
		
	}
	
	private void inicializaListeners(){
		//Inicializa Listeners do Spinner
		jsMes.setValueFactory(mesSpinner);
		jsAno.setValueFactory(anoSpinner);
		
		jsMes.getValueFactory().setValue(LocalDate.now().getMonthValue());
		jsAno.getValueFactory().setValue(LocalDate.now().getYear());
	}
	
	
}
