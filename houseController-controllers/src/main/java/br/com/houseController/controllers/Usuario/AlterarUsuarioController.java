package br.com.houseController.controllers.Usuario;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.service.Usuario.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

public class AlterarUsuarioController extends ParametrosObjetos implements Initializable{
	
	@FXML
	Label jlTitulo;
	
	@FXML
	Label jlEditar;
	
	@FXML
	Label jlExcluir;
	
	@FXML
	StackPane spDialog;
	
	@FXML
	TableView<Usuario> jtvUsuarioTable;
	
	@FXML
	TableColumn<Usuario, Integer> colID;
	
	@FXML
	TableColumn<Usuario, String> colNome;
	
	@FXML
	TableColumn<Usuario, String> colLogin;
	
	@FXML
	TableColumn<Usuario, String> colEmail;
	
	UsuarioService usuarioService;
	
	ObservableList<Usuario> usuarios;
	
//	@FXML
//	public void Alterar(){
//		UsuarioService us = new UsuarioService();
//		ScreenUtils.abrirNovaJanela("fxml/Usuario/NovoUsuario.fxml", us.findOne(new Usuario(14,"","","","",true)));
//	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		iniciaNomesCampos();
		
		colID.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
		jtvUsuarioTable.setItems(preencheTabela());
	}

	private void iniciaNomesCampos() {
		jlTitulo.setText(Internationalization.getMessage("botao_alterar_usuario"));
		colID.setText(Internationalization.getMessage("titulo_col_id_usuario"));
		colNome.setText(Internationalization.getMessage("titulo_col_nome_usuario"));
		colLogin.setText(Internationalization.getMessage("titulo_col_login_usuario"));
		colEmail.setText(Internationalization.getMessage("titulo_col_email_usuario"));
		jlEditar.setText(Internationalization.getMessage("botao_editar"));
		jlExcluir.setText(Internationalization.getMessage("botao_excluir"));
	}

	private ObservableList<Usuario> preencheTabela() {
		UsuarioService usuarioService = new UsuarioService();
		List<Usuario> listaUsuarios = usuarioService.findAll();
		usuarios = FXCollections.observableArrayList();
		for(Usuario usuario : listaUsuarios){
			if(!usuario.getNome().equals("Admin")) {
				usuarios.add(usuario);
			}
		}
		return usuarios;
	}
	
	@FXML
	public void handleEditar(){
		try{
			if(colID.getCellData(jtvUsuarioTable.getSelectionModel().getSelectedIndex()) != null){
				usuarioService = new UsuarioService();
				Usuario usuario = usuarioService.findOne(new Usuario(colID.getCellData(jtvUsuarioTable.getSelectionModel().getSelectedIndex())));
				ScreenUtils.abrirNovaJanela("fxml/Usuario/NovoUsuario.fxml", usuario);
			}else{
				ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@FXML
	public void handleExcluir(){
		if(colID.getCellData(jtvUsuarioTable.getSelectionModel().getSelectedIndex()) != null){
			usuarioService = new UsuarioService();
			usuarioService.delete(colID.getCellData(jtvUsuarioTable.getSelectionModel().getSelectedIndex()));
			atualizarTable();
		}else{
			ScreenUtils.janelaInformação(spDialog, Internationalization.getMessage("header_erro3"), Internationalization.getMessage("item_nao_selecionado"), Internationalization.getMessage("erro_button2"));
		}
	}

	private void atualizarTable() {
		usuarios.clear();
		jtvUsuarioTable.setItems(preencheTabela());
	}
	

}
