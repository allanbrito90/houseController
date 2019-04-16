package br.com.houseController.model.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity(name="ingrediente")
@Table(name="ingrediente")
public class Ingrediente {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String descricaoIngrediente;	
	
	@OneToOne
	private UnidadeMedida unidadeMedida;
	
		
	public Ingrediente(int id, String descricaoIngrediente, UnidadeMedida unidadeMedida) {
		super();
		this.id = id;
		this.descricaoIngrediente = descricaoIngrediente;
		this.unidadeMedida = unidadeMedida;
	}

	public Ingrediente(String descricaoIngrediente, UnidadeMedida unidadeMedida) {
		super();
		this.descricaoIngrediente = descricaoIngrediente;
		this.unidadeMedida = unidadeMedida;
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
	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	
	
	

}
