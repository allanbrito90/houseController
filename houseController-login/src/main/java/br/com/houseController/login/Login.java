package br.com.houseController.login;

import java.io.File;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login {
	public void exibirLogin(Stage stage) {
		try {
			File file = new File("C:/Users/allan.santos/Documents/Arquivos Pessoais/houseController/houseController/houseController-login/src/main/java/br/com/houseController/login/login.fxml");
			System.out.println(file.exists());
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("login.fxml"));
		stage.setTitle("Login");
		stage.setScene(new Scene(root,200,250));
		stage.show();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
