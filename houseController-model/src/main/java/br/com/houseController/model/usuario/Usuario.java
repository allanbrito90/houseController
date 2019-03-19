package br.com.houseController.model.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Modelo de usuário para login
 * @author allan
 * @version 1.0
 *
 */
@Entity
@Table(name="usuario")
public class Usuario{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	/**
	 * Id do usuário
	 */
	private int id;
	
	@Column
	/**
	 * login para acesso e permissões
	 */
	private String login;
	
	@Column
	private String senha;
	
	@Column
	private String nome;
	
	@Column
	private String email;
	
	@Column
	private Boolean ativo;
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	// No-arg Constructor
	public Usuario() {
	}
	
	/**
	 * Construtor de usuário com ID
	 * @param id
	 * @param login
	 * @param senha
	 * @param nome
	 * @param email
	 */
	public Usuario(int id, String login, String senha, String nome, String email, Boolean ativo) {
		super();
		this.id = id;
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.ativo = ativo;
	}
	
	/**
	 * Construtor sem ID
	 * @param login
	 * @param senha
	 * @param nome
	 * @param email
	 */
	public Usuario(String login, String senha, String nome, String email, Boolean ativo) {
		super();
		this.login = login;
		this.senha = senha;
		this.nome = nome;
		this.email = email;
		this.ativo = ativo;
	}
	
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", nome=" + nome + ", email=" + email + ", ativo=" + ativo + "]";
	}
   
	
	
	
	
}
