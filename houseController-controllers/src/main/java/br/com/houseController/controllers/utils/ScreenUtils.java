package br.com.houseController.controllers.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.base.IFXLabelFloatControl;

import br.com.houseController.components.NumberTextField;
import br.com.houseController.controllers.Controller;
import br.com.houseController.controllers.ParametrosObjetos;
import br.com.houseController.controllers.PrincipalController;
import br.com.houseController.controllers.SubMenus.SubMenuUsuarios;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
	
	public static void abrirNovaJanela(String caminho, Object... objs){
		sequenciaJanelas.put(++chaveFxmlAtual, caminho);
		abrirScrollAnchorcomParametros(caminho,objs);

	}
	
	public static void abrirNovaJanela(String caminho, Controller controller, Object... objs){
		sequenciaJanelas.put(++chaveFxmlAtual, caminho);
		abrirScrollAnchorcomParametros(caminho,controller,objs);

	}
	
	private static void abrirScrollAnchorcomParametros(String caminho, Object... objs) {
		try {
			FXMLLoader fxml = new FXMLLoader();
			fxml.setLocation(PrincipalController.class.getClassLoader().getResource(caminho));
			scroll = fxml.load();			
			ap.getChildren().add(scroll);
			if (objs != null) {
				ParametrosObjetos po = fxml.getController();
				List<Object> objetos = new ArrayList<Object>();
				for (Object obj : objs) {
					objetos.add(obj);
				}
				po.setObjetos(objetos);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void abrirScrollAnchorcomParametros(String caminho, Controller controller, Object... objs) {
		try {
			FXMLLoader fxml = new FXMLLoader();
			fxml.setController(controller);
			fxml.setLocation(PrincipalController.class.getClassLoader().getResource(caminho));
			scroll = fxml.load();			
			ap.getChildren().add(scroll);
			if (objs != null) {
				ParametrosObjetos po = fxml.getController();
				List<Object> objetos = new ArrayList<Object>();
				for (Object obj : objs) {
					objetos.add(obj);
				}
				po.setObjetos(objetos);
			}
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
			if(node instanceof NumberTextField){
				((NumberTextField) node).setText("0.00");
			}else if (node instanceof TextInputControl) {
				((TextInputControl) node).clear();
			}else if (node instanceof ComboBox){
				((ComboBox<?>) node).getSelectionModel().select(0);
			} 
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
	
	public static BigDecimal janelaEntradaValor(StackPane spDialog, BigDecimal receitaDisponivel, BigDecimal despesaDisponivel) {
//		JFXDialogLayout dialogContent = new JFXDialogLayout();
//		NumberTextField jntf = new NumberTextField();
//		dialogContent.setHeading(new Text("Digite o valor"));
//		dialogContent.setBody(jntf);
//		JFXButton fechar = new JFXButton("Ok");
//		fechar.setButtonType(JFXButton.ButtonType.RAISED);
//		fechar.setStyle("-fx-background-color: #00bfff;");
//		
//		dialogContent.setActions(fechar);
//		
//		JFXDialog dialog = new JFXDialog(spDialog, dialogContent, JFXDialog.DialogTransition.BOTTOM);
//		fechar.setOnAction(new EventHandler<ActionEvent>() {
//			
//			@Override
//			public void handle(ActionEvent event) {
//				dialog.close();
//			}
//		});
//		dialog.setOverlayClose(false);
//		dialog.show();
		
		//------------------------------ESTA JANELA FUNCIONOU-----------------------
		Stage stage = new Stage();
		
		Pane pane = new Pane();
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		
		
		pane.setPrefHeight(100);
		pane.setPrefWidth(300);
		
		VBox vBox = new VBox();
		vBox.setSpacing(5);
		
		NumberTextField jntf = new NumberTextField();
		
		JFXButton jbOk = new JFXButton("OK");
		
		jbOk.setOnAction((e)->{
			if(jntf.getValor().compareTo(receitaDisponivel)==1 || jntf.getValor().compareTo(despesaDisponivel)==1){
				janelaInformação(spDialog,"Erro","O valor é maior do que o permitido","OK");
			}else{
				stage.close();
			}
		});
		
		vBox.getChildren().addAll(jntf,jbOk);
		
		pane.getChildren().add(vBox);
		
		stage.showAndWait();
		//---------------------------------FIM DA JANELA QUE FUNCIONOU
		
		return new BigDecimal(jntf.getText());
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
	
	public static boolean verificaAcessoAdmin(StackPane sp, Controller controller, Usuario usuario){
		if(!(controller instanceof SubMenuUsuarios) && usuario.getLogin().equals("admin")){
			ScreenUtils.janelaInformação(sp, "Acesso Negado", "Você não tem acesso com este usuário", "Tudo bem então!");
			return false;
		}else{
			return true;
		}
	}
	
}
