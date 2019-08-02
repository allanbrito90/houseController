package br.com.houseController.model.Enums;

public enum EnumCategoria {
FIXA("FIXA"),
VARIAVEL("VARIÁVEL");

private String nome;
	
private EnumCategoria(String nome){
	this.nome = nome;
}

private String getNome(){
	return nome;
}


}


