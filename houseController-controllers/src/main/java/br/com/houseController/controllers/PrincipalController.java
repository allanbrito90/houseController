package br.com.houseController.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.com.houseController.persistence.ConnectionFactory;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PrincipalController implements Initializable{
	
	@FXML
	BorderPane bpPrincipal;
	
	@FXML
	ImageView ivMinimizar;
	
	@FXML
	ImageView ivFechar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		botoesJanela();
		
		
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
	}

	
}
