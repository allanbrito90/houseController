package br.com.houseController.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.sun.javafx.tk.ScreenConfigurationAccessor;

import br.com.houseController.controllers.SubMenus.SubMenuCompras;
import br.com.houseController.controllers.SubMenus.SubMenuDespesas;
import br.com.houseController.controllers.SubMenus.SubMenuMetaController;
import br.com.houseController.controllers.SubMenus.SubMenuReceitas;
import br.com.houseController.controllers.SubMenus.SubMenuUsuarios;
import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.internationalization.Internationalization;
import br.com.houseController.model.SubMenu.SequenciaJanelas;
import br.com.houseController.model.usuario.UsuarioLogado;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PrincipalController implements Initializable{
	
	@FXML
	private StackPane spPrincipal;
	
	@FXML
	private BorderPane bpPrincipal;
	
	@FXML
	private ImageView ivVoltar;
	
	@FXML
	private ImageView ivAvancar;
	
	@FXML
	private ImageView ivMinimizar;
	
	@FXML
	private ImageView ivFechar;
	
	@FXML
	private AnchorPane jbHome;
	
	@FXML
	private AnchorPane jbUsuarios;

	@FXML
	private AnchorPane jbDespesas;
	
	@FXML
	private AnchorPane jbReceitas;
	
	@FXML
	private AnchorPane jbMetas;
	
	@FXML
	private AnchorPane jbCompras;
	
	@FXML
	private AnchorPane apTela;
	
	@FXML
	private Label jlHome;
	
	@FXML
	private Label jlUsuarios;

	@FXML
	private Label jlDespesas;
	
	@FXML
	private Label jlReceitas;
	
	@FXML
	private Label jlMetas;
	
	@FXML
	private Label jlCompras;
	
	private ScrollPane sp;
	
	private UsuarioLogado usuarioLogado = UsuarioLogado.getInstance();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		botoesJanela();
		
		ScreenUtils su = new ScreenUtils(sp, apTela);
		ScreenUtils.setChaveFxmlAtual(0);
		SequenciaJanelas sj = new SequenciaJanelas("fxml/home.fxml",null);
		ScreenUtils.getSequenciaJanelas().put(0, sj);
		ScreenUtils.abrirScrollAnchor(sj);
//		new FadeOut(ivMinimizar).PlayOnFinished(new FadeIn(ivMinimizar)).play();
		
		internacionalizar();
		
	}
	

	private void internacionalizar() {
		jlCompras.setText(Internationalization.getMessage("botao_compras"));
		jlDespesas.setText(Internationalization.getMessage("botao_despesas"));
		jlHome.setText(Internationalization.getMessage("botao_home"));
		jlMetas.setText(Internationalization.getMessage("botao_metas"));
		jlReceitas.setText(Internationalization.getMessage("botao_receitas"));
		jlUsuarios.setText(Internationalization.getMessage("botao_usuarios"));
	}


	private void botoesJanela() {
		ivVoltar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils.voltarJanela();
			}
		});
		
		ivAvancar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils.avancarJanela();
			}
		});
		
		ivMinimizar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Stage stage = (Stage) ivMinimizar.getScene().getWindow();
				stage.setIconified(true);
			}
		});
		
		ivFechar.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils.janelaFechar(spPrincipal);
				
			}
		});
		
		jbHome.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {				
				ScreenUtils.abrirNovaJanela("fxml/home.fxml");
			}
		});
		
		jbMetas.setOnMouseClicked(new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {				
				if (ScreenUtils.verificaAcessoAdmin(spPrincipal, new SubMenuMetaController(), usuarioLogado.getUsuario())) {
					ScreenUtils.abrirNovaJanela("fxml/subMenuMeta.fxml");
				}
			}
		});
		
		jbDespesas.setOnMouseClicked(abrirSubMenu("fxml/subMenuGeral.fxml",new SubMenuDespesas(), null));
		jbReceitas.setOnMouseClicked(abrirSubMenu("fxml/subMenuGeral.fxml",new SubMenuReceitas(), null));
		jbCompras.setOnMouseClicked(abrirSubMenu("fxml/subMenuGeral.fxml",new SubMenuCompras(), null));
		jbUsuarios.setOnMouseClicked(abrirSubMenu("fxml/subMenuGeral.fxml",new SubMenuUsuarios(), null));
	}


	private EventHandler<Event> abrirSubMenu(String local, Controller controller, Object... obj) {
		return new EventHandler<Event>() {
			
			@Override
			public void handle(Event event) {				
				if(ScreenUtils.verificaAcessoAdmin(spPrincipal, controller, usuarioLogado.getUsuario())){
//					ScreenUtils.abrirNovaJanela(local,controller, obj);
					SequenciaJanelas sj = new SequenciaJanelas(local,controller);
					ScreenUtils.abrirNovaJanela(sj, obj);					
				}
			}
		};
	}
	
}
