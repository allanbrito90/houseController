package br.com.houseController.internationalization;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Internationalization {
	
	private static Locale locale = new Locale("en","US");
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("internationalization/messages",locale);
	
	public static Locale getInstance(){
		return locale;
	}
	
	public static ResourceBundle getResourceBundleInstance(){
		return resourceBundle;
	}
	
	public static String getMessage(String message){
		return resourceBundle.getString(message);
	}
	
	public static String getMessage(String message, String... params){
		StringBuilder msg = new StringBuilder(resourceBundle.getString(message));
		for(int i = 0 ; i < params.length; i++){
			msg = new StringBuilder(msg.toString().replace(String.format("{%s}", i), params[i]));
		}
		return msg.toString();
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void setLocale(Locale locale) {
		Internationalization.locale = locale;
	}

	public static ResourceBundle getResourceBundle() {
		return resourceBundle;
	}

	public static void setResourceBundle(ResourceBundle resourceBundle) {
		Internationalization.resourceBundle = resourceBundle;
	}
	
	
	
}
