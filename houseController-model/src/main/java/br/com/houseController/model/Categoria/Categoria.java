package br.com.houseController.model.Categoria;

public class Categoria {
	
	Integer id;
	String descricaoCategoria;	
	
	public Categoria() {	}
	
	public Categoria(Integer id, String descricaoCategoria) {
		super();
		this.id = id;
		this.descricaoCategoria = descricaoCategoria;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescricaoCategoria() {
		return descricaoCategoria;
	}
	public void setDescricaoCategoria(String descricaoCategoria) {
		this.descricaoCategoria = descricaoCategoria;
	}
	
	
}
