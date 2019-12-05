package br.com.houseController.model.SubMenu;

public class SequenciaJanelas {

	private String caminho;
	private Object controller;
	
	public SequenciaJanelas() {
		super();
	}
	
	public SequenciaJanelas(String caminho, Object controller) {
		super();
		this.caminho = caminho;
		this.controller = controller;
	}
	
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public Object getController() {
		return controller;
	}
	public void setController(Object controller) {
		this.controller = controller;
	}
	
	
}
