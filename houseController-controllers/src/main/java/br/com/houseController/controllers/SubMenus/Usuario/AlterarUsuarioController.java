package br.com.houseController.controllers.SubMenus.Usuario;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class AlterarUsuarioController extends ParametrosObjetos implements Initializable{
	
	@FXML
	public void Alterar(){
		UsuarioService us = new UsuarioService();
		ScreenUtils.abrirNovaJanela("fxml/Usuario/NovoUsuario.fxml", NovoUsuarioController.class, us.findOne(new Usuario(14,"","","","",true)));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}
	

}
