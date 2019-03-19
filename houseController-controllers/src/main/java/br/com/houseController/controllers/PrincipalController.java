package br.com.houseController.controllers;

import java.awt.Desktop.Action;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalController implements Initializable{
	
	@FXML
	BorderPane bpPrincipal;
	
	@FXML
	ImageView ivMinimizar;
	
	@FXML
	ImageView ivFechar;
	
	@FXML
	JFXButton jbUsuarios;
	
	@FXML
	AnchorPane apTela;
	
	ScrollPane sp;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		botoesJanela();
//		new FadeOut(ivMinimizar).PlayOnFinished(new FadeIn(ivMinimizar)).play();
		
	}
	

	private void botoesJanela() {
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
				ConnectionFactory.fecharConexao();
				System.exit(0);
				
			}
		});
		
		jbUsuarios.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils su = new ScreenUtils();
				su.abrirScrollAnchor(sp, apTela, "fxml/subMenuUsuario.fxml");
			}
		});
	}
	
}
