package br.com.houseController.controllers.SubMenus.Compras;

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
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.Exceptions.CamposNaoPreenchidosException;
import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.Enums.EnumCategoria;
import br.com.houseController.model.Enums.EnumContaAtiva;
import br.com.houseController.model.despesas.Compras;
import br.com.houseController.model.despesas.Despesa;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.model.produto.Produto;
import br.com.houseController.model.produto.UnidadeMedida;
import br.com.houseController.service.Compras.ComprasService;
import br.com.houseController.service.Despesa.DespesaService;
import br.com.houseController.service.Produto.IngredienteService;
import br.com.houseController.service.Produto.ProdutoService;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.util.StringConverter;

public class NovoProdutoController extends ParametrosObjetos implements Initializable {

	@FXML
	private StackPane spDialog;

	@FXML
	private Label jlTitulo;

	@FXML
	private VBox vbProdutos;

	@FXML
	private AnchorPane jbAdd;

	@FXML
	private Spinner<Integer> jsMes;

	@FXML
	private Spinner<Integer> jsAno;
	
	@FXML
	private Label jlSubtitulo;
	
	@FXML
	private Label jlPeriodo;
	
	@FXML
	private JFXButton jbPesquisar;
	
	@FXML
	private Label jlSalvar;
	

	// TODO Colocar estes statics numa classe fixa para acesso por todos
	final static int MINANO = 2000;
	final static int MAXANO = 3000;
	final static int MINMES = 1;
	final static int MAXMES = 12;

