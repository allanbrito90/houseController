package br.com.houseController.service.Usuario;

import java.util.ArrayList;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class UsuarioService implements InterfaceService<Usuario>{

	public UsuarioService() {
	}

	@Override
	public Integer insert(Usuario obj) {
		Session session = ConnectionFactory.obterNovaSessao();
		session.save(obj);
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
		Query query = session.createQuery("delete from Usuario where id = :id");
		query.setParameter("id", id);
		Integer retorno = query.executeUpdate();
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
		Query query = session.createQuery("from Usuario where login = :login and senha = :senha");
		query.setParameter("login", usuario.getLogin());
		query.setParameter("senha", usuario.getSenha());		
		ArrayList<Usuario> list = (ArrayList<Usuario>) query.getResultList();
		ConnectionFactory.fecharSessao(session);
		if(list.size()>0){
			return true;
		}
		return false;		
	}


	

	
	//Para inserir (ou salvar update após o get)
//	Usuario usuario = new Usuario(1, "alex.brito", "5678", "Alex", "alex@hotmail.com");
//	session.beginTransaction();
//	session.save(usuario);
//	session.getTransaction().commit();
//	System.out.println("Inserido!");
	
	//Para obter um registro (ou para obter registro para alteração (após isso usar o save))
//	session.beginTransaction();
//	Usuario usuario = session.get(Usuario.class, 7);
//	System.out.println("Usuário obtido: " + usuario);
//	usuario.setNome("Alex");
//	session.save(usuario);
	
	//Para obter vários registros
//	session.beginTransaction();
//	List<Usuario> usuarios = session.createQuery("from Usuario").getResultList(); //Utilizar o nome da Entity (classe) e não o nome da table
//	for(Usuario usuario2 : usuarios){
//		System.out.println(usuario2);
//	}
	
//	Para trazer registros específicos com Where clause
//	session.beginTransaction();
//	Query query = session.createQuery("from Usuario where login = :nome and senha = :senha");
//	query.setParameter("nome", "talita");
//	query.setParameter("senha", "4321");
//	List list = query.getResultList();
//	System.out.println(list);
	
	//Para deletar registro
//	session.beginTransaction();
//	session.delete(usuario);
	
//	try {   			
//
//		
//		session.beginTransaction();
//		Query query = session.createQuery("from Usuario");
//		List list = query.getResultList();
//		System.out.println(list);
//		
//	} catch (RuntimeException e) {
//		e.printStackTrace();
//	}
//
//} catch (Exception e) {
//	factory.close();
//}

}
