package br.com.houseController.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.houseController.controllers.utils.ScreenUtils;
import br.com.houseController.model.SubMenu.BlocoSubMenu;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
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
				hBox.setSpacing(5);
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
		anchorPane.getStyleClass().add("fundoBotaoSubMenu");
		anchorPane.setPrefSize(260, 200);
		Label label = new Label();
		label.setText(blocoSubMenu.getNomeMenu());
		label.setAlignment(Pos.CENTER);
		anchorPane.getChildren().add(label);
//		label.layoutXProperty().bind(anchorPane.widthProperty().subtract(anchorPane.widthProperty()).divide(2));
//		label.layoutYProperty().bind(anchorPane.heightProperty().subtract(anchorPane.heightProperty()).divide(2));
		anchorPane.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				ScreenUtils.abrirNovaJanela(blocoSubMenu.getDestino());
			}
		});
		
		return anchorPane;
	}

}