	SpinnerValueFactory<Integer> mesSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINMES, MAXMES,
			MINMES);
	SpinnerValueFactory<Integer> anoSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(MINANO, MAXANO,
			MINANO);

	ObservableList<String> ingredientes;

	ProdutoService produtoService = new ProdutoService();
	IngredienteService ingredienteService = new IngredienteService();

	HashMap<Integer, Produto> mapProduto = new HashMap<>();
	HashMap<Integer, Produto> mapProdutoFixo = new HashMap<>();
	ArrayList<Produto> listProdutos = new ArrayList<>();
	HashMap<String, Ingrediente> mapIngrediente = new HashMap<>();

	int indice = 0;

	@FXML
	public void handleSalvar() {
		Task<Void> taskSalvar = new Task<Void>() {
			@Override
			protected Void call() throws Exception {

				Platform.runLater(()->{
					
				for (int i = 0; i <= indice; i++) {
					if (mapProduto.get(i) != null) {
						if (mapProduto.get(i).getIngrediente() != null && mapProduto.get(i).getQuantidade() != null && mapProduto.get(i).getValor() != null) {
							mapProduto.get(i).setComprado(false);
							mapProduto.get(i).setPeriodoReferencia(LocalDate.of(jsAno.getValue(), jsMes.getValue(), 1));
							listProdutos.add(mapProduto.get(i));
							produtoService.insert(mapProduto.get(i));
						} else {
							System.out.println("Erro");
//							throw new CamposNaoPreenchidosException(Internationalization.getMessage("campos_nao_preenchidos"));
						}
					}
				}
				for (int i = 0; i <= mapProdutoFixo.size(); i++) {
					try {
						if (mapProduto.get(i) == null) {
							listProdutos.remove(mapProduto.get(i));
							produtoService.delete(mapProdutoFixo.get(i).getId());
						}
					} catch (NullPointerException npe) {
						npe.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				armazenaTabelaCompras(jsMes.getValue(), jsAno.getValue());
				carregaListaProdutos(LocalDate.of(jsAno.getValue(), jsMes.getValue(), 1));
				});

				return null;
			}
		};
		
		taskSalvar.setOnRunning(e->{
			Aguarde2.mostrarJanelaAguarde();
		});
		
		taskSalvar.setOnFailed(e->{
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro7"), e.getSource().getException().getMessage(), Internationalization.getMessage("erro_button5"));
		});
		
		taskSalvar.setOnSucceeded(e->{
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_sucesso5"), "Funcionou", Internationalization.getMessage("certo_button6"));
		});
		new Thread(taskSalvar).start();
	}

	private void armazenaTabelaCompras(int mes, int ano) {
		ComprasService comprasService = new ComprasService();

		BigDecimal totalMes = BigDecimal.ZERO;
		for (Produto produto : listProdutos) {
			totalMes = totalMes.add(produto.getValor().multiply(produto.getQuantidade()));
		}
		Compras compras = comprasService.findOneByMonthAndYear(jsMes.getValue(), jsAno.getValue());
		if (compras == null) {
			compras = new Compras();
			compras.setPeriodoReferencia(LocalDate.of(jsAno.getValue(), jsMes.getValue(), 1));
		}
		compras.setValorDespesa(totalMes);
		comprasService.insert(compras);

		// Cria instância de despesaService e despesa
		DespesaService despesaService = new DespesaService();
		Despesa despesa = new Despesa();

		// Coloca valores de descrição e data na despesa
		despesa.setDescricaoDespesa(Internationalization.getMessage("botao_compras"));
		despesa.setDtPagamento(compras.getPeriodoReferencia());

		// Verifica se há despesa com este mês
		despesa = despesaService.findOne(despesa);

		// Caso não haja, cria uma despesa com o mês vigente
		if (despesa == null) {
			despesa = new Despesa();
			despesa.setDescricaoDespesa(Internationalization.getMessage("botao_compras"));
			despesa.setDtPagamento(compras.getPeriodoReferencia());
			despesa.setCategoria(EnumCategoria.VARIAVEL);
			despesa.setContaAtiva(EnumContaAtiva.ATIVO);
		}

		// Atualiza o valor
		despesa.setValorDespesa(compras.getValorDespesa());

		//Seta o valor como compra
		despesa.setCompras(true);
		
		// Armazena na tabela de despesas como Despesa variável
		despesaService.insert(despesa);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		internacionalizar();
		jsMes.setValueFactory(mesSpinner);
		jsAno.setValueFactory(anoSpinner);
		jsMes.getValueFactory().setValue(LocalDate.now().getMonthValue());
		jsAno.getValueFactory().setValue(LocalDate.now().getYear());

		for (Ingrediente ingrediente : ingredienteService.findAll()) {
			mapIngrediente.put(ingrediente.getDescricaoIngrediente(), ingrediente);
		}

		Platform.runLater(() -> {
			carregaListaProdutos(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(), 1));
		});
	}

	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_compras"));
		jlSubtitulo.setText(Internationalization.getMessage("label_legenda_novo_produto"));
		jlPeriodo.setText(Internationalization.getMessage("titulo_periodo"));
		jbPesquisar.setText(Internationalization.getMessage("botao_pesquisar"));
		jlSalvar.setText(Internationalization.getMessage("botao_salvar"));
	}

	private void carregaListaProdutos(LocalDate periodo) {
		listProdutos.clear();
		indice = -1;
		vbProdutos.getChildren().clear();
		List<Produto> produtos = produtoService.produtosPorMes(periodo);
		mapProdutoFixo.clear();
		mapProduto.clear();
		for (Produto produto : produtos) {
			indice++;
			mapProdutoFixo.put(indice, produto);
			criarProduto(indice, produto);
		}
	}

	private ObservableList<String> carregaListaIngredientes() {
		IngredienteService ingredienteService = new IngredienteService();
		List<Ingrediente> listIngrediente = ingredienteService.findAll();
		ingredientes = FXCollections.observableArrayList();
		for (Ingrediente ingrediente : listIngrediente) {
			ingredientes.add(ingrediente.getDescricaoIngrediente());
		}
		return ingredientes;
	}

	private void criarProduto(int indice, Produto produto) {
		mapProduto.put(indice, produto);

		HBox hbProduto = new HBox();
		vbProdutos.getChildren().add(hbProduto);
		hbProduto.setSpacing(10);

		JFXComboBox<String> jcbProduto = new JFXComboBox<>();
		jcbProduto.setItems(carregaListaIngredientes());
		jcbProduto.setPrefWidth(400);
		
//		jcbProduto.setConverter(new StringConverter<Ingrediente>() {
//			
//			@Override
//			public String toString(Ingrediente object) {
//				return object.getDescricaoIngrediente();
//			}
//			
//			@Override
//			public Ingrediente fromString(Ingrediente string) {
//				return jcbProduto.getItems().stream().filter(prod -> prod.getDescricaoIngrediente().equals(string)).findFirst().orElse(null);
//			}
//		});
//		
		hbProduto.getChildren().add(jcbProduto);
		
		//----------------------------Campo de Quantidade
		//Inicialmente criamos nossos nodes e deixamo-os corretamente confirgurados
		/*Produtos Inteiros*/
		Spinner<Integer> jsQtde = new Spinner<>();
		SpinnerValueFactory<Integer> valorSpinnerInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0);
		/*Produtos Fracionados*/
		JFXTextField jtfQtde = new JFXTextField();
		jsQtde.setValueFactory(valorSpinnerInt);
		valorSpinnerInt = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 255, 0);
		
		//Ambos são adicionados ao HBox
		hbProduto.getChildren().addAll(jtfQtde, jsQtde);
		
		//É analisado qual o tipo de Unidade de medida deve ser utilizado, caso o produto seja novo será utilizado o inteiro
		if (produto.getIngrediente() != null && produto.getIngrediente().getUnidadeMedida().getFracionado()) {
			jtfQtde.setVisible(true);
			jtfQtde.setPrefSize(100, 10);
			jsQtde.setVisible(false);
			jsQtde.setPrefSize(0, 0);
		}else if(produto.getIngrediente() != null && !produto.getIngrediente().getUnidadeMedida().getFracionado()) {
			jtfQtde.setVisible(false);
			jtfQtde.setPrefSize(0, 0);
			jsQtde.setVisible(true);
			jsQtde.setPrefSize(100, 10);
		}else {
			jtfQtde.setVisible(false);
			jtfQtde.setPrefSize(0, 0);
			jsQtde.setVisible(true);
			jsQtde.setPrefSize(100, 10);
		}
		
		NumberTextField jntfValor = new NumberTextField();
		jntfValor.setPromptText(Internationalization.getMessage("campo_valor"));

		JFXButton jbApagar = new JFXButton(Internationalization.getMessage("botao_excluir"));
		hbProduto.getChildren().add(jntfValor);
		hbProduto.getChildren().add(jbApagar);

		if (produto.getIngrediente() != null) {
			jcbProduto.getSelectionModel().select(produto.getIngrediente().getDescricaoIngrediente());
		}

		if (produto.getQuantidade() != null) {
			jtfQtde.setText(produto.getQuantidade().toString());
			jsQtde.getValueFactory().setValue(produto.getQuantidade().intValue());
		}

		if (produto.getValor() != null) {
			jntfValor.setText(produto.getValor().toString());
		}
		
		jcbProduto.getSelectionModel().selectedItemProperty().addListener((obs,oldV,newV)->{
			produto.setIngrediente(ingredienteService.findOne(new Ingrediente(newV)));
//			produto.setIngrediente(mapIngrediente.get(jcbProduto.getSelectionModel().getSelectedItem().getDescricaoIngrediente()));
			if(produto.getIngrediente().getUnidadeMedida().getFracionado()) {
				jsQtde.setVisible(false);
				jsQtde.setPrefSize(0, 0);
				jtfQtde.setVisible(true);
				jtfQtde.setPrefSize(100, 10);
			}else {
				jsQtde.setVisible(true);
				jsQtde.setPrefSize(100, 10);
				jtfQtde.setVisible(false);
				jtfQtde.setPrefSize(0, 0);
			}
		});

