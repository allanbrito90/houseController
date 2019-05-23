package br.com.houseController.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Controller{
	
	@FXML
	protected Label jlTitulo;
	
	@FXML
	protected VBox vbMenu; 
	
	public void titulo(String titulo){
		jlTitulo.setText(titulo);
	}

}
