package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.Exceptions.CamposNaoPreenchidosException;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.produto.UnidadeMedida;
import br.com.houseController.service.Compras.UnidadeMedidaService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class NovaUnidadeMedidaController extends ParametrosObjetos implements Initializable {
	
	@FXML
	private StackPane spDialog;
	
	@FXML
	private JFXTextField jtfNome;
	
	@FXML
	private JFXCheckBox jcbFracionado;
	
	@FXML
	private Label jlTitulo;
	
	@FXML
	private Label jlNome;
	
	@FXML
	private Label jlSalvar;
	
	@FXML
	private Label jlLimpar;
	
	UnidadeMedida unidadeMedida = new UnidadeMedida();
	
	
	@FXML
	public void handleSalvar(){
		Task<Void> taskSalvar = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				ScreenUtils.checarCamposVazios(jtfNome);
				UnidadeMedidaService unidadeMedidaService = new UnidadeMedidaService();
				unidadeMedidaService.insert(unidadeMedida);
				return null;
			}
	
		};
		
		taskSalvar.setOnRunning(e -> {
			Aguarde2.mostrarJanelaAguarde();
		});
		
		taskSalvar.setOnSucceeded(e -> {
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_sucesso3"), Internationalization.getMessage("unidade_medida_cadastrada"), Internationalization.getMessage("certo_button4"));
		});
		
		taskSalvar.setOnFailed(e -> {
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro6"), e.getSource().getException().getMessage() , Internationalization.getMessage("erro_button1"));
		});
		new Thread(taskSalvar).start();
	}

	@FXML
	public void handleLimpar(){
		ScreenUtils.limparCampos(jtfNome);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		internacionalizar();
		
		jtfNome.textProperty().addListener((obs, oldT, newT) -> {
			unidadeMedida.setDescricao(newT);
		});
		
		jcbFracionado.selectedProperty().addListener((obs,oldValue,newValue) -> {
			unidadeMedida.setFracionado(newValue);
		});
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (getObjetos() != null) {
					unidadeMedida = (UnidadeMedida) getObjetos().get(0);
					jlTitulo.setText(Internationalization.getMessage("botao_alterar_unidade_medida"));
					jtfNome.setText(unidadeMedida.getDescricao());
					jcbFracionado.selectedProperty().setValue(unidadeMedida.getFracionado());
				}
			}
		});
		
	}

	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_nova_unidade_medida"));		
		jlNome.setText(Internationalization.getMessage("campo_nome"));
		jcbFracionado.setText(Internationalization.getMessage("campo_unidade_medida_fracionada"));
		jlSalvar.setText(Internationalization.getMessage("botao_salvar"));
		jlLimpar.setText(Internationalization.getMessage("botao_limpar"));
	}

}
