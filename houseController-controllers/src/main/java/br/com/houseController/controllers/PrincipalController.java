package br.com.houseController.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class PrincipalController implements Initializable{
	
	@FXML
	StackPane spPrincipal;
	
	@FXML
	BorderPane bpPrincipal;
	
	@FXML
	ImageView ivVoltar;
	
	@FXML
	ImageView ivAvancar;
	
	@FXML
	ImageView ivMinimizar;
	
	@FXML
	ImageView ivFechar;
	
	@FXML
	AnchorPane jbHome;
	
	@FXML
	AnchorPane jbUsuarios;
	
	@FXML
	AnchorPane apTela;
	
	ScrollPane sp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		botoesJanela();
		
		ScreenUtils su = new ScreenUtils(sp, apTela);
		ScreenUtils.setChaveFxmlAtual(0);
		ScreenUtils.getSequenciaJanelas().put(0,"fxml/home.fxml");
		ScreenUtils.abrirScrollAnchor("fxml/home.fxml");
//		new FadeOut(ivMinimizar).PlayOnFinished(new FadeIn(ivMinimizar)).play();
		
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
		
		jbUsuarios.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {				
				ScreenUtils.abrirNovaJanela("fxml/subMenuUsuario.fxml");
			}
		});
		
	}
	
}
