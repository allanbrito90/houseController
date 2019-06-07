package br.com.houseController.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ConnectionFactory 
{
	
	
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
	
	static SessionFactory factory;
	
	public static void criarConexao(){
    	
    	factory = new Configuration()
    			.configure("hibernate/hibernate.cfg.xml")
    			.buildSessionFactory();
   	}
	
	public static Session obterNovaSessao(){
		return factory.openSession();
	}
	
	public static void fecharSessao(Session session){
		//Se estiver utilizando factory.getCurrentSession() provavelmente este método não será necessário
		session.close();
	}
	
	public static Session desfazer(Session session){
		session.getTransaction().rollback();
		return session;
	}
	
	public static void salvar(Session session){
		session.getTransaction().commit();
	}

	public static void fecharConexao() {
		factory.close();		
	}
}
