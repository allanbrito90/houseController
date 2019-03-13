package br.com.houseController.controllers.dialogs;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Aguarde 
{
	private Stage stage;
	
    public void mostrarJanelaAguarde(){
		try {
			stage = new Stage();
			Parent root1 = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("fxml/aguarde.fxml"));
//			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void finalizarJanelaAguarde(){
    	stage.close();
    }
    
}
