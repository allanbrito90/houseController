package br.com.houseController.model.produto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="unidadeMedida")
@Table(name="unidade_medida")
public class UnidadeMedida {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String descricao;
	
	@Column
	private Boolean fracionado;


	public UnidadeMedida(int id, String descricao, Boolean fracionado) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.fracionado = false;
	}

	public UnidadeMedida(String descricao) {
		super();
		this.descricao = descricao;
	}

	public UnidadeMedida() {
		this.fracionado = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
		
	public Boolean getFracionado() {
		return fracionado;
	}
	
	public void setFracionado(Boolean fracionado) {
		this.fracionado = fracionado;
	}
}
