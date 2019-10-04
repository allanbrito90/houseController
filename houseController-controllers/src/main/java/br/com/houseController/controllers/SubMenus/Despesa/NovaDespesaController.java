package br.com.houseController.controllers.SubMenus.Despesa;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.Enums.EnumCategoria;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Despesa.DespesaService;
import br.com.houseController.service.Receita.ReceitaService;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class NovaDespesaController extends ParametrosObjetos implements Initializable{
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private VBox vbValor;
	
	@FXML
	private VBox vbVariavel;
	
	@FXML
	private JFXComboBox<String> jcbCategoria;
	
	@FXML
	private JFXDatePicker jdpDtPagamento;
	
	@FXML
	private JFXDatePicker jdpDtVencimento;
	
	@FXML
	private JFXComboBox<String> jcbUsuario;
	
	@FXML
	private JFXTextArea jtaDescricao;
	
	@FXML
	private JFXCheckBox jcbRepetirConta;
	
	@FXML
	private Spinner<Integer> jsRepetirMes;
	
	@FXML
	private Spinner<Integer> jsRepetirAno;
	
	@FXML 
	private Label jlTitulo;
	
	@FXML
	private AnchorPane jbAdd;
	
	//TODO Colocar estes statics numa classe fixa para acesso por todos
	final static int MINANO = 2000;
	final static int MAXANO = 3000;
	final static int MINMES = 1;
	final static int MAXMES = 12;
	
	SpinnerValueFactory<Integer> mesSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINMES, MAXMES, MINMES);
	SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO, MINANO);

	NumberTextField jntfValor = new NumberTextField();
	
	HashMap<String,Usuario> mapUsuario = new HashMap<>();
	
	Despesa despesa;
	
	@FXML
	private void handleSalvar(){
		//Verifica se a categoria for variável para não gravar erroneamente o valor de uma data de vencimento
		if(despesa.getCategoria().name() == EnumCategoria.VARIAVEL.name()){
			despesa.setDtVencimento(null);
		}
		
		//Verifica se caso estes campos não tiverem sido alterados o sistema seta o valor que estiver no ComboBox no momento
		if(despesa.getUsuario() == null){
			despesa.setUsuario(mapUsuario.get(0));
		}
		
		//Salva a instância
		DespesaService despesaService = new DespesaService();
		despesaService.insert(despesa);
		
		//Verifica se há receita cadastrada para atualização
		
		//Verifica se precisa repetir conta
		if(jcbRepetirConta.isSelected()){
		Despesa despesa2;
			

		//Cria data de final da repetição
		LocalDate localDateFinal = LocalDate.of(jsRepetirAno.getValue(), jsRepetirMes.getValue(), 1);
		LocalDate localDateAtual = jdpDtVencimento.getValue();
		
		//Verifica se data é depois da do vencimento
			if(localDateFinal.isAfter(localDateAtual)){
		
		//Caso seja, adiciona um mês a data de vencimento
				while(localDateFinal.isAfter(localDateAtual)){
					despesa2 = new Despesa();//Uma nova despesa é criada para que haja um novo endereçamento na memória, caso fosse a mesma instância, o Hibernate atualizaria ao invés de adicionar um novo registro
					despesa2 = despesa;
					despesa2.setId(null);
					localDateAtual = localDateAtual.plusMonths(1);
					despesa2.setDtVencimento(localDateAtual);
		
		//Salva a informação
					despesaService.insert(despesa2);
				}
			}
		}
		
	}
	
	@FXML
	private void handleLimpar(){
		ScreenUtils.limparCampos(jtaDescricao,jntfValor);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vbValor.getChildren().add(jntfValor);
		
		inicializaCampos();
		inicializaListeners();
		
	}
	

	private void inicializaCampos() {
		//Coloca valores no campo de Categorias e setando para o primeiro
		jcbCategoria.getItems().add("Variável");
		jcbCategoria.getItems().add("Fixa");
		jcbCategoria.getSelectionModel().select(0);
		
		//Coloca valores no campo de Usuários e setando para o primeiro
		UsuarioService usuarioService = new UsuarioService();
		List<Usuario> listUsuario = usuarioService.findAll();
		for(Usuario usuario : listUsuario){
			mapUsuario.put(usuario.getNome(), usuario);
			jcbUsuario.getItems().add(usuario.getNome());
		}
		jcbUsuario.getSelectionModel().select(0);
		
		//Setando campo de data do pagamento para o dia corrente
		jdpDtPagamento.setValue(LocalDate.now());
		jdpDtVencimento.setValue(LocalDate.now());
		
		jsRepetirMes.setValueFactory(mesSpinner);
		jsRepetirAno.setValueFactory(anoSpinner);
		jsRepetirMes.getValueFactory().setValue(LocalDate.now().getMonthValue());
		jsRepetirAno.getValueFactory().setValue(LocalDate.now().getYear());
		
		Platform.runLater(()->{
			if(getObjetos() != null){
				jlTitulo.setText("Editar Despesa");
				despesa = (Despesa) getObjetos().get(0);
//				jcbCategoria.getSelectionModel().select(despesa.getCategoria().name());
				if(despesa.getCategoria().name().toUpperCase() == EnumCategoria.FIXA.name()){
					jcbCategoria.getSelectionModel().select(1);
				}else{
					jcbCategoria.getSelectionModel().select(0);
				}
				jntfValor.setText(despesa.getValorDespesa().setScale(2, RoundingMode.HALF_EVEN).toString());
//				jcbPago.setSelected(despesa.getPago() != null ? despesa.getPago() : false);
				if(despesa.getUsuario() != null){
					jcbUsuario.getSelectionModel().select(despesa.getUsuario().getNome());
				}
				jtaDescricao.setText(despesa.getDescricaoDespesa());
				if (despesa.getDtVencimento() != null) {
					jdpDtPagamento.setValue(despesa.getDtPagamento());
				}
				if (despesa.getDtVencimento() != null) {
					jdpDtVencimento.setValue(despesa.getDtVencimento());
				}
			}else{
				despesa = new Despesa();
				despesa.setCategoria(EnumCategoria.VARIAVEL);
				despesa.setDescricaoDespesa("");
				despesa.setDtPagamento(LocalDate.now());
				despesa.setDtVencimento(LocalDate.now());
				despesa.setPago(false);
				despesa.setValorDespesa(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN));
			}
		});
		
		ReceitaService receitaService = new ReceitaService();
		List<Receita> listReceita = receitaService.findAll();
		
