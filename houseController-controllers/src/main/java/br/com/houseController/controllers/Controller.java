package br.com.houseController.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller{
	
	@FXML
	protected Label jlTitulo;
	
	@FXML
	protected VBox vbMenu; 
	
	public void titulo(String titulo){
		jlTitulo.setText(titulo);
	}
	
	public List<HBox> criaBlocosSubMenus(BlocoSubMenu... blocosSubMenu){
		List<HBox> hBoxes = new ArrayList<>();
		for(int i=0; i < blocosSubMenu.length; i++){
			if(i % 3 == 0){
				HBox hBox = new HBox();	
				hBox.setPrefSize(867, 250);
				hBoxes.add(hBox);
				AnchorPane ap = criaBlocoIndividual(blocosSubMenu[i]);	
				hBox.getChildren().add(ap);
			}else{
				AnchorPane ap = criaBlocoIndividual(blocosSubMenu[i]);	
				hBoxes.get(hBoxes.size()-1).getChildren().add(ap);				
			}
		}		
		return hBoxes;
	}

	private AnchorPane criaBlocoIndividual(BlocoSubMenu blocoSubMenu) {		
		AnchorPane anchorPane = new AnchorPane();
		anchorPane.setPrefSize(260, 200);
		Label label = new Label();
		label.setText(blocoSubMenu.getNomeMenu());
		anchorPane.getChildren().add(label);
		
		return anchorPane;
	}

}
