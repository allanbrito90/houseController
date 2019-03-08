package br.com.houseController.service.Usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import br.com.houseController.model.Interfaces.InterfaceService;
import br.com.houseController.model.usuario.Usuario;
import br.com.houseController.persistence.ConnectionFactory;

public class UsuarioService implements InterfaceService{

	public UsuarioService() {
	}

	@Override
	public Integer insert(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> ArrayList<T> findAll() {
		Session session = ConnectionFactory.obterNovaSessao();
		Query query = session.createQuery("from Usuario");
		ArrayList<Object> usuarios = new ArrayList<>();
		List<?> list = query.getResultList();
		for(Object obj : list){
			Usuario usuario = (Usuario) obj; 
			usuarios.add(usuario);
		}
		ConnectionFactory.fecharSessao(session);
		return list;
	}

	@Override
	public Integer delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object findOne(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void obterTodosUsuarios(){
		
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
