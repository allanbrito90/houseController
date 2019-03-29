package br.com.houseController.controllers.SubMenus.Usuario;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AlterarUsuarioController extends ParametrosObjetos implements Initializable{
	
	@FXML
	JFXListView<String> jlvUsuarios;
	
	UsuarioService usuarioService;
	
	@FXML
	public void Alterar(){
		UsuarioService us = new UsuarioService();
		ScreenUtils.abrirNovaJanela("fxml/Usuario/NovoUsuario.fxml", NovoUsuarioController.class, us.findOne(new Usuario(14,"","","","",true)));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		usuarioService = new UsuarioService();
		jlvUsuarios = new JFXListView<String>();
		
		jlvUsuarios.getItems().add("Hello World");
		
//		ArrayList<Usuario> usuarios = usuarioService.findAll();
//		ObservableList<String> listaUsuarios = FXCollections.observableArrayList(); 
//		for(Usuario usuario : usuarios){
//			jlvUsuarios.getItems().add(usuario.toString());
//			listaUsuarios.add(usuario.toString());
//		}
			
	}
	

}
