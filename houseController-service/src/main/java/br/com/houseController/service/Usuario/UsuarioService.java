package br.com.houseController.service.Usuario;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class UsuarioService implements InterfaceService<Usuario>, Callable<Boolean>{
	Usuario usuario = new Usuario();
	
	public UsuarioService(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public UsuarioService() {
	}

	@Override
	public Integer insert(Usuario obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		session.saveOrUpdate(obj);
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return obj.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Usuario> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario");
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		return list;
	}
	

	@Override
	public Integer delete(int id) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.beginTransaction();
		Query query = session.createQuery("delete from Usuario where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
		session.getTransaction().commit();
		ConnectionFactory.fecharSessao(session);
		return retorno;
	}

	@Override
	public Usuario findOne(Usuario obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		Usuario usuario = session.get(Usuario.class, obj.getId());
		ConnectionFactory.fecharSessao(session);
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public boolean checaLogin(Usuario usuario){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario where login = :login and senha = :senha and ativo = 1");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());		
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);

		if(list.size()>0){
			return true;
		}
		return false;		
	}
	
	@SuppressWarnings("unchecked")
	public boolean checaLoginExistente(Usuario usuario){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario where login = :login and ativo = 1");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());		
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);

		if(list.size()>0){
			return true;
		}
		return false;		
	}


	@Override
	public Boolean call() throws Exception {
		return checaLogin(usuario);
	}
	
	public Boolean metodoTeste(Usuario usuario){
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario where login = :login and senha = :senha and ativo = 1");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());		
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);

		if(list.size()>0){
			return true;
		}
		return false;	
	}

}
