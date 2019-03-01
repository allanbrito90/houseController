package br.com.houseController.main;


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
//    	launch(args);
    	
    	SessionFactory factory = new Configuration()
    			.configure("hibernate.cfg.xml")
    			.addAnnotatedClass(Usuario.class)
    			.buildSessionFactory();
    	
    	Session session = factory.getCurrentSession();
    	
    	try {
			/*Usuario usuario = new Usuario(7, "talita", "4321", "Talita", "talitanunes.tlt@hotmail.com");
			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();
			System.out.println("Inserido!");*/
			
    		try {
    			session.beginTransaction();
    			/*String detalhesProdutoHQL = "from usuario where id='5'";
    			  Query detalhesProdutoQuery = session.createQuery(detalhesProdutoHQL);
    			  Usuario detalhesProduto = 
    			  (Usuario) ((org.hibernate.query.Query) detalhesProdutoQuery).list().get(0);
    			  System.out.println(detalhesProduto);*/
    			
    			Usuario usuario = session.get(Usuario.class, 1);
    			System.out.println(usuario);
    		} catch (RuntimeException e) {
    			e.printStackTrace();
    		}

		} catch (Exception e) {
			factory.close();
		}
    	  	 
    	
    }

	@Override
	public void start(Stage stage) throws Exception {
		Login login = new Login();
		login.exibirLogin(stage);		
	}
}
