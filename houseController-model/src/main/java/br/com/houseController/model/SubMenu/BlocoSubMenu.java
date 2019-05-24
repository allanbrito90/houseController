package br.com.houseController.model.SubMenu;

public class BlocoSubMenu {
	
	private String nomeMenu;
	private String caminhoImagemBloco;
	private String destino;


	public String getNomeMenu() {
		return nomeMenu;
	}
	public void setNomeMenu(String nomeMenu) {
		this.nomeMenu = nomeMenu;
	}
	public String getCaminhoImagemBloco() {
		return caminhoImagemBloco;
	}
	public void setCaminhoImagemBloco(String caminhoImagemBloco) {
		this.caminhoImagemBloco = caminhoImagemBloco;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public BlocoSubMenu(String nomeMenu, String caminhoImagemBloco, String destino) {
		super();
		this.nomeMenu = nomeMenu;
		this.caminhoImagemBloco = caminhoImagemBloco;
		this.destino = destino;
	}
	
	
	
	
	
	

}
