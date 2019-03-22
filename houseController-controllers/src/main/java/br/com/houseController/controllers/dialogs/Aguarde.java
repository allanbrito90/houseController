package br.com.houseController.controllers.dialogs;

import java.io.IOException;

import com.jfoenix.controls.JFXSpinner;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class Aguarde 
{
	private Stage stage;
	
	private AnchorPane apTeste;
	
    public void mostrarJanelaAguarde(){
		try {
			stage = new Stage();
			Parent root1 = (Parent) FXMLLoader.load(getClass().getClassLoader().getResource("fxml/aguarde.fxml"));
			stage.setScene(new Scene(root1));
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void finalizarJanelaAguarde(){
    	stage.close();
    }
    
    public void teste(AnchorPane n){
		JFXSpinner jSpinner = new JFXSpinner();
		jSpinner.setPrefSize(30, 30);
		
		apTeste = new AnchorPane(jSpinner);
		apTeste.setManaged(false);
		apTeste.setLayoutX(n.getWidth()/2);
		apTeste.setLayoutY(n.getHeight()/2);
		n.getChildren().add(apTeste);
		apTeste.toFront();
    }
    
    public void teste2(){
    	apTeste.setManaged(false);
    	apTeste.setVisible(false);
    }
    
}
