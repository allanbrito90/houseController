package br.com.houseController.model.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="ingrediente")
@Table(name="ingrediente")
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String descricaoIngrediente;
	
	
	public Ingrediente(int id, String descricaoIngrediente) {
		super();
		this.id = id;
		this.descricaoIngrediente = descricaoIngrediente;
	}
	
	
	
	public Ingrediente() {	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescricaoIngrediente() {
		return descricaoIngrediente;
	}
	public void setDescricaoIngrediente(String descricaoIngrediente) {
		this.descricaoIngrediente = descricaoIngrediente;
	}
	
	
	

}
