package br.com.houseController.login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login {
	public void exibirLogin(Stage stage) {
		try {
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
		stage.setTitle("Login");
		stage.setScene(new Scene(root,250,200));
		stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
