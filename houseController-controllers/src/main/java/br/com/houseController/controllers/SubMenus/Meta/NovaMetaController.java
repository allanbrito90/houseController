package br.com.houseController.controllers.SubMenus.Meta;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.meta.Meta.MetaService;
import br.com.houseController.model.meta.Meta;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class NovaMetaController extends ParametrosObjetos implements Initializable{
	
	@FXML
	Label jlTitulo;
	
	@FXML
	JFXTextField jtfTitulo;
	
	@FXML
	JFXTextArea jtaDescricao;
	
	@FXML
	JFXDatePicker jdpData;
	
	@FXML
	JFXCheckBox jcbConcluido;
	
	@FXML
	JFXComboBox<String> jcbUsuario;
	
	private Meta meta;
	
	HashMap<String,Usuario> mapUsuario = new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(()->{
			if(getObjetos() !=null){
				meta = (Meta) getObjetos().get(0);
				jlTitulo.setText("Editar Meta");
				jtfTitulo.setText(meta.getTitulo());
				jtaDescricao.setText(meta.getDescMeta());
				jdpData.setValue(meta.getDtConclusao());
				jcbConcluido.selectedProperty().set(meta.isCumprido());
				jcbUsuario.getSelectionModel().select(meta.getUsuario().getNome());
			}else{
				meta = new Meta();
				meta.setDtInicial(LocalDate.now());
				jdpData.setValue(LocalDate.now());
			}
		});

		UsuarioService usuarioService = new UsuarioService();
		List<Usuario> listUsuario = usuarioService.findAll();
		for(Usuario usuario : listUsuario){
			mapUsuario.put(usuario.getNome(), usuario);
			jcbUsuario.getItems().add(usuario.getNome());
		}
		
		
		
		jtfTitulo.textProperty().addListener((obs,oldV,newV) -> {meta.setTitulo(newV);});		
		jtaDescricao.textProperty().addListener((obs,oldV,newV) -> {meta.setDescMeta(newV);});
		jdpData.valueProperty().addListener((obs,oldV,newV) -> {meta.setDtConclusao(newV);});
		jcbConcluido.selectedProperty().addListener((obs,oldV,newV) -> {meta.setCumprido(newV);});		
		jcbUsuario.valueProperty().addListener((obs,oldV,newV) -> {meta.setUsuario(mapUsuario.get(newV));});
		
	}
	
	@FXML
	public void handleSalvar(){
		MetaService metaService = new MetaService();
		metaService.insert(meta);
	}
	
	@FXML
	public void handleLimpar(){
		ScreenUtils.limparCampos(jtfTitulo,jtaDescricao, jcbUsuario);
	}

}
