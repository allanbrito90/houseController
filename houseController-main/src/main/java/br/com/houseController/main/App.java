package br.com.houseController.main;


import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;

import br.com.houseController.login.Login;
import br.com.houseController.model.usuario.Usuario;
import javafx.application.Application;
import javafx.stage.Stage;
import net.bytebuddy.asm.Advice.Unused;

/**
 * Hello world!
 *
 */
public class App extends Application{

    public static void main( String[] args )
    {
    	launch(args);
    	File file = new File("src/main/java/hibernate.cfg.xml");
    	
//    	SessionFactory factory = new Configuration()
//    			.configure(file)
////    			.addAnnotatedClass(Usuario.class)
//    			.buildSessionFactory();
//    	
//    	Session session = factory.getCurrentSession();
//    	
//    	try {
    		
    		//Para inserir (ou salvar update após o get)
//			Usuario usuario = new Usuario(1, "alex.brito", "5678", "Alex", "alex@hotmail.com");
//			session.beginTransaction();
//			session.save(usuario);
//			session.getTransaction().commit();
//			System.out.println("Inserido!");
    		
    		//Para obter um registro (ou para obter registro para alteração (após isso usar o save))
//			session.beginTransaction();
//			Usuario usuario = session.get(Usuario.class, 7);
//			System.out.println("Usuário obtido: " + usuario);
//			usuario.setNome("Alex");
//			session.save(usuario);
    		
    		//Para obter vários registros
//			session.beginTransaction();
//			List<Usuario> usuarios = session.createQuery("from Usuario").getResultList(); //Utilizar o nome da Entity (classe) e não o nome da table
//			for(Usuario usuario2 : usuarios){
//				System.out.println(usuario2);
//			}
    		
//    		Para trazer registros específicos com Where clause
//    		session.beginTransaction();
//			Query query = session.createQuery("from Usuario where login = :nome and senha = :senha");
//			query.setParameter("nome", "talita");
//			query.setParameter("senha", "4321");
//			List list = query.getResultList();
//			System.out.println(list);
    		
    		//Para deletar registro
//			session.beginTransaction();
//    		session.delete(usuario);
    		
//    		try {   			
//
//    			
//    			session.beginTransaction();
//    			Query query = session.createQuery("from Usuario");
//    			List list = query.getResultList();
//    			System.out.println(list);
//    			
//    		} catch (RuntimeException e) {
//    			e.printStackTrace();
//    		}
//
//		} catch (Exception e) {
//			factory.close();
//		}
    	  	 
    	
    }

	@Override
	public void start(Stage stage) throws Exception {
		Login login = new Login();
		login.exibirLogin(stage);		
	}
}
