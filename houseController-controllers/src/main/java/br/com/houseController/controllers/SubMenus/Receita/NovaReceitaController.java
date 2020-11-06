package br.com.houseController.controllers.SubMenus.Receita;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.annotations.Parent;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import br.com.houseController.Exceptions.CamposNaoPreenchidosException;
import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.dialogs.Aguarde2;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Receita.ReceitaService;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class NovaReceitaController extends ParametrosObjetos implements Initializable{
	
	@FXML
	StackPane spDialog;
	
	@FXML
	private JFXDatePicker jdpData;
	
	@FXML
	private JFXTextArea jtaDescricao;
	
	@FXML
	private JFXComboBox<String> jcbUsuario;
	
	@FXML
	private VBox vbCampos;
	
	@FXML
	private Label jlTitulo;
	
	@FXML
	private Label jlSubtitulo;
	
	@FXML
	private Label jlDataReceita;
	
	@FXML
	private Label jlDescricao;
	
	@FXML
	private Label jlUsuario;
	
	@FXML
	private Label jlValor;
	
	@FXML
	private Label jlSalvar;
	
	@FXML
	private Label jlLimpar;
	
	private NumberTextField jntfValor = new NumberTextField();
	
	private Receita receita;
	
	HashMap<String,Usuario> mapUsuario = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		internacionalizar();
		jntfValor.setPromptText("$");
		jntfValor.setLabelFloat(true);
		vbCampos.getChildren().add(jntfValor);
		
		Platform.runLater(()->{
			if(getObjetos() != null){
				jlTitulo.setText(Internationalization.getMessage("botao_alterar_receita"));
				receita = (Receita) getObjetos().get(0);
				jdpData.setValue(receita.getDtPagamento());
				jntfValor.setText(receita.getValor().setScale(2, RoundingMode.HALF_EVEN).toString());
				jtaDescricao.setText(receita.getDescricaoPagamento());
				jcbUsuario.getSelectionModel().select(receita.getUsuario().getNome());
			}else{
				receita = new Receita();
			}
			
		});
		
		UsuarioService usuarioService = new UsuarioService();
		List<Usuario> listUsuario = usuarioService.findAll();
		for(Usuario usuario : listUsuario){
			mapUsuario.put(usuario.getNome(), usuario);
			jcbUsuario.getItems().add(usuario.getNome());
		}
		
		jdpData.valueProperty().addListener((obs,oldV,newV)->{receita.setDtPagamento(newV);});
		jntfValor.textProperty().addListener((obs,oldV,newV)->{receita.setValor(new BigDecimal(newV).setScale(2, RoundingMode.HALF_EVEN));});
		jtaDescricao.textProperty().addListener((obs,oldV,newV)->{receita.setDescricaoPagamento(newV);});
		jcbUsuario.valueProperty().addListener((obs,oldV,newV)->{receita.setUsuario(mapUsuario.get(newV));});
	}
	
	private void internacionalizar() {
		jlTitulo.setText(Internationalization.getMessage("botao_nova_receita"));
		jlSubtitulo.setText(Internationalization.getMessage("msg_preencha_campos"));
		jlDataReceita.setText(Internationalization.getMessage("campo_data_receita"));
		jlDescricao.setText(Internationalization.getMessage("campo_descricao"));
		jlUsuario.setText(Internationalization.getMessage("campo_atribuido_a"));
		jlValor.setText(Internationalization.getMessage("campo_valor"));
		jlSalvar.setText(Internationalization.getMessage("botao_salvar"));
		jlLimpar.setText(Internationalization.getMessage("botao_limpar"));
	}

	@FXML
	private void handleSalvar() throws CamposNaoPreenchidosException {
		Task<Void> taskSalvar = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				ScreenUtils.checarCamposVazios(jtaDescricao,jntfValor);
				ReceitaService receitaService = new ReceitaService();
				receitaService.insert(receita);
				return null;
			}
			
		};
		
		taskSalvar.setOnRunning(e->{Aguarde2.mostrarJanelaAguarde();});
		
		taskSalvar.setOnFailed(e->{
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro9"), e.getSource().getException().getMessage(), Internationalization.getMessage("erro_button7"));
		});
		
		taskSalvar.setOnSucceeded(e->{
			Aguarde2.finalizarJanelaAguarde();
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_sucesso6"), Internationalization.getMessage("receita_cadastrada"), Internationalization.getMessage("certo_button8"));
		});
		new Thread(taskSalvar).start();
	}
	
	@FXML
	private void handleLimpar() {
		ScreenUtils.limparCampos(jntfValor,jtaDescricao);
	}
	


}
