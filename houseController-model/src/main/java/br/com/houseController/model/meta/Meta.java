package br.com.houseController.model.meta;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.houseController.model.usuario.Usuario;

@Entity(name="meta")
@Table(name="meta")
public class Meta {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	private Usuario usuario;
	
	@Column
	private String titulo;
	
	@Column
	private String descMeta;
	
	@Column
	private boolean cumprido;
	
	@Column
	private LocalDate dtInicial;
	
	@Column
	private LocalDate dtConclusao;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescMeta() {
		return descMeta;
	}

	public void setDescMeta(String descMeta) {
		this.descMeta = descMeta;
	}

	public boolean isCumprido() {
		return cumprido;
	}

	public void setCumprido(boolean cumprido) {
		this.cumprido = cumprido;
	}

	public LocalDate getDtInicial() {
		return dtInicial;
	}

	public void setDtInicial(LocalDate dtInicial) {
		this.dtInicial = dtInicial;
	}

	public LocalDate getDtConclusao() {
		return dtConclusao;
	}

	public void setDtConclusao(LocalDate dtConclusao) {
		this.dtConclusao = dtConclusao;
	}

	public Meta(int id, Usuario usuario, String titulo, String descMeta, boolean cumprido, LocalDate dtInicial, LocalDate dtConclusao) {
		this.id = id;
		this.usuario = usuario;
		this.titulo = titulo;
		this.descMeta = descMeta;
		this.cumprido = cumprido;
		this.dtInicial = dtInicial;
		this.dtConclusao = dtConclusao;
	}
	
	public Meta(Usuario usuario, String titulo, String descMeta, boolean cumprido, LocalDate dtInicial, LocalDate dtConclusao) {
		this.usuario = usuario;
		this.titulo = titulo;
		this.descMeta = descMeta;
		this.cumprido = cumprido;
		this.dtInicial = dtInicial;
		this.dtConclusao = dtConclusao;
	}

	public Meta() {}
	
	
}
