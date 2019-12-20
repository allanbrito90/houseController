package br.com.houseController.model.Enums;

public enum EnumIdioma {

	PORTUGUES("pt","BR"),
	INGLES("en","US");
	
	private String idioma;
	private String pais;

	private EnumIdioma (String idioma, String pais){
		this.idioma = idioma;
		this.pais = pais;
	}
	
	public String getLinguagem(){
		return idioma;
	}
	
	public String getPais(){
		return pais;
	}
	
	public static EnumIdioma getFromLinguagem(String linguagem){
		for (EnumIdioma enumIdioma : EnumIdioma.values()){
			if(linguagem.equals(enumIdioma.getLinguagem())){
				return enumIdioma;
			}
		}
		return null;
	}
	
	public static EnumIdioma getFromPais(String pais){
		for (EnumIdioma enumIdioma : EnumIdioma.values()){
			if(pais.equals(enumIdioma.getPais())){
				return enumIdioma;
			}
		}
		return null;
	}
	
}
