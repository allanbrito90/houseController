package br.com.houseController.controllers.SubMenus.Receita;

import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Receita.ReceitaService;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class NovaReceitaController extends ParametrosObjetos implements Initializable{
	
	@FXML
	private JFXDatePicker jdpData;
	
	@FXML
	private JFXTextArea jtaDescricao;
	
	@FXML
	private JFXComboBox<String> jcbUsuario;
	
	@FXML
	private VBox vbCampos;
	
	private NumberTextField jntfValor = new NumberTextField();
	
	private Receita receita;
	
	HashMap<String,Usuario> mapUsuario = new HashMap<>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		jntfValor.setPromptText("R$");
		jntfValor.setLabelFloat(true);
		vbCampos.getChildren().add(jntfValor);
		
		Platform.runLater(()->{
			if(getObjetos() != null){
				receita = (Receita) getObjetos().get(0);
				jdpData.setValue(receita.getDtPagamento());
				jntfValor.setText(receita.getValor().toString());
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
		jntfValor.textProperty().addListener((obs,oldV,newV)->{receita.setValor(new BigDecimal(newV));});
		jtaDescricao.textProperty().addListener((obs,oldV,newV)->{receita.setDescricaoPagamento(newV);});
		jcbUsuario.valueProperty().addListener((obs,oldV,newV)->{receita.setUsuario(mapUsuario.get(newV));});
	}
	
	@FXML
	private void handleSalvar() {
		if (ScreenUtils.checarCamposVazios(jtaDescricao,jntfValor)) {
			ReceitaService receitaService = new ReceitaService();
			receitaService.insert(receita);
		}
	}
	
	@FXML
	private void handleLimpar() {
		ScreenUtils.limparCampos(jntfValor,jtaDescricao);
	}
	


}
