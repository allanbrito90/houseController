package br.com.houseController.controllers.utils;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.util.ArrayList;

public class Fonts {
	private static ArrayList<Fonts> fontList= new ArrayList<Fonts>();
	
	public static final String FONT_LOCATION = "fonts/";
	
	private static String fontPath;
	
	public Fonts(String filePath) {
		Fonts.fontPath = FONT_LOCATION + filePath;
		registerFont();
	}
	
	private void registerFont() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			File file = new File(Fonts.class.getClassLoader().getResource(fontPath).toExternalForm());
			System.out.println(file.exists());
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, file));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addFont(Fonts font) {
		fontList.add(font);
	}
	
}
