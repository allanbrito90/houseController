package br.com.houseController.model.Abstracts;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import br.com.houseController.model.Enums.EnumCategoria;
import br.com.houseController.model.Enums.EnumContaAtiva;
import br.com.houseController.model.receita.Receita;
import br.com.houseController.model.usuario.Usuario;

@MappedSuperclass
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractDespesa{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	protected
	Integer id;
	
	@Column
	Boolean pago;
	
//	@ManyToOne
	@Column
	EnumCategoria categoria;
	
	@Column
	EnumContaAtiva contaAtiva;
	
	@Column
	String descricaoDespesa;
	
	@Column
	BigDecimal valorDespesa;
	
	@OneToOne
	Usuario usuario;
	

	//@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@ManyToOne
	Receita receitaUtilizada;
	
	
	
	public AbstractDespesa() {
	}



	public AbstractDespesa(Integer id, Boolean pago, EnumCategoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada) {
		super();
		this.id = id;
		this.pago = pago;
		this.categoria = categoria;
		this.contaAtiva = contaAtiva;
		this.descricaoDespesa = descricaoDespesa;
		this.valorDespesa = valorDespesa;
		this.receitaUtilizada = receitaUtilizada;
	}
	
	public AbstractDespesa(Boolean pago, EnumCategoria categoria, EnumContaAtiva contaAtiva, String descricaoDespesa, BigDecimal valorDespesa, Receita receitaUtilizada) {
		super();
		this.pago = pago;
		this.categoria = categoria;
		this.contaAtiva = contaAtiva;
		this.descricaoDespesa = descricaoDespesa;
		this.valorDespesa = valorDespesa;
		this.receitaUtilizada = receitaUtilizada;
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Boolean getPago() {
		return pago;
	}



	public void setPago(Boolean pago) {
		this.pago = pago;
	}



	public EnumCategoria getCategoria() {
		return categoria;
	}



	public void setCategoria(EnumCategoria categoria) {
		this.categoria = categoria;
	}



	public EnumContaAtiva getContaAtiva() {
		return contaAtiva;
	}



	public void setContaAtiva(EnumContaAtiva contaAtiva) {
		this.contaAtiva = contaAtiva;
	}



	public String getDescricaoDespesa() {
		return descricaoDespesa;
	}



	public void setDescricaoDespesa(String descricaoDespesa) {
		this.descricaoDespesa = descricaoDespesa;
	}



	public BigDecimal getValorDespesa() {
		return valorDespesa;
	}



	public void setValorDespesa(BigDecimal valorDespesa) {
		this.valorDespesa = valorDespesa;
	}



	public Receita getReceitaUtilizada() {
		return receitaUtilizada;
	}



	public void setReceitaUtilizada(Receita receitaUtilizada) {
		this.receitaUtilizada = receitaUtilizada;
	}



	public Usuario getUsuario() {
		return usuario;
	}



	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	
	

}
