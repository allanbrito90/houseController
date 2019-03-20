package br.com.houseController.controllers.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import br.com.houseController.controllers.PrincipalController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class ScreenUtils {
	
	static ScrollPane scroll;
	static AnchorPane ap;
	static Integer chaveFxmlAtual;
	static Map<Integer,String> sequenciaJanelas;
	
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

	public ScreenUtils(ScrollPane scroll, AnchorPane ap){
		ScreenUtils.sequenciaJanelas = new HashMap<Integer,String>();
		ScreenUtils.scroll = scroll;
		ScreenUtils.ap = ap;
	}
	
	public static void abrirNovaJanela(String caminho){
		sequenciaJanelas.put(++chaveFxmlAtual, caminho);
		abrirScrollAnchor(caminho);
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
	
}