//		jcbProduto.setOnAction((event) -> {
//			produto.setIngrediente(mapIngrediente.get(jcbProduto.getSelectionModel().getSelectedItem()));
//		});

		jsQtde.valueProperty().addListener((obs, oldV, newV) -> {
			BigDecimal valor = new BigDecimal(newV).setScale(2,RoundingMode.HALF_EVEN);
			produto.setQuantidade(valor);
			jtfQtde.setText(valor.toString());
		});
		
		jtfQtde.textProperty().addListener((obs,oldV,newV)->{
			produto.setQuantidade(new BigDecimal(newV).setScale(2,RoundingMode.HALF_EVEN));
			jsQtde.getValueFactory().setValue(new BigDecimal(newV).setScale(2, RoundingMode.HALF_EVEN).intValue());
		});

		jntfValor.setOnKeyReleased((event) -> {
			produto.setValor(
					BigDecimal.valueOf(Float.valueOf(jntfValor.getText())).setScale(2, RoundingMode.HALF_EVEN));
		});

		jbApagar.setOnMouseClicked((event) -> {
			vbProdutos.getChildren().remove(hbProduto);
			mapProduto.remove(indice);
		});
	}

	@FXML
	public void handleAdicionar() {
		criarProduto(++indice, new Produto());
	}

	@FXML
	public void handlePesquisar() {
		carregaListaProdutos(LocalDate.of(jsAno.getValue(), jsMes.getValue(), 1));
	}

}
