package br.com.houseController.controllers.dialogs;

import java.io.IOException;

import com.jfoenix.controls.JFXSpinner;

import br.com.houseController.controllers.PrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Hello world!
 *
 */
public class Aguarde2 
{
	static Stage stage;
	
    public static void mostrarJanelaAguarde(){
		try {
			/*if(stage == null){*/
				stage = new Stage();
				Parent root1 = (Parent) FXMLLoader.load(PrincipalController.class.getClassLoader().getResource("fxml/aguarde.fxml"));
				Scene scene = new Scene(root1);
				stage.setScene(scene);
				stage.initStyle(StageStyle.TRANSPARENT);
				stage.show();
			/*}else{
				stage.show();
			}*/
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public static void finalizarJanelaAguarde(){
    	stage.close();
    }
    
}
