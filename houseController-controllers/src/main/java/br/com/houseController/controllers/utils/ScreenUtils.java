package br.com.houseController.controllers.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.base.IFXLabelFloatControl;

import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.PrincipalController;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ScreenUtils {
	
	static ScrollPane scroll;
	static AnchorPane ap;
	static Integer chaveFxmlAtual;
	static Map<Integer,String> sequenciaJanelas;
	static JFXDialog informationDialog;
	
//	-------------------------------------------------------------------------------
//	---------------------------GETTERS E SETTERS-----------------------------------
//	-------------------------------------------------------------------------------
	public static Map<Integer, String> getSequenciaJanelas() {
		return sequenciaJanelas;
	}

	public static void setSequenciaJanelas(Map<Integer, String> sequenciaJanelas) {
		ScreenUtils.sequenciaJanelas = sequenciaJanelas;
	}

	public static Integer getChaveFxmlAtual() {
		return chaveFxmlAtual;
	}

	public static void setChaveFxmlAtual(Integer chaveFxmlAtual) {
		ScreenUtils.chaveFxmlAtual = chaveFxmlAtual;
	}

//	-------------------------------------------------------------------------------
//	--------------------------------MÉTODOS----------------------------------------
//	-------------------------------------------------------------------------------
	public ScreenUtils(ScrollPane scroll, AnchorPane ap){
		ScreenUtils.sequenciaJanelas = new HashMap<Integer,String>();
		ScreenUtils.scroll = scroll;
		ScreenUtils.ap = ap;
	}
	
	public static void abrirNovaJanela(String caminho){
		sequenciaJanelas.put(++chaveFxmlAtual, caminho);
		abrirScrollAnchor(caminho);
	}
	
	public static void abrirNovaJanela(String caminho, Class<?> Controller, Object... objs){
		sequenciaJanelas.put(++chaveFxmlAtual, caminho);
		abrirScrollAnchorcomParametros(caminho,Controller,objs);

	}
	
	private static void abrirScrollAnchorcomParametros(String caminho, Class<?> controller, Object... objs) {
		try {
			FXMLLoader fxml = new FXMLLoader();
			fxml.setLocation(PrincipalController.class.getClassLoader().getResource(caminho));
			scroll = fxml.load();			
			ap.getChildren().add(scroll);
			ParametrosObjetos po = fxml.getController();
			List<Object> objetos = new ArrayList<Object>();
			for(Object obj : objs){
				objetos.add(obj);
			}			
			po.setObjetos(objetos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void voltarJanela(){
		if (chaveFxmlAtual - 1 >= 0) {
			String fxmlAnterior = sequenciaJanelas.get(--chaveFxmlAtual);
			abrirScrollAnchor(fxmlAnterior);
		}
	}
	
	public static void avancarJanela(){
		if(sequenciaJanelas.get(chaveFxmlAtual+1)!=null){
			String fxmlAnterior = sequenciaJanelas.get(++chaveFxmlAtual);
			abrirScrollAnchor(fxmlAnterior);
		}
	}
	
	public static void abrirScrollAnchor(String caminho){
		try {
			scroll = FXMLLoader.load(PrincipalController.class.getClassLoader().getResource(caminho));
			ap.getChildren().add(scroll);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void limparCampos(IFXLabelFloatControl... nodes){
		for(IFXLabelFloatControl node : nodes){
			((TextInputControl) node).clear();
		}
	}
	
	public static Boolean checarCamposVazios(IFXLabelFloatControl... nodes){
		for(IFXLabelFloatControl node : nodes){
			if(((TextInputControl) node).getText().equals("")){
				System.out.println("Campo vazio");
				return false;
			}
		}
		return true;
	}

	public static void janelaInformação(StackPane spDialog, String titulo, String conteudo, String nomeBotao) {
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		dialogContent.setHeading(new Text(titulo));
		dialogContent.setBody(new Text(conteudo));
		JFXButton fechar = new JFXButton(nomeBotao);
		fechar.setButtonType(JFXButton.ButtonType.RAISED);
		fechar.setStyle("-fx-background-color: #00bfff;");
		
		dialogContent.setActions(fechar);
		
		JFXDialog dialog = new JFXDialog(spDialog, dialogContent, JFXDialog.DialogTransition.BOTTOM);
		fechar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				dialog.close();				
			}
		});
		dialog.show();
	}
	
	public static void janelaAguarde(StackPane spDialog) {
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		dialogContent.setHeading(new Text("Aguarde"));
		dialogContent.setBody(new Text("Aguarde"));		
		informationDialog = new JFXDialog(spDialog, dialogContent, JFXDialog.DialogTransition.BOTTOM);
		informationDialog.show();
	}
	
	public static void janelaAguardeFinalizar(){
		informationDialog.close();
		informationDialog = null;
	}
	
	public static void janelaFechar(StackPane spDialog) {
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		dialogContent.setHeading(new Text("Fechar a Aplicação?"));
		dialogContent.setBody(new Text("Deseja realmente finalizar a aplicação?"));
		JFXButton fechar = new JFXButton("Sim");
		fechar.setButtonType(JFXButton.ButtonType.RAISED);
		fechar.setStyle("-fx-background-color: #00bfff;");
		
		JFXButton naoFechar = new JFXButton("Não");
		naoFechar.setButtonType(JFXButton.ButtonType.RAISED);
		naoFechar.setStyle("-fx-background-color: #ce0000;");
		
		
		dialogContent.setActions(fechar,naoFechar);
		
		JFXDialog dialog = new JFXDialog(spDialog, dialogContent, JFXDialog.DialogTransition.BOTTOM);
		fechar.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				ConnectionFactory.fecharConexao();
				dialog.close();		
				System.exit(0);
			}
		});
		
		naoFechar.setOnAction((e) -> {
			dialog.close();
		});
		
		dialog.show();
	}
	
}