//		for(Receita receita : listReceita){
//			jcbReceita.getItems().add(receita);
//		}
		
	}
	
	private void inicializaListeners(){
		jcbCategoria.valueProperty().addListener((obs,oldV,newV)->{
			if(newV.toUpperCase().equals(EnumCategoria.FIXA.name())){
				vbVariavel.setVisible(true);
				despesa.setCategoria(EnumCategoria.FIXA);
			}else{
				vbVariavel.setVisible(false);			
				despesa.setCategoria(EnumCategoria.VARIAVEL);
			}
		});
//		jcbReceita.setCellFactory(new Callback<ListView<Receita>, ListCell<Receita>>() {
//			
//			@Override
//			public ListCell<Receita> call(ListView<Receita> param) {
//				final ListCell<Receita> cell = new ListCell<Receita>(){
//					
//					@Override
//					protected void updateItem(Receita t, boolean b){
//						super.updateItem(t, b);
//						
//						if(t!=null){
//							setText(t.getDescricaoPagamento() + " - " +  t.getValor().toString());
//						}else{
//							setText(null);
//						}
//					}
//				};
//				return cell;
//			}
//		});
//		jcbReceita.selectionModelProperty().addListener((obs,oldV,newV)->{jcbReceita.getSelectionModel().select(jcbReceita.getSelectionModel().getSelectedItem().getDescricaoPagamento() + " - " + jcbReceita.getSelectionModel().getSelectedItem().getValor().toString());});
		jtaDescricao.textProperty().addListener((obs,oldV,newV)->{despesa.setDescricaoDespesa(newV);});
		jntfValor.textProperty().addListener((obs,oldV,newV)->{despesa.setValorDespesa(new BigDecimal(newV).setScale(2, RoundingMode.HALF_EVEN));});
		jcbUsuario.valueProperty().addListener((obs,oldV,newV) -> {despesa.setUsuario(mapUsuario.get(newV));});
		jdpDtPagamento.valueProperty().addListener((obs,oldV,newV)-> {despesa.setDtPagamento(newV);});
		jdpDtVencimento.valueProperty().addListener((obs,oldV,newV)-> {despesa.setDtVencimento(newV);});
//		jcbPago.selectedProperty().addListener((obs,oldV,newV)->{despesa.setPago(newV);});
		
	}
	
	
}
