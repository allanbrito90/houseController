package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.Exceptions.CamposNaoPreenchidosException;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.produto.Ingrediente;
import br.com.houseController.model.produto.UnidadeMedida;
import br.com.houseController.service.Compras.UnidadeMedidaService;
import br.com.houseController.service.Produto.IngredienteService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class NovoIngredienteController extends ParametrosObjetos implements Initializable {
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private JFXTextField jtfNome;
	
	@FXML
	private JFXComboBox<String> jcbUnidadeMedida;
	
	@FXML
	private Label jlTitulo;
	
	@FXML
	private Label jlSubtitulo;
	
	@FXML
	private Label jlNome;
	
	@FXML
	private Label jlUnidadeMedida;
	
	@FXML
	private Label jlSalvar;
	
	@FXML
	private Label jlLimpar;
	
	UnidadeMedidaService unidadeMedidaService = new UnidadeMedidaService();
	Ingrediente ingrediente;
	ObservableList<String> unidadesMedida;
	
	@FXML
	public void handleSalvar(){
		
		
		Task<Void> taskSalvar = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				ScreenUtils.checarCamposVazios(jtfNome);
				IngredienteService ingredienteService = new IngredienteService();
				ingredienteService.insert(ingrediente);
				return null;
			}
			
		};
		
		taskSalvar.setOnRunning(e->{
			Aguarde2.mostrarJanelaAguarde();
		});
		
		taskSalvar.setOnSucceeded(e->{
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, "Opa", "Ingrediente cadastrado com sucesso", "Fechado");
		});
		
		taskSalvar.setOnFailed(e->{
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, "Oooops", e.getSource().getException().getMessage(), "Fechado");
		});
		new Thread(taskSalvar).start();
	}

	@FXML
	public void handleLimpar(){
		ScreenUtils.limparCampos(jtfNome, jcbUnidadeMedida);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		internacionalizar();

		if (getObjetos() != null) {
			ingrediente = (Ingrediente) getObjetos().get(0);
			jlTitulo.setText("Editar Ingrediente");
			jtfNome.setText(ingrediente.getDescricaoIngrediente());
			jcbUnidadeMedida.getSelectionModel().select(ingrediente.getUnidadeMedida().getDescricao());
		}else{
			ingrediente = new Ingrediente();
		}
	
		
//		Platform.runLater(new Runnable() {
//
//			@Override
//			public void run() {
//				if (getObjetos() != null) {
//					ingrediente = (Ingrediente) getObjetos().get(0);
//					jlTitulo.setText("Editar Ingrediente");
//					jtfNome.setText(ingrediente.getDescricaoIngrediente());
//					jcbUnidadeMedida.getSelectionModel().select(ingrediente.getUnidadeMedida().getDescricao());
//				}else{
//					ingrediente = new Ingrediente();
//				}
//			}
//		});
		
		jtfNome.textProperty().addListener((obs, oldT, newT) -> {
			ingrediente.setDescricaoIngrediente(newT);
		});
		
		jcbUnidadeMedida.valueProperty().addListener((obs, oldT, newT) -> {
			ingrediente.setUnidadeMedida(unidadeMedidaService.findByNome(new UnidadeMedida(jcbUnidadeMedida.getSelectionModel().getSelectedItem())));
		});
	
		List<UnidadeMedida> listUnidadeMedida =  unidadeMedidaService.findAll();
		
		if (!listUnidadeMedida.isEmpty()) {
			unidadesMedida = FXCollections.observableArrayList();
			for (UnidadeMedida unidadeMedida : listUnidadeMedida) {
				unidadesMedida.add(unidadeMedida.getDescricao());
			}
			jcbUnidadeMedida.setItems(unidadesMedida);
			jcbUnidadeMedida.getSelectionModel().select(0);
		}
		
	}

	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_novo_ingrediente"));
		jlSubtitulo.setText(Internationalization.getMessage("msg_preencha_campos"));
		jlNome.setText(Internationalization.getMessage("campo_nome"));
		jlUnidadeMedida.setText(Internationalization.getMessage("campo_unidade_medida"));
		jlSalvar.setText(Internationalization.getMessage("botao_salvar"));
		jlLimpar.setText(Internationalization.getMessage("botao_limpar"));
	}

}
