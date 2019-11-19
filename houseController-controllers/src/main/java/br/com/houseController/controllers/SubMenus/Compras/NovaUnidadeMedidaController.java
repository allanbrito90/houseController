package br.com.houseController.controllers.SubMenus.Compras;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.Exceptions.CamposNaoPreenchidosException;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
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
			ScreenUtils.janelaInformação(spDialog, "Olha aí!", "Cadastro efetuado com sucesso", "Ufa!");
		});
		
		taskSalvar.setOnFailed(e -> {
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, "Ooops", e.getSource().getException().getMessage() , "Beleza");
		});
		new Thread(taskSalvar).start();
	}

	@FXML
	public void handleLimpar(){
		ScreenUtils.limparCampos(jtfNome);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
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
					jlTitulo.setText("Editar Usuário");
					jtfNome.setText(unidadeMedida.getDescricao());
					jcbFracionado.selectedProperty().setValue(unidadeMedida.getFracionado());
				}
			}
		});
		
	}

}
