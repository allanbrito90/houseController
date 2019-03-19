package br.com.houseController.controllers.utils;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class ScreenUtils {
	
	public void abrirScrollAnchor(ScrollPane scroll, AnchorPane ap, String caminho){
		try {
			scroll = FXMLLoader.load(getClass().getClassLoader().getResource(caminho));
			ap.getChildren().add(scroll);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
