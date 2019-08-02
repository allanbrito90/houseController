package br.com.houseController.controllers.SubMenus.Despesa;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.Controller;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.Enums.EnumCategoria;
import br.com.houseController.model.despesas.DespesaFixa;
import br.com.houseController.model.despesas.DespesaVariavel;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

public class NovaDespesaController extends ParametrosObjetos implements Initializable{
	
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

	NumberTextField jntf = new NumberTextField();
	
	HashMap<String,Usuario> mapUsuario = new HashMap<>();
	
	DespesaFixa despesaFixa;
	DespesaVariavel despesaVariavel;
	
	@FXML
	private void handleSalvar(){
		
	}
	
	@FXML
	private void handleLimpar(){
		ScreenUtils.limparCampos(jtaDescricao,jntf);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		vbValor.getChildren().add(jntf);
		
		inicializaCampos();
		
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
		
		jcbCategoria.valueProperty().addListener((obs,oldV,newV)->{
			if(newV.toUpperCase().equals(EnumCategoria.FIXA.name())){
				vbVariavel.setVisible(true);
			}else{
				vbVariavel.setVisible(false);				
			}
		});
		
		Platform.runLater(()->{
			if(getObjetos() != null){
				
			}else{
				
			}
		});
		
	}
	
	
}
