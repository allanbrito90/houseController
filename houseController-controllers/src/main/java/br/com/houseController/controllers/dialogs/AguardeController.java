package br.com.houseController.controllers.dialogs;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import br.com.houseController.internationalization.Internationalization;

public class AguardeController implements Initializable{

	@FXML
	private Label jlAguarde;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		jlAguarde.setText(Internationalization.getMessage("mensagem_aguarde"));
	}
	
	
}
